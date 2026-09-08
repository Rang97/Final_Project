package com.example.demo.domain.user.service;

import com.example.demo.domain.user.dto.UserGameCreateRequest;
import com.example.demo.domain.user.repository.UserGameMapper;
import com.example.demo.global.util.CurrentUserProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/** Requires MYSQL_TEST_URL, MYSQL_TEST_USERNAME and MYSQL_TEST_PASSWORD.
 * Creates and removes only its own randomly named test schema; never loads application schema.sql.
 */
@EnabledIfEnvironmentVariable(named = "MYSQL_TEST_URL", matches = ".+")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserGameMySqlIntegrationTest {
    private final String schema = "user_game_test_" + UUID.randomUUID().toString().replace("-", "");
    private JdbcTemplate admin;
    private JdbcTemplate jdbc;
    private UserGameService service;
    private boolean schemaCreated;

    @BeforeAll
    void connect() throws Exception {
        String url = System.getenv("MYSQL_TEST_URL");
        String username = System.getenv("MYSQL_TEST_USERNAME");
        String password = System.getenv("MYSQL_TEST_PASSWORD");
        admin = new JdbcTemplate(new DriverManagerDataSource(url, username, password));
        admin.execute("CREATE DATABASE `" + schema + "`");
        schemaCreated = true;
        var uri = java.net.URI.create(url.substring(5));
        String testUrl = "jdbc:mysql://" + uri.getRawAuthority() + "/" + schema
                + (uri.getRawQuery() == null ? "" : "?" + uri.getRawQuery());
        var dataSource = new DriverManagerDataSource(testUrl, username, password);
        jdbc = new JdbcTemplate(dataSource);
        jdbc.execute("CREATE TABLE `user` (user_id BIGINT PRIMARY KEY) ENGINE=InnoDB");
        jdbc.execute("CREATE TABLE game (game_id BIGINT PRIMARY KEY, name_ko VARCHAR(100), cover_url VARCHAR(500), genre VARCHAR(100)) ENGINE=InnoDB");
        jdbc.execute("CREATE TABLE user_game (user_game_id BIGINT AUTO_INCREMENT PRIMARY KEY, user_id BIGINT NOT NULL, game_id BIGINT NOT NULL, is_main BOOLEAN NOT NULL DEFAULT FALSE, UNIQUE KEY uk_user_game(user_id,game_id), FOREIGN KEY(user_id) REFERENCES `user`(user_id), FOREIGN KEY(game_id) REFERENCES game(game_id)) ENGINE=InnoDB");
        jdbc.update("INSERT INTO `user` VALUES (1), (2)");
        for (int id = 1; id <= 6; id++) {
            jdbc.update("INSERT INTO game VALUES (?, ?, ?, ?)", id, "Game " + id, "cover" + id, "RPG");
        }
        var factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setMapperLocations(new ClassPathResource("mapper/UserGameMapper.xml"));
        var config = new org.apache.ibatis.session.Configuration();
        config.setMapUnderscoreToCamelCase(true);
        factory.setConfiguration(config);
        var mapper = new SqlSessionTemplate(factory.getObject()).getMapper(UserGameMapper.class);
        CurrentUserProvider user = new CurrentUserProvider() {
            public Long getCurrentUserId() { return 1L; }
            public Long getCurrentUserIdOrNull() { return 1L; }
        };
        var proxy = new ProxyFactory(new UserGameService(user, mapper));
        var interceptor = new TransactionInterceptor();
        interceptor.setTransactionManager(new DataSourceTransactionManager(dataSource));
        interceptor.setTransactionAttributeSource(new AnnotationTransactionAttributeSource());
        proxy.addAdvice(interceptor);
        service = (UserGameService) proxy.getProxy();
    }

    @BeforeEach
    void resetOwnTestData() {
        jdbc.update("DELETE FROM user_game");
        for (int id = 1; id <= 4; id++) {
            jdbc.update("INSERT INTO user_game(user_id, game_id) VALUES (1, ?)", id);
        }
    }

    @AfterAll
    void cleanUp() {
        if (schemaCreated) admin.execute("DROP DATABASE IF EXISTS `" + schema + "`");
    }

    @Test
    void concurrentRegistrationKeepsMaximumFive() throws Exception {
        var outcomes = concurrently(() -> register(5), () -> register(6));
        assertThat(outcomes).containsExactlyInAnyOrder(201, 409);
        assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM user_game WHERE user_id=1", Integer.class)).isEqualTo(5);
    }

    @Test
    void concurrentMainChangesLeaveExactlyOneMain() throws Exception {
        concurrently(() -> { service.changeMainGame(1L); return 200; },
                () -> { service.changeMainGame(2L); return 200; });
        assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM user_game WHERE user_id=1 AND is_main=TRUE", Integer.class)).isEqualTo(1);
    }

    @Test
    void mappingAndDeletingMainPreserveOtherUsers() {
        jdbc.update("INSERT INTO user_game(user_id,game_id,is_main) VALUES (2,1,TRUE)");
        service.changeMainGame(2L);
        var games = service.getMyGames();
        assertThat(games).hasSize(4);
        assertThat(games.getFirst()).isEqualTo(new com.example.demo.domain.user.dto.UserGameResponse(2L, "Game 2", "cover2", "RPG", true));
        service.delete(2L);
        assertThat(service.getMyGames()).noneMatch(com.example.demo.domain.user.dto.UserGameResponse::isMain);
        assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM user_game WHERE user_id=2 AND is_main=TRUE", Integer.class)).isEqualTo(1);
    }

    @Test
    void failedMainUpdateRollsBackPreviousSelection() {
        service.changeMainGame(1L);
        jdbc.execute("CREATE TRIGGER reject_main BEFORE UPDATE ON user_game FOR EACH ROW BEGIN IF NEW.game_id = 2 AND NEW.is_main = TRUE THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'test failure'; END IF; END");
        try {
            assertThatThrownBy(() -> service.changeMainGame(2L)).isInstanceOf(org.springframework.dao.DataAccessException.class);
            assertThat(jdbc.queryForObject("SELECT game_id FROM user_game WHERE user_id=1 AND is_main=TRUE", Long.class)).isEqualTo(1L);
        } finally {
            jdbc.execute("DROP TRIGGER reject_main");
        }
    }

    private int register(long gameId) throws Exception {
        var request = new ObjectMapper().readValue("{\"gameId\":" + gameId + "}", UserGameCreateRequest.class);
        try {
            service.register(request);
            return 201;
        } catch (ResponseStatusException exception) {
            return exception.getStatusCode().value();
        }
    }

    private java.util.List<Integer> concurrently(Callable<Integer> first, Callable<Integer> second) throws Exception {
        var executor = Executors.newFixedThreadPool(2);
        var barrier = new CyclicBarrier(2);
        try {
            var a = executor.submit(() -> { barrier.await(5, TimeUnit.SECONDS); return first.call(); });
            var b = executor.submit(() -> { barrier.await(5, TimeUnit.SECONDS); return second.call(); });
            return java.util.List.of(a.get(20, TimeUnit.SECONDS), b.get(20, TimeUnit.SECONDS));
        } finally {
            executor.shutdownNow();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}

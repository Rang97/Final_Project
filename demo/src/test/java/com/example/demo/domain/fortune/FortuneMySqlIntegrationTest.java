package com.example.demo.domain.fortune;

import com.example.demo.domain.fortune.repository.FortuneMapper;
import com.example.demo.domain.fortune.service.FortuneStore;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.*;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;
import static org.assertj.core.api.Assertions.*;

@EnabledIfEnvironmentVariable(named = "MYSQL_TEST_URL", matches = ".+")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FortuneMySqlIntegrationTest {
    final String schema = "fortune_test_" + UUID.randomUUID().toString().replace("-", "");
    final LocalDate date = LocalDate.of(2026, 9, 8);
    JdbcTemplate admin, jdbc;
    FortuneMapper mapper;
    FortuneStore store;
    boolean created;

    @BeforeAll void setup() throws Exception {
        String url = System.getenv("MYSQL_TEST_URL");
        String username = System.getenv("MYSQL_TEST_USERNAME"), password = System.getenv("MYSQL_TEST_PASSWORD");
        admin = new JdbcTemplate(new DriverManagerDataSource(url, username, password));
        admin.execute("CREATE DATABASE `" + schema + "`");
        created = true;
        var uri = java.net.URI.create(url.substring(5));
        String testUrl = "jdbc:mysql://" + uri.getRawAuthority() + "/" + schema
                + (uri.getRawQuery() == null ? "" : "?" + uri.getRawQuery());
        var ds = new DriverManagerDataSource(testUrl, username, password);
        jdbc = new JdbcTemplate(ds);
        jdbc.execute("CREATE TABLE `user` (user_id BIGINT PRIMARY KEY) ENGINE=InnoDB");
        jdbc.execute("INSERT INTO `user` VALUES (1)");
        jdbc.execute("""
                CREATE TABLE IF NOT EXISTS fortune_generation (
    user_id BIGINT NOT NULL,
    fortune_date DATE NOT NULL,
    status ENUM('PROCESSING', 'SUCCEEDED', 'FAILED') NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, fortune_date),
    CONSTRAINT fk_fortune_generation_user FOREIGN KEY (user_id) REFERENCES `user`(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
                """);
        jdbc.execute("""
                CREATE TABLE daily_fortune (
                    fortune_id BIGINT AUTO_INCREMENT PRIMARY KEY, user_id BIGINT NOT NULL,
                    fortune_date DATE NOT NULL, response_json JSON,
                    UNIQUE KEY uk_fortune(user_id,fortune_date)
                ) ENGINE=InnoDB
                """);
        jdbc.execute("CREATE TABLE game (game_id BIGINT PRIMARY KEY, name_ko VARCHAR(100))");
        jdbc.execute("CREATE TABLE user_game (user_game_id BIGINT PRIMARY KEY, user_id BIGINT, game_id BIGINT, is_main BOOLEAN)");
        jdbc.execute("CREATE TABLE game_tag (tag_id BIGINT PRIMARY KEY, name VARCHAR(50))");
        jdbc.execute("CREATE TABLE game_tag_map (game_id BIGINT, tag_id BIGINT)");
        jdbc.execute("INSERT INTO game VALUES (15, 'game1'), (16, 'game2')");
        jdbc.execute("INSERT INTO user_game VALUES (1,1,15,TRUE), (2,1,16,FALSE)");
        jdbc.execute("INSERT INTO game_tag VALUES (1,'MOBA'), (2,'TEAM')");
        jdbc.execute("INSERT INTO game_tag_map VALUES (15,1), (15,2)");
        var factory = new SqlSessionFactoryBean();
        factory.setDataSource(ds);
        var config = new org.apache.ibatis.session.Configuration();
        config.setMapUnderscoreToCamelCase(true);
        config.addMapper(FortuneMapper.class);
        factory.setConfiguration(config);
        mapper = new SqlSessionTemplate(factory.getObject()).getMapper(FortuneMapper.class);
        var proxy = new ProxyFactory(new FortuneStore(mapper));
        var tx = new TransactionInterceptor();
        tx.setTransactionManager(new DataSourceTransactionManager(ds));
        tx.setTransactionAttributeSource(new AnnotationTransactionAttributeSource());
        proxy.addAdvice(tx);
        store = (FortuneStore) proxy.getProxy();
    }

    @BeforeEach void reset() {
        jdbc.update("DELETE FROM daily_fortune");
        jdbc.update("DELETE FROM fortune_generation");
    }

    @AfterAll void cleanup() {
        if (created) admin.execute("DROP DATABASE `" + schema + "`");
    }

    @Test void onlyOneConcurrentClaimWins() throws Exception {
        try (var executor = Executors.newFixedThreadPool(2)) {
            var barrier = new CyclicBarrier(2);
            Callable<Boolean> claim = () -> {
                barrier.await(5, TimeUnit.SECONDS);
                try { mapper.claim(1L, date); return true; }
                catch (DuplicateKeyException e) { return false; }
            };
            var first = executor.submit(claim);
            var second = executor.submit(claim);
            assertThat(List.of(first.get(10, TimeUnit.SECONDS), second.get(10, TimeUnit.SECONDS)))
                    .containsExactlyInAnyOrder(true, false);
        }
        assertThat(mapper.status(1L, date)).isEqualTo("PROCESSING");
    }

    @Test void storesJsonAndStatusAtomically() {
        mapper.claim(1L, date);
        store.complete(1L, date, "{\"ok\":true}");
        assertThat(mapper.find(1L, date).responseJson()).contains("true");
        assertThat(mapper.status(1L, date)).isEqualTo("SUCCEEDED");
    }

    @Test void failedSaveRollsBackSucceededStatus() {
        mapper.claim(1L, date);
        mapper.save(1L, date, "{}");
        assertThatThrownBy(() -> store.complete(1L, date, "{}")).isInstanceOf(DuplicateKeyException.class);
        assertThat(mapper.status(1L, date)).isEqualTo("PROCESSING");
    }

    @Test void mapsJoinedGamesIncludingUntaggedGame() {
        assertThat(mapper.games(1L)).containsExactly(
                new FortuneMapper.GameRow(15L, "game1", "MOBA"),
                new FortuneMapper.GameRow(15L, "game1", "TEAM"),
                new FortuneMapper.GameRow(16L, "game2", null));
    }
}


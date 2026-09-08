-- ============================================================
-- Final_Project schema.sql
-- DBMS: MySQL 8.x
-- Charset: utf8mb4
--
-- 설계 반영 사항
-- 1) PARTY.cover_url은 GAME.cover_url을 참조하는 FK로 만들 수 없으므로 일반 VARCHAR 컬럼으로 둡니다.
--    (실제 구현에서는 GAME JOIN으로 조회하고 PARTY.cover_url 자체를 제거해도 됩니다.)
-- 2) PARTY_MEMBER.chemistry_score는 유저-유저 실시간 계산값이므로 DB 컬럼에서 제외합니다.
-- 3) USER_GAME의 "최대 5개" 제한은 DB가 아니라 Service 계층에서 검증합니다.
-- 4) DAILY_FORTUNE은 사용자별 날짜당 1건만 저장하도록 UNIQUE(user_id, fortune_date)를 둡니다.
-- 5) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
-- 5.1) ENGINE=InnoDB
    -- MySQL의 저장 엔진
    -- 외래키(FK), 트랜잭션, 롤백 같은 기능 지원
    -- Spring/JPA 프로젝트에서는 사실상 기본 추천
-- 5.2) DEFAULT CHARSET=utf8mb4
    -- 테이블의 기본 문자셋
    -- 한글, 영어, 일본어, 이모지까지 저장 가능
    -- utf8보다 utf8mb4가 더 완전한 UTF-8
-- 5.3) COLLATE=utf8mb4_unicode_ci
    -- 문자열 비교/정렬 규칙
    -- ci는 case-insensitive라서 대소문자를 구분하지 않는다는 뜻
    -- 예: "USER"와 "user"를 같은 값처럼 비교할 수 있음
-- ============================================================

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 자식 테이블부터 삭제
DROP TABLE IF EXISTS `daily_fortune`;
DROP TABLE IF EXISTS `saju_input`;
DROP TABLE IF EXISTS `block`;
DROP TABLE IF EXISTS `comment`;
DROP TABLE IF EXISTS `post`;
DROP TABLE IF EXISTS `party_member`;
DROP TABLE IF EXISTS `party`;
DROP TABLE IF EXISTS `game_tag_map`;
DROP TABLE IF EXISTS `user_game`;
DROP TABLE IF EXISTS `game_tag`;
DROP TABLE IF EXISTS `game`;
DROP TABLE IF EXISTS `saju`;
DROP TABLE IF EXISTS `user`;

SET FOREIGN_KEY_CHECKS = 1;

-- ============================================================
-- 1. USER
-- ============================================================
CREATE TABLE `user` (
    `user_id` BIGINT NOT NULL AUTO_INCREMENT,
    `login_id` VARCHAR(50) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `nickname` VARCHAR(50) NOT NULL,
    `role` ENUM('USER', 'ADMIN') NOT NULL DEFAULT 'USER',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (`user_id`),
    UNIQUE KEY `uk_user_login_id` (`login_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ============================================================
-- 2. SAJU
-- USER : SAJU = 1 : 1
-- ============================================================
CREATE TABLE `saju` (
    `saju_id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL,

    `gender` VARCHAR(10) NOT NULL,
    `birth_date` DATE NOT NULL,
    `calendar_type` ENUM('SOLAR', 'LUNAR') NOT NULL,
    `birth_time_type` ENUM('EXACT', 'TIME_BRANCH', 'UNKNOWN') NOT NULL,
    `birth_time` TIME NULL,
    `birth_time_branch`
        ENUM('JA', 'CHUK', 'IN', 'MYO', 'JIN', 'SA', 'O', 'MI', 'SIN', 'YU', 'SUL', 'HAE')
        NULL,

    `year_stem` VARCHAR(10) NOT NULL,
    `year_branch` VARCHAR(10) NOT NULL,
    `month_stem` VARCHAR(10) NOT NULL,
    `month_branch` VARCHAR(10) NOT NULL,
    `day_stem` VARCHAR(10) NOT NULL,
    `day_branch` VARCHAR(10) NOT NULL,
    `hour_stem` VARCHAR(10) NULL,
    `hour_branch` VARCHAR(10) NULL,

    `wood_count` DOUBLE NOT NULL,
    `fire_count` DOUBLE NOT NULL,
    `earth_count` DOUBLE NOT NULL,
    `metal_count` DOUBLE NOT NULL,
    `water_count` DOUBLE NOT NULL,

    `saju_animal_name` VARCHAR(50) NOT NULL,
    `calculated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (`saju_id`),
    UNIQUE KEY `uk_saju_user_id` (`user_id`),

    CONSTRAINT `fk_saju_user`
        FOREIGN KEY (`user_id`)
        REFERENCES `user` (`user_id`)
        ON DELETE CASCADE,

    CONSTRAINT `chk_saju_wood_count` CHECK (`wood_count` >= 0),
    CONSTRAINT `chk_saju_fire_count` CHECK (`fire_count` >= 0),
    CONSTRAINT `chk_saju_earth_count` CHECK (`earth_count` >= 0),
    CONSTRAINT `chk_saju_metal_count` CHECK (`metal_count` >= 0),
    CONSTRAINT `chk_saju_water_count` CHECK (`water_count` >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ============================================================
-- 2-1. SAJU_INPUT
-- 회원가입에서 받은 원본 입력값. 계산 완료 후 SAJU 생성에 사용.
-- USER : SAJU_INPUT = 1 : 1
-- ============================================================
CREATE TABLE `saju_input` (
    `saju_input_id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL,
    `birth_date` DATE NOT NULL,
    `gender` ENUM('MALE', 'FEMALE') NOT NULL,
    `calendar_type` ENUM('SOLAR', 'LUNAR') NOT NULL,
    `birth_time_type` ENUM('TIME_BRANCH', 'UNKNOWN') NOT NULL,
    `birth_time_branch`
        ENUM('JA', 'CHUK', 'IN', 'MYO', 'JIN', 'SA', 'O', 'MI', 'SIN', 'YU', 'SUL', 'HAE')
        NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (`saju_input_id`),
    UNIQUE KEY `uk_saju_input_user_id` (`user_id`),

    CONSTRAINT `fk_saju_input_user`
        FOREIGN KEY (`user_id`)
        REFERENCES `user` (`user_id`)
        ON DELETE CASCADE,

    CONSTRAINT `chk_saju_input_birth_time`
        CHECK (
            (`birth_time_type` = 'UNKNOWN' AND `birth_time_branch` IS NULL)
            OR
            (`birth_time_type` = 'TIME_BRANCH' AND `birth_time_branch` IS NOT NULL)
        )
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ============================================================
-- 3. GAME
-- ============================================================
CREATE TABLE `game` (
    `game_id` BIGINT NOT NULL AUTO_INCREMENT,
    `name_ko` VARCHAR(100) NOT NULL,
    `cover_url` VARCHAR(500) NULL,
    `genre` VARCHAR(100) NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (`game_id`),
    UNIQUE KEY `uk_game_name_ko` (`name_ko`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ============================================================
-- 4. USER_GAME
-- USER : GAME = N : M
-- 한 사용자의 최대 5개 게임 등록 제한은 Service에서 검증
-- ============================================================
CREATE TABLE `user_game` (
    `user_game_id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL,
    `game_id` BIGINT NOT NULL,
    `is_main` BOOLEAN NOT NULL DEFAULT FALSE,

    PRIMARY KEY (`user_game_id`),
    UNIQUE KEY `uk_user_game` (`user_id`, `game_id`),

    CONSTRAINT `fk_user_game_user`
        FOREIGN KEY (`user_id`)
        REFERENCES `user` (`user_id`)
        ON DELETE CASCADE,

    CONSTRAINT `fk_user_game_game`
        FOREIGN KEY (`game_id`)
        REFERENCES `game` (`game_id`)
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ============================================================
-- 5. GAME_TAG
-- ============================================================
CREATE TABLE `game_tag` (
    `tag_id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,

    PRIMARY KEY (`tag_id`),
    UNIQUE KEY `uk_game_tag_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ============================================================
-- 6. GAME_TAG_MAP
-- GAME : GAME_TAG = N : M
-- ============================================================
CREATE TABLE `game_tag_map` (
    `game_id` BIGINT NOT NULL,
    `tag_id` BIGINT NOT NULL,

    PRIMARY KEY (`game_id`, `tag_id`),

    CONSTRAINT `fk_game_tag_map_game`
        FOREIGN KEY (`game_id`)
        REFERENCES `game` (`game_id`)
        ON DELETE CASCADE,

    CONSTRAINT `fk_game_tag_map_tag`
        FOREIGN KEY (`tag_id`)
        REFERENCES `game_tag` (`tag_id`)
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ============================================================
-- 7. PARTY
-- ============================================================
CREATE TABLE `party` (
    `party_id` BIGINT NOT NULL AUTO_INCREMENT,
    `host_id` BIGINT NOT NULL,
    `game_id` BIGINT NOT NULL,
    `title` VARCHAR(150) NOT NULL,

    -- GAME.cover_url을 화면에 그대로 사용할 경우 이 컬럼은 제거 가능
    `cover_url` VARCHAR(500) NULL,

    `max_member_count` INT NOT NULL,
    `now_member_count` INT NOT NULL DEFAULT 1,

    `chemistry_type` ENUM('SYNERGY', 'RIVAL', 'BALANCED') NOT NULL,
    `status` ENUM('RECRUITING', 'FULL', 'COMPLETED', 'CLOSED')
        NOT NULL DEFAULT 'RECRUITING',

    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (`party_id`),

    KEY `idx_party_host_id` (`host_id`),
    KEY `idx_party_game_id` (`game_id`),
    KEY `idx_party_status` (`status`),

    CONSTRAINT `fk_party_host`
        FOREIGN KEY (`host_id`)
        REFERENCES `user` (`user_id`)
        ON DELETE RESTRICT,

    CONSTRAINT `fk_party_game`
        FOREIGN KEY (`game_id`)
        REFERENCES `game` (`game_id`)
        ON DELETE RESTRICT,

    CONSTRAINT `chk_party_max_member_count`
        CHECK (`max_member_count` >= 2),

    CONSTRAINT `chk_party_now_member_count`
        CHECK (
            `now_member_count` >= 1
            AND `now_member_count` <= `max_member_count`
        )
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ============================================================
-- 8. PARTY_MEMBER
-- chemistry_score는 저장하지 않음.
-- 로그인 사용자 ↔ 상대 파티원의 SAJU를 이용해 실시간 계산 후 DTO에 포함.
-- ============================================================
CREATE TABLE `party_member` (
    `party_member_id` BIGINT NOT NULL AUTO_INCREMENT,
    `party_id` BIGINT NOT NULL,
    `user_id` BIGINT NOT NULL,

    `status` ENUM('APPLIED', 'APPROVED', 'REJECTED', 'KICKED', 'LEFT')
        NOT NULL DEFAULT 'APPLIED',

    PRIMARY KEY (`party_member_id`),
    UNIQUE KEY `uk_party_member` (`party_id`, `user_id`),

    KEY `idx_party_member_user_id` (`user_id`),

    CONSTRAINT `fk_party_member_party`
        FOREIGN KEY (`party_id`)
        REFERENCES `party` (`party_id`)
        ON DELETE CASCADE,

    CONSTRAINT `fk_party_member_user`
        FOREIGN KEY (`user_id`)
        REFERENCES `user` (`user_id`)
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ============================================================
-- 9. POST
-- ============================================================
CREATE TABLE `post` (
    `post_id` BIGINT NOT NULL AUTO_INCREMENT,
    `writer_id` BIGINT NOT NULL,
    `title` VARCHAR(200) NOT NULL,
    `content` TEXT NOT NULL,
    `view_count` INT NOT NULL DEFAULT 0,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (`post_id`),
    KEY `idx_post_writer_id` (`writer_id`),
    KEY `idx_post_created_at` (`created_at`),

    CONSTRAINT `fk_post_writer`
        FOREIGN KEY (`writer_id`)
        REFERENCES `user` (`user_id`)
        ON DELETE RESTRICT,

    CONSTRAINT `chk_post_view_count`
        CHECK (`view_count` >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ============================================================
-- 10. COMMENT
-- ============================================================
CREATE TABLE `comment` (
    `comment_id` BIGINT NOT NULL AUTO_INCREMENT,
    `post_id` BIGINT NOT NULL,
    `writer_id` BIGINT NOT NULL,
    `content` TEXT NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (`comment_id`),
    KEY `idx_comment_post_id` (`post_id`),
    KEY `idx_comment_writer_id` (`writer_id`),

    CONSTRAINT `fk_comment_post`
        FOREIGN KEY (`post_id`)
        REFERENCES `post` (`post_id`)
        ON DELETE CASCADE,

    CONSTRAINT `fk_comment_writer`
        FOREIGN KEY (`writer_id`)
        REFERENCES `user` (`user_id`)
        ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ============================================================
-- 11. BLOCK
-- ============================================================
CREATE TABLE `block` (
    `block_id` BIGINT NOT NULL AUTO_INCREMENT,
    `blocker_id` BIGINT NOT NULL,
    `blocked_id` BIGINT NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (`block_id`),
    UNIQUE KEY `uk_block_relation` (`blocker_id`, `blocked_id`),

    KEY `idx_block_blocked_id` (`blocked_id`),

    CONSTRAINT `fk_block_blocker`
        FOREIGN KEY (`blocker_id`)
        REFERENCES `user` (`user_id`)
        ON DELETE CASCADE,

    CONSTRAINT `fk_block_blocked`
        FOREIGN KEY (`blocked_id`)
        REFERENCES `user` (`user_id`)
        ON DELETE CASCADE,

    CONSTRAINT `chk_block_self`
        CHECK (`blocker_id` <> `blocked_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ============================================================
-- 12. DAILY_FORTUNE
-- 사용자별 하루 한 건의 운세
-- ============================================================
CREATE TABLE daily_fortune (
    fortune_id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    fortune_date DATE NOT NULL,
    game_fortune TEXT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (fortune_id),

    UNIQUE KEY uk_daily_fortune_user_date (user_id, fortune_date),

    CONSTRAINT fk_daily_fortune_user
        FOREIGN KEY (user_id)
        REFERENCES user(user_id)
        ON DELETE CASCADE
);


-- ============================================================
-- 스키마 생성 확인
-- ============================================================
SHOW TABLES;

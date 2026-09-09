-- Existing databases: run manually once before starting the new application.
-- No existing fortune text or user data is deleted. Flyway is not configured.
SET @fortune_column_sql = (
    SELECT IF(COUNT(*) = 0,
        'ALTER TABLE daily_fortune ADD COLUMN response_json JSON NULL AFTER game_fortune',
        'SELECT 1')
    FROM information_schema.columns
    WHERE table_schema = DATABASE() AND table_name = 'daily_fortune' AND column_name = 'response_json'
);
PREPARE fortune_column_statement FROM @fortune_column_sql;
EXECUTE fortune_column_statement;
DEALLOCATE PREPARE fortune_column_statement;

CREATE TABLE IF NOT EXISTS fortune_generation (
    user_id BIGINT NOT NULL,
    fortune_date DATE NOT NULL,
    status ENUM('PROCESSING', 'SUCCEEDED', 'FAILED') NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, fortune_date),
    CONSTRAINT fk_fortune_generation_user FOREIGN KEY (user_id) REFERENCES `user`(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

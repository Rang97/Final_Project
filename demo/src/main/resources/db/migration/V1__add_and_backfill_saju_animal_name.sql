-- 기존 운영 DB에 수동 적용하기 위한 멱등 마이그레이션입니다.
-- 현재 프로젝트는 Flyway를 사용하지 않으므로 이 파일은 자동 실행되지 않습니다.
SET @add_saju_animal_name = (
    SELECT IF(
        COUNT(*) = 0,
        'ALTER TABLE saju ADD COLUMN saju_animal_name VARCHAR(50) NULL AFTER water_count',
        'SELECT 1'
    )
    FROM information_schema.columns
    WHERE table_schema = DATABASE()
      AND table_name = 'saju'
      AND column_name = 'saju_animal_name'
);

PREPARE add_column_statement FROM @add_saju_animal_name;
EXECUTE add_column_statement;
DEALLOCATE PREPARE add_column_statement;

UPDATE saju
SET saju_animal_name = CONCAT(
    CASE day_stem
        WHEN '甲' THEN '푸른' WHEN '갑' THEN '푸른'
        WHEN '乙' THEN '푸른' WHEN '을' THEN '푸른'
        WHEN '丙' THEN '붉은' WHEN '병' THEN '붉은'
        WHEN '丁' THEN '붉은' WHEN '정' THEN '붉은'
        WHEN '戊' THEN '노란' WHEN '무' THEN '노란'
        WHEN '己' THEN '노란' WHEN '기' THEN '노란'
        WHEN '庚' THEN '하얀' WHEN '경' THEN '하얀'
        WHEN '辛' THEN '하얀' WHEN '신' THEN '하얀'
        WHEN '壬' THEN '검은' WHEN '임' THEN '검은'
        WHEN '癸' THEN '검은' WHEN '계' THEN '검은'
    END,
    ' ',
    CASE day_branch
        WHEN '子' THEN '쥐' WHEN '자' THEN '쥐'
        WHEN '丑' THEN '소' WHEN '축' THEN '소'
        WHEN '寅' THEN '호랑이' WHEN '인' THEN '호랑이'
        WHEN '卯' THEN '토끼' WHEN '묘' THEN '토끼'
        WHEN '辰' THEN '용' WHEN '진' THEN '용'
        WHEN '巳' THEN '뱀' WHEN '사' THEN '뱀'
        WHEN '午' THEN '말' WHEN '오' THEN '말'
        WHEN '未' THEN '양' WHEN '미' THEN '양'
        WHEN '申' THEN '원숭이' WHEN '신' THEN '원숭이'
        WHEN '酉' THEN '닭' WHEN '유' THEN '닭'
        WHEN '戌' THEN '개' WHEN '술' THEN '개'
        WHEN '亥' THEN '돼지' WHEN '해' THEN '돼지'
    END
)
WHERE saju_animal_name IS NULL OR saju_animal_name = '';

ALTER TABLE saju
    MODIFY COLUMN saju_animal_name VARCHAR(50) NOT NULL;

-- Final_Project test seed data
-- MySQL / Spring Boot data.sql
-- cover_url은 추후 직접 등록할 수 있도록 모두 NULL로 저장

-- =========================
-- 1. GAME_TAG
-- =========================
INSERT INTO game_tag (name) VALUES
('PVP'),
('COOP'),
('TEAM'),
('COMPETITIVE'),
('CASUAL'),
('SURVIVAL'),
('BATTLE_ROYALE'),
('MMORPG'),
('MOBA'),
('FPS'),
('TPS'),
('SPORTS'),
('PARTY'),
('STRATEGY'),
('PUZZLE'),
('HORROR'),
('SANDBOX'),
('RAID'),
('OPEN_WORLD');

-- =========================
-- 2. GAME (51개)
-- =========================
INSERT INTO game (name_ko, cover_url, genre, created_at) VALUES
('리그 오브 레전드', NULL, 'MOBA', CURRENT_TIMESTAMP),
('발로란트', NULL, 'FPS', CURRENT_TIMESTAMP),
('오버워치 2', NULL, 'FPS', CURRENT_TIMESTAMP),
('PUBG: 배틀그라운드', NULL, '배틀로얄', CURRENT_TIMESTAMP),
('에이펙스 레전드', NULL, '배틀로얄', CURRENT_TIMESTAMP),
('카운터 스트라이크 2', NULL, 'FPS', CURRENT_TIMESTAMP),
('레인보우 식스 시즈', NULL, 'FPS', CURRENT_TIMESTAMP),
('마블 라이벌즈', NULL, 'TPS', CURRENT_TIMESTAMP),
('포트나이트', NULL, '배틀로얄', CURRENT_TIMESTAMP),
('도타 2', NULL, 'MOBA', CURRENT_TIMESTAMP),
('더 파이널스', NULL, 'FPS', CURRENT_TIMESTAMP),
('델타 포스', NULL, 'FPS', CURRENT_TIMESTAMP),
('콜 오브 듀티: 워존', NULL, '배틀로얄', CURRENT_TIMESTAMP),
('배틀필드 2042', NULL, 'FPS', CURRENT_TIMESTAMP),
('팀 포트리스 2', NULL, 'FPS', CURRENT_TIMESTAMP),
('헬다이버즈 2', NULL, 'TPS', CURRENT_TIMESTAMP),
('워프레임', NULL, 'TPS', CURRENT_TIMESTAMP),
('데스티니 2', NULL, 'FPS', CURRENT_TIMESTAMP),
('딥 락 갤럭틱', NULL, 'FPS', CURRENT_TIMESTAMP),
('레프트 4 데드 2', NULL, 'FPS', CURRENT_TIMESTAMP),
('백 4 블러드', NULL, 'FPS', CURRENT_TIMESTAMP),
('레디 오어 낫', NULL, '택티컬 FPS', CURRENT_TIMESTAMP),
('데드 바이 데이라이트', NULL, '공포', CURRENT_TIMESTAMP),
('로스트아크', NULL, 'MMORPG', CURRENT_TIMESTAMP),
('메이플스토리', NULL, 'MMORPG', CURRENT_TIMESTAMP),
('던전앤파이터', NULL, 'MORPG', CURRENT_TIMESTAMP),
('월드 오브 워크래프트', NULL, 'MMORPG', CURRENT_TIMESTAMP),
('파이널 판타지 XIV', NULL, 'MMORPG', CURRENT_TIMESTAMP),
('검은사막', NULL, 'MMORPG', CURRENT_TIMESTAMP),
('길드워 2', NULL, 'MMORPG', CURRENT_TIMESTAMP),
('마인크래프트', NULL, '샌드박스', CURRENT_TIMESTAMP),
('로블록스', NULL, '샌드박스', CURRENT_TIMESTAMP),
('러스트', NULL, '서바이벌', CURRENT_TIMESTAMP),
('팰월드', NULL, '서바이벌', CURRENT_TIMESTAMP),
('프로젝트 좀보이드', NULL, '서바이벌', CURRENT_TIMESTAMP),
('테라리아', NULL, '샌드박스', CURRENT_TIMESTAMP),
('스타듀 밸리', NULL, '생활', CURRENT_TIMESTAMP),
('돈 스타브 투게더', NULL, '서바이벌', CURRENT_TIMESTAMP),
('발헤임', NULL, '서바이벌', CURRENT_TIMESTAMP),
('래프트', NULL, '서바이벌', CURRENT_TIMESTAMP),
('그라운디드', NULL, '서바이벌', CURRENT_TIMESTAMP),
('노 맨즈 스카이', NULL, '오픈월드', CURRENT_TIMESTAMP),
('씨 오브 시브즈', NULL, '오픈월드', CURRENT_TIMESTAMP),
('몬스터 헌터 와일즈', NULL, '액션 RPG', CURRENT_TIMESTAMP),
('몬스터 헌터: 월드', NULL, '액션 RPG', CURRENT_TIMESTAMP),
('엘든 링', NULL, '액션 RPG', CURRENT_TIMESTAMP),
('EA SPORTS FC 26', NULL, '스포츠', CURRENT_TIMESTAMP),
('FC 온라인', NULL, '스포츠', CURRENT_TIMESTAMP),
('로켓 리그', NULL, '스포츠', CURRENT_TIMESTAMP),
('철권 8', NULL, '격투', CURRENT_TIMESTAMP),
('마비노기 모바일', NULL, 'MMORPG', CURRENT_TIMESTAMP);

-- =========================
-- 3. GAME_TAG_MAP
-- =========================
-- 리그 오브 레전드
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('PVP', 'TEAM', 'COMPETITIVE', 'MOBA')
WHERE g.name_ko = '리그 오브 레전드';

-- 발로란트
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('PVP', 'TEAM', 'COMPETITIVE', 'FPS')
WHERE g.name_ko = '발로란트';

-- 오버워치 2
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('PVP', 'TEAM', 'COMPETITIVE', 'FPS')
WHERE g.name_ko = '오버워치 2';

-- PUBG: 배틀그라운드
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('PVP', 'TEAM', 'COMPETITIVE', 'BATTLE_ROYALE', 'TPS')
WHERE g.name_ko = 'PUBG: 배틀그라운드';

-- 에이펙스 레전드
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('PVP', 'TEAM', 'COMPETITIVE', 'BATTLE_ROYALE', 'FPS')
WHERE g.name_ko = '에이펙스 레전드';

-- 카운터 스트라이크 2
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('PVP', 'TEAM', 'COMPETITIVE', 'FPS')
WHERE g.name_ko = '카운터 스트라이크 2';

-- 레인보우 식스 시즈
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('PVP', 'TEAM', 'COMPETITIVE', 'FPS', 'STRATEGY')
WHERE g.name_ko = '레인보우 식스 시즈';

-- 마블 라이벌즈
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('PVP', 'TEAM', 'COMPETITIVE', 'TPS')
WHERE g.name_ko = '마블 라이벌즈';

-- 포트나이트
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('PVP', 'TEAM', 'BATTLE_ROYALE', 'TPS', 'CASUAL')
WHERE g.name_ko = '포트나이트';

-- 도타 2
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('PVP', 'TEAM', 'COMPETITIVE', 'MOBA')
WHERE g.name_ko = '도타 2';

-- 더 파이널스
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('PVP', 'TEAM', 'COMPETITIVE', 'FPS')
WHERE g.name_ko = '더 파이널스';

-- 델타 포스
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('PVP', 'TEAM', 'COMPETITIVE', 'FPS')
WHERE g.name_ko = '델타 포스';

-- 콜 오브 듀티: 워존
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('PVP', 'TEAM', 'COMPETITIVE', 'BATTLE_ROYALE', 'FPS')
WHERE g.name_ko = '콜 오브 듀티: 워존';

-- 배틀필드 2042
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('PVP', 'TEAM', 'FPS')
WHERE g.name_ko = '배틀필드 2042';

-- 팀 포트리스 2
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('PVP', 'TEAM', 'FPS', 'CASUAL')
WHERE g.name_ko = '팀 포트리스 2';

-- 헬다이버즈 2
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('COOP', 'TEAM', 'TPS')
WHERE g.name_ko = '헬다이버즈 2';

-- 워프레임
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('COOP', 'TEAM', 'TPS', 'OPEN_WORLD')
WHERE g.name_ko = '워프레임';

-- 데스티니 2
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('COOP', 'PVP', 'TEAM', 'FPS', 'RAID')
WHERE g.name_ko = '데스티니 2';

-- 딥 락 갤럭틱
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('COOP', 'TEAM', 'FPS')
WHERE g.name_ko = '딥 락 갤럭틱';

-- 레프트 4 데드 2
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('COOP', 'TEAM', 'FPS', 'HORROR')
WHERE g.name_ko = '레프트 4 데드 2';

-- 백 4 블러드
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('COOP', 'TEAM', 'FPS', 'HORROR')
WHERE g.name_ko = '백 4 블러드';

-- 레디 오어 낫
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('COOP', 'TEAM', 'FPS', 'STRATEGY')
WHERE g.name_ko = '레디 오어 낫';

-- 데드 바이 데이라이트
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('PVP', 'COOP', 'HORROR')
WHERE g.name_ko = '데드 바이 데이라이트';

-- 로스트아크
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('COOP', 'TEAM', 'MMORPG', 'RAID')
WHERE g.name_ko = '로스트아크';

-- 메이플스토리
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('COOP', 'MMORPG', 'CASUAL')
WHERE g.name_ko = '메이플스토리';

-- 던전앤파이터
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('COOP', 'TEAM', 'RAID')
WHERE g.name_ko = '던전앤파이터';

-- 월드 오브 워크래프트
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('COOP', 'PVP', 'TEAM', 'MMORPG', 'RAID')
WHERE g.name_ko = '월드 오브 워크래프트';

-- 파이널 판타지 XIV
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('COOP', 'TEAM', 'MMORPG', 'RAID')
WHERE g.name_ko = '파이널 판타지 XIV';

-- 검은사막
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('COOP', 'PVP', 'MMORPG', 'OPEN_WORLD')
WHERE g.name_ko = '검은사막';

-- 길드워 2
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('COOP', 'PVP', 'TEAM', 'MMORPG', 'OPEN_WORLD')
WHERE g.name_ko = '길드워 2';

-- 마인크래프트
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('COOP', 'CASUAL', 'SANDBOX', 'SURVIVAL')
WHERE g.name_ko = '마인크래프트';

-- 로블록스
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('COOP', 'PVP', 'CASUAL', 'SANDBOX', 'PARTY')
WHERE g.name_ko = '로블록스';

-- 러스트
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('PVP', 'COOP', 'SURVIVAL', 'SANDBOX', 'OPEN_WORLD')
WHERE g.name_ko = '러스트';

-- 팰월드
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('COOP', 'SURVIVAL', 'OPEN_WORLD')
WHERE g.name_ko = '팰월드';

-- 프로젝트 좀보이드
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('COOP', 'SURVIVAL', 'HORROR', 'OPEN_WORLD')
WHERE g.name_ko = '프로젝트 좀보이드';

-- 테라리아
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('COOP', 'CASUAL', 'SANDBOX', 'OPEN_WORLD')
WHERE g.name_ko = '테라리아';

-- 스타듀 밸리
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('COOP', 'CASUAL')
WHERE g.name_ko = '스타듀 밸리';

-- 돈 스타브 투게더
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('COOP', 'TEAM', 'SURVIVAL')
WHERE g.name_ko = '돈 스타브 투게더';

-- 발헤임
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('COOP', 'TEAM', 'SURVIVAL', 'OPEN_WORLD')
WHERE g.name_ko = '발헤임';

-- 래프트
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('COOP', 'TEAM', 'SURVIVAL')
WHERE g.name_ko = '래프트';

-- 그라운디드
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('COOP', 'TEAM', 'SURVIVAL', 'OPEN_WORLD')
WHERE g.name_ko = '그라운디드';

-- 노 맨즈 스카이
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('COOP', 'CASUAL', 'OPEN_WORLD')
WHERE g.name_ko = '노 맨즈 스카이';

-- 씨 오브 시브즈
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('COOP', 'PVP', 'TEAM', 'OPEN_WORLD')
WHERE g.name_ko = '씨 오브 시브즈';

-- 몬스터 헌터 와일즈
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('COOP', 'TEAM')
WHERE g.name_ko = '몬스터 헌터 와일즈';

-- 몬스터 헌터: 월드
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('COOP', 'TEAM')
WHERE g.name_ko = '몬스터 헌터: 월드';

-- 엘든 링
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('COOP', 'PVP', 'OPEN_WORLD')
WHERE g.name_ko = '엘든 링';

-- EA SPORTS FC 26
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('PVP', 'TEAM', 'COMPETITIVE', 'SPORTS')
WHERE g.name_ko = 'EA SPORTS FC 26';

-- FC 온라인
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('PVP', 'COMPETITIVE', 'SPORTS')
WHERE g.name_ko = 'FC 온라인';

-- 로켓 리그
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('PVP', 'TEAM', 'COMPETITIVE', 'SPORTS')
WHERE g.name_ko = '로켓 리그';

-- 철권 8
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
JOIN game_tag t ON t.name IN ('PVP', 'COMPETITIVE')
WHERE g.name_ko = '철권 8';

-- 마비노기 모바일
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
         JOIN game_tag t ON t.name IN ('COOP', 'MMORPG', 'CASUAL')
WHERE g.name_ko = '마비노기 모바일';

-- =========================
-- 4. 확인용 조회
-- =========================
SELECT
    g.game_id,
    g.name_ko,
    g.genre,
    g.cover_url,
    GROUP_CONCAT(t.name ORDER BY t.name SEPARATOR ', ') AS tags
FROM game g
LEFT JOIN game_tag_map gtm ON g.game_id = gtm.game_id
LEFT JOIN game_tag t ON gtm.tag_id = t.tag_id
GROUP BY g.game_id, g.name_ko, g.genre, g.cover_url
ORDER BY g.game_id;
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
INSERT INTO game (name_ko, cover_url, genre, created_at)
VALUES ('리그 오브 레전드',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXxl8qtZUmIF9UWAtTTa5MrD-2jO6ebRmF0JeJnOPZqg&s=10',
        'MOBA', CURRENT_TIMESTAMP),
       ('발로란트',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTxF7v5hzZAOE8-E_QeE4dhQdL-AvjhWMaReMuNB0LuHA&s=10',
        'FPS', CURRENT_TIMESTAMP),
       ('오버워치 2', 'https://i.ytimg.com/vi/j9YXi0WohA4/sddefault.jpg', 'FPS', CURRENT_TIMESTAMP),
       ('PUBG: 배틀그라운드',
        'https://cdn1.epicgames.com/spt-assets/53ec4985296b4facbe3a8d8d019afba9/pubg-battlegrounds-16v1j.jpg', '배틀로얄',
        CURRENT_TIMESTAMP),
       ('에이펙스 레전드', 'https://sm.ign.com/ign_kr/screenshot/default/apex-rejeondeu-daepyoimiji_zz7m.jpg', '배틀로얄',
        CURRENT_TIMESTAMP),
       ('카운터 스트라이크 2',
        'https://i.namu.wiki/i/H9o0kxkkzc1d62R04YC-T0sSAqLGMcPdFjzk0k5Tfgca6wpktWCTpON42blnYRdJ2nX4U5RvRxTQJzAkvN-Rug.webp',
        'FPS', CURRENT_TIMESTAMP),
       ('레인보우 식스 시즈',
        'https://i.namu.wiki/i/uKCTy6-65w-Ze36L_3yiwa00VxnPHV9sqZFYqaLeZ9eT8VCeAV6apQo0ted2zkF_Y8Lf0gdL5MDaYCqjouES7g.webp',
        'FPS', CURRENT_TIMESTAMP),
       ('마블 라이벌즈',
        'https://cdn1.epicgames.com/spt-assets/eb15454c010f4a748498cd3a62096a52/marvel-rivals-1ywtf.png?resize=1&w=480&h=270&quality=medium',
        'TPS', CURRENT_TIMESTAMP),
       ('포트나이트', 'https://cdn.gamemeca.com/data_center/305/870/20250515163647.jpg', '배틀로얄', CURRENT_TIMESTAMP),
       ('도타 2',
        'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/570/capsule_616x353.jpg?t=1769535998',
        'MOBA', CURRENT_TIMESTAMP),
       ('더 파이널스',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRG_aoLeec-dmbtyPoHZ6zx-uw9wTWmZThsAnAWuAPMn0GtJyDmPnufsQxL&s=10',
        'FPS', CURRENT_TIMESTAMP),
       ('델타 포스',
        'https://image.api.playstation.com/vulcan/ap/rnd/202504/2411/6d9e1ba85d2ae96e21eb6017c85b67ec1667c45c25cf7d1c.png',
        'FPS', CURRENT_TIMESTAMP),
       ('콜 오브 듀티: 워존',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTqD_D1PK8l9cprm7AhdKQvodEF2cLT69TPlcHqAYVh_ijyWOrIb_RJpNds&s=10',
        '배틀로얄', CURRENT_TIMESTAMP),
       ('배틀필드 2042',
        'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/1517290/capsule_616x353.jpg?t=1777324359',
        'FPS', CURRENT_TIMESTAMP),
       ('팀 포트리스 2',
        'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/440/capsule_616x353.jpg?t=1757348372',
        'FPS', CURRENT_TIMESTAMP),
       ('헬다이버즈 2',
        'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/553850/2a58d0a6c901e61cc6ad5a7cb488048a1e9f4809/header_koreana.jpg?t=1788431424',
        'TPS', CURRENT_TIMESTAMP),
       ('워프레임',
        'https://cdn1.epicgames.com/offer/244aaaa06bfa49d088205b13b9d2d115/EGS_WarframeTheNewWarResistancePack_DigitalExtremes_DLC_S1_2560x1440-45dbf9291117d6a656170f08731bed62',
        'TPS', CURRENT_TIMESTAMP),
       ('데스티니 2',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSCz---gFv-axWzc-8O06dSSXAVr5_u21St54TSNRgrN2DKuLxb8i9BC5w&s=10',
        'FPS', CURRENT_TIMESTAMP),
       ('딥 락 갤럭틱',
        'https://i.namu.wiki/i/TElle9rF5323NwartBbrW_uwalqTuBdYFJuFHL_wSkEofA3iU5sFqwH1NhdNLsRE1dFSfSPL5WyOBRk4BLcvgQ.webp',
        'FPS', CURRENT_TIMESTAMP),
       ('레프트 4 데드 2',
        'https://cdn1.epicgames.com/offer/2c42520d342a46d7a6e0cfa77b4715de/EGS_DyingLightLeft4Dead2WeaponPack_Techland_DLC_S1_2560x1440-3d06fdd708dc14f0c9263a0ea9e6db41?resize=1&w=480&h=270&quality=medium',
        'FPS', CURRENT_TIMESTAMP),
       ('백 4 블러드', 'https://i.redd.it/ji0g9o8epxgg1.png', 'FPS', CURRENT_TIMESTAMP),
       ('레디 오어 낫',
        'https://i.namu.wiki/i/KcrWmrGeyDKSukw-Kg3MMeBg3SweO9wFfFI0G0JN3csAajC11L6969b4LCF9W1P9pUQ-1-F335lbSua8JyZcQw.webp',
        '택티컬 FPS', CURRENT_TIMESTAMP),
       ('데드 바이 데이라이트',
        'https://cdn1.epicgames.com/spt-assets/2b2299be8ae84d679d4dc57c55af1510/dead-by-daylight-6hqhj.jpg', '공포',
        CURRENT_TIMESTAMP),
       ('로스트아크', 'https://cdn.sisajournal-e.com/news/photo/first/201811/img_191321_1.png', 'MMORPG', CURRENT_TIMESTAMP),
       ('메이플스토리', 'https://cdn.gamevu.co.kr/news/photo/202305/26969_69156_3926.jpg', 'MMORPG', CURRENT_TIMESTAMP),
       ('던전앤파이터', 'https://ssl.nexon.com/s2/game/m_dnf/brand/common/meta_facebook.jpg', 'MORPG', CURRENT_TIMESTAMP),
       ('월드 오브 워크래프트',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTZKdaNOcH97t2L3GptyEubV-dFZQe274yuIoB8GqgnQg&s',
        'MMORPG', CURRENT_TIMESTAMP),
       ('파이널 판타지 XIV', 'https://img2.quasarzone.com/editor/2023/09/07/7aa9750838c594160eaf1d8bfa606d30.png', 'MMORPG',
        CURRENT_TIMESTAMP),
       ('검은사막',
        'https://s1.pearlcdn.com/KR/Upload/Manager/MetaTag/fd0733fc3e520260729102139646.jpg?v=639226780476572155',
        'MMORPG', CURRENT_TIMESTAMP),
       ('길드워 2',
        'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/1284210/9f7e5a2965f5bbf99cec82c798305a27967ac837/capsule_616x353.jpg?t=1782483330',
        'MMORPG', CURRENT_TIMESTAMP),
       ('마인크래프트',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRbeDALEtXNlNTtDQLZ2c33FCbinE2HtyfM-tbDz7_JgL8JvXxfhzZdU2w&s=10',
        '샌드박스', CURRENT_TIMESTAMP),
       ('로블록스', 'https://images.rbxcdn.com/5348266ea6c5e67b19d6a814cbbb70f6.jpg', '샌드박스', CURRENT_TIMESTAMP),
       ('러스트', 'https://cdn.gamemeca.com/data_center/229/893/20210602100448.jpg', '서바이벌', CURRENT_TIMESTAMP),
       ('팰월드',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcThD4QqkV5WDgvdjjF_V9Po48Ww1_MYDJVWTqplw7xO-9l226LEx73Xy3Zy&s=10',
        '서바이벌', CURRENT_TIMESTAMP),
       ('프로젝트 좀보이드',
        'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/108600/header.jpg?t=1787740093', '서바이벌',
        CURRENT_TIMESTAMP),
       ('테라리아', 'https://terraria.wiki.gg/images/Day.jpg?a82c4f', '샌드박스', CURRENT_TIMESTAMP),
       ('스타듀 밸리',
        'https://img1.kakaocdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/MMT/image/0yFMOCOwGCWKgcp92gZ89TimJDM.jpg',
        '생활', CURRENT_TIMESTAMP),
       ('돈 스타브 투게더',
        'https://store.nintendo.co.kr/media/catalog/product/cache/69fca11704444074de0ecc4f2e43f422/0/8/081425533fd5a75ad96eb4333f260edf690b91110402ac801e07fd332035da8e.jpg',
        '서바이벌', CURRENT_TIMESTAMP),
       ('발헤임', 'https://img.boardlife.co.kr/data/photo/2024/10/10/1728500902-953011_w300.jpg', '서바이벌',
        CURRENT_TIMESTAMP),
       ('래프트', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/648800/header.jpg?t=1727184011',
        '서바이벌', CURRENT_TIMESTAMP),
       ('그라운디드', 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/962130/header.jpg?t=1768264530',
        '서바이벌', CURRENT_TIMESTAMP),
       ('노 맨즈 스카이', 'https://cdn.011st.com/11dims/resize/1000x1000/quality/75/11src/product/3280967339/B.jpg?184000000',
        '오픈월드', CURRENT_TIMESTAMP),
       ('씨 오브 시브즈', 'https://sm.ign.com/ign_kr/game/s/ssi-obeu-s/ssi-obeu-ssibeujeu_sbm8.jpg', '오픈월드',
        CURRENT_TIMESTAMP),
       ('몬스터 헌터 와일즈',
        'https://image.api.playstation.com/vulcan/ap/rnd/202409/0506/aa5c40ba185302dfcc88edc276a876fdc6c516c4db07ec9d.png',
        '액션 RPG', CURRENT_TIMESTAMP),
       ('몬스터 헌터: 월드',
        'https://i.namu.wiki/i/Mw85tEHuwKqHP6OBdry0kU1pGwzoZAtQEkD0hBKqM-7bXBS26-ik2EryYiCiaGNoBzSrnZJ1pGq47xAxx-1VAw.webp',
        '액션 RPG', CURRENT_TIMESTAMP),
       ('엘든 링',
        'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/1245620/capsule_616x353.jpg?t=1787868578',
        '액션 RPG', CURRENT_TIMESTAMP),
       ('EA SPORTS FC 26',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS4YtHkf9MbLxgo6SBQhAoEX5tkjpn_dIH171DHST-g0si0G3ro1VhcjKU&s=10',
        '스포츠', CURRENT_TIMESTAMP),
       ('FC 온라인', 'https://img2.yna.co.kr/etc/inner/KR/2023/09/21/AKR20230921134400017_01_i_P4.jpg', '스포츠',
        CURRENT_TIMESTAMP),
       ('로켓 리그',
        'https://cdn1.epicgames.com/offer/9773aa1aa54f4f7b80e44bef04986cea/EGS_RocketLeague_PsyonixLLC_S1_2560x1440-1a37e26b20fb4f3ebd825e64bc7914eb',
        '스포츠', CURRENT_TIMESTAMP),
       ('철권 8', 'https://image.api.playstation.com/vulcan/ap/rnd/202212/2101/CxnQHVI3gzboBTHqEEQVWZJ6.png', '격투',
        CURRENT_TIMESTAMP),
       ('마비노기 모바일', 'https://lwi.nexon.com/m_mabinogim/teaser/meta.jpg', 'MMORPG', CURRENT_TIMESTAMP),
       ('킹 오브 파이터즈 15',
        'https://cdn2.unrealengine.com/egs-thekingoffightersxvstandardedition-snkcorporation-bundles-g1a-00-1920x1080-5500f4d04c31.jpg',
        '격투', CURRENT_TIMESTAMP);

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

-- 킹 오브 파이터즈 15
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
         JOIN game_tag t ON t.name IN ('PVP', 'COMPETITIVE')
WHERE g.name_ko = '킹 오브 파이터즈 15';

-- =========================
-- 4. 확인용 조회
-- =========================
SELECT g.game_id,
       g.name_ko,
       g.genre,
       g.cover_url,
       GROUP_CONCAT(t.name ORDER BY t.name SEPARATOR ', ') AS tags
FROM game g
         LEFT JOIN game_tag_map gtm ON g.game_id = gtm.game_id
         LEFT JOIN game_tag t ON gtm.tag_id = t.tag_id
GROUP BY g.game_id, g.name_ko, g.genre, g.cover_url
ORDER BY g.game_id;
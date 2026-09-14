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
-- 2. GAME (51개) + 게임 설명 추가
-- =========================
INSERT INTO game (name_ko, cover_url, genre, description, created_at)
VALUES
    ('리그 오브 레전드',
     'https://store-images.s-microsoft.com/image/apps.18996.14127010465288187.f9de4a96-0ee4-4da3-bf66-d4132b38c599.caf661a7-e0b3-492d-b91b-63627e47283e',
     'MOBA',
     '140여 명의 전설적인 챔피언들과 함께 전장을 지배하는 영웅이 되어보세요.',
     CURRENT_TIMESTAMP),

    ('발로란트',
     'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTxF7v5hzZAOE8-E_QeE4dhQdL-AvjhWMaReMuNB0LuHA&s=10',
     'FPS',
     '찰나의 순발력과 정교한 요원 스킬 활용으로 승부욕을 불태워보세요.',
     CURRENT_TIMESTAMP),

    ('오버워치 2',
     'https://blz-contentstack-images.akamaized.net/v3/assets/bltf408a0557f4e4998/blt030bf3d606661d3c/633f5be164fe5a7d4481a16c/overwatch-section1-feature1.png',
     'FPS',
     '하이템포 팀 전투와 시원한 궁극기 연계로 전장을 지배하는 영웅이 되어보세요.',
     CURRENT_TIMESTAMP),

    ('PUBG: 배틀그라운드',
     'https://cdn1.epicgames.com/spt-assets/53ec4985296b4facbe3a8d8d019afba9/pubg-battlegrounds-16v1j.jpg',
     '배틀로얄',
     '좁아지는 자기장 속, 최후의 승자가 되기 위해 펼쳐지는 치열한 생존 교전',
     CURRENT_TIMESTAMP),

    ('에이펙스 레전드',
     'https://sm.ign.com/ign_kr/screenshot/default/apex-rejeondeu-daepyoimiji_zz7m.jpg',
     '배틀로얄',
     '스피디한 기동과 화려한 고유 스킬로 한시도 눈을 뗄 수 없는 배틀로얄 전장 속으로',
     CURRENT_TIMESTAMP),

    ('카운터 스트라이크 2',
     'https://i.namu.wiki/i/H9o0kxkkzc1d62R04YC-T0sSAqLGMcPdFjzk0k5Tfgca6wpktWCTpON42blnYRdJ2nX4U5RvRxTQJzAkvN-Rug.webp',
     'FPS',
     '0.1초의 반응속도와 정교한 샷감으로 전 세계를 사로잡은 명작 정통 슈터',
     CURRENT_TIMESTAMP),

    ('레인보우 식스 시즈',
     'https://i.namu.wiki/i/uKCTy6-65w-Ze36L_3yiwa00VxnPHV9sqZFYqaLeZ9eT8VCeAV6apQo0ted2zkF_Y8Lf0gdL5MDaYCqjouES7g.webp',
     'FPS',
     '벽을 부수고 전술을 지휘하며 펼치는 치열하고 정교한 건물 침투 작전',
     CURRENT_TIMESTAMP),

    ('마블 라이벌즈',
     'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSrZD_GR4rzn5gWpcdOTam4Ek2tgOacNHW49LO8kDwqb18bVnEYZ79pXAU&s=10',
     'TPS',
     '마블 히어로들의 파괴적인 스킬이 교차하며 전장을 뒤흔드는 화려한 액션',
     CURRENT_TIMESTAMP),

    ('포트나이트',
     'https://cdn.gamemeca.com/data_center/305/870/20250515163647.jpg',
     '배틀로얄',
     '실시간 구조물 건설과 폭발적인 사격이 결합된 역동적인 경쟁의 세계로',
     CURRENT_TIMESTAMP),

    ('도타 2',
     'https://gfn.co.kr/en/games/media/images/wide_art_image-dota-2-91596497.original.jpg',
     'MOBA',
     '수백 가지 자원 관리와 날카로운 판세 읽기로 상대를 제압하는 전략의 정수',
     CURRENT_TIMESTAMP),

    ('더 파이널스',
     'https://dszw1qtcnsa5e.cloudfront.net/community/20250403/b5e54866-ebf2-48a0-99ae-5c1239bdc7f7/Main.png',
     'FPS',
     '전장의 모든 환경을 파괴하고 경기장을 재구성하며 화력을 쏟아부으세요.',
     CURRENT_TIMESTAMP),

    ('델타 포스',
     'https://image.api.playstation.com/vulcan/ap/rnd/202504/2411/6d9e1ba85d2ae96e21eb6017c85b67ec1667c45c25cf7d1c.png',
     'FPS',
     '대규모 전장에서 펼쳐지는 실시간 화력전과 긴장감 넘치는 현대 전면전 속으로',
     CURRENT_TIMESTAMP),

    ('콜 오브 듀티: 워존',
     'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTqD_D1PK8l9cprm7AhdKQvodEF2cLT69TPlcHqAYVh_ijyWOrIb_RJpNds&s=10',
     '배틀로얄',
     '스피디한 교전 피드백과 본능적인 사격의 쾌감이 폭발하는 극한의 전장 속으로',
     CURRENT_TIMESTAMP),

    ('배틀필드 2042',
     'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/1517290/capsule_616x353.jpg?t=1777324359',
     'FPS',
     '128인 대규모 전장에서 펼쳐지는 혼돈과 화력전의 압도적 스케일을 경험하세요.',
     CURRENT_TIMESTAMP),

    ('팀 포트리스 2',
     'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/440/capsule_616x353.jpg?t=1757348372',
     'FPS',
     '개성 넘치는 클래스들이 어우러져 유쾌하게 유대감을 다지는 유쾌한 팀 슈터',
     CURRENT_TIMESTAMP),

    ('헬다이버즈 2',
     'https://image.api.playstation.com/vulcan/ap/rnd/202608/0517/700bcf4d370673f3b7ee5c02a8b1e5b7e65a59ddfe829f18.png',
     'TPS',
     '은하계의 평화를 위해 동료들과 파티를 이루어 외계 위협에 맞서세요.',
     CURRENT_TIMESTAMP),

    ('워프레임',
     'https://cdn1.epicgames.com/offer/244aaaa06bfa49d088205b13b9d2d115/EGS_WarframeTheNewWarResistancePack_DigitalExtremes_DLC_S1_2560x1440-45dbf9291117d6a656170f08731bed62',
     'TPS',
     '그 누구도 멈출 수 없는 강력한 전사로 깨어나 동료들과 함께 싸움에 임하세요.',
     CURRENT_TIMESTAMP),

    ('데스티니 2',
     'https://store-images.s-microsoft.com/image/apps.35038.68655995542193491.1f117385-8144-4300-8086-7d58ce4fd599.7eca0919-f914-437e-9a02-09aa0a19a976',
     'FPS',
     '인류 최후의 도시를 지키는 수호자가 되어 별빛을 따라가 어둠에 맞서세요.',
     CURRENT_TIMESTAMP),

    ('딥 락 갤럭틱',
     'https://i.namu.wiki/i/TElle9rF5323NwartBbrW_uwalqTuBdYFJuFHL_wSkEofA3iU5sFqwH1NhdNLsRE1dFSfSPL5WyOBRk4BLcvgQ.webp',
     'FPS',
     '4명의 드워프 동료들과 깊은 미로를 파헤치며 싹트는 뜨거운 동료애',
     CURRENT_TIMESTAMP),

    ('레프트 4 데드 2',
     'https://cdn1.epicgames.com/offer/2c42520d342a46d7a6e0cfa77b4715de/EGS_DyingLightLeft4Dead2WeaponPack_Techland_DLC_S1_2560x1440-3d06fdd708dc14f0c9263a0ea9e6db41',
     'FPS',
     '등 뒤를 맡길 수 있는 동료들과 함께 위기를 헤쳐나가는 협동 슈터의 대명사',
     CURRENT_TIMESTAMP),

    ('백 4 블러드',
     'https://i.redd.it/ji0g9o8epxgg1.png',
     'FPS',
     '서로의 능력을 보완하고 전략을 더해 감염체의 위협을 극복해 나가세요.',
     CURRENT_TIMESTAMP),

    ('레디 오어 낫',
     'https://store-images.s-microsoft.com/image/apps.39640.13578379328545234.9a5c7815-319e-44dd-b243-b580c45874f3.35017ade-207d-4d87-811e-3e41624595ad',
     '택티컬 FPS',
     '한 치의 오차도 허용하지 않는 정교한 전술과 엄격한 절제의 SWAT 시뮬레이션',
     CURRENT_TIMESTAMP),

    ('데드 바이 데이라이트',
     'https://cdn1.epicgames.com/spt-assets/2b2299be8ae84d679d4dc57c55af1510/dead-by-daylight-6hqhj.jpg',
     'HORROR',
     '어둠 속 살인마의 시선을 피해 숨 막히는 심리전을 펼치는 비대칭 공포 술래잡기',
     CURRENT_TIMESTAMP),

    ('로스트아크',
     'https://cdn.sisajournal-e.com/news/photo/first/201811/img_191321_1.png',
     'MMORPG',
     '항해와 내실, 화려한 레이드까지 끊임없이 성장하는 아크라시아 모험기',
     CURRENT_TIMESTAMP),

    ('메이플스토리',
     'https://dszw1qtcnsa5e.cloudfront.net/community/20260723/a1b44ccc-765f-41a1-9e70-f7aba5e0f3b6/%EB%84%A5%EC%8A%A8%EB%B3%B4%EB%8F%84%EC%9E%90%EB%A3%8C%EB%84%A5%EC%8A%A8%EB%A9%94%EC%9D%B4%ED%94%8C%EC%8A%A4%ED%86%A0%EB%A6%AC%EB%AA%A8%EB%93%A0%EC%A7%81%EC%97%85%EC%97%90%EC%84%B8%EB%B2%88%EC%A7%B86%EC%B0%A8%EC%8A%A4%ED%82%AC%EC%BD%94%EC%96%B4%EC%B6%94%EA%B0%80.jpg',
     'MMORPG',
     '끊임없는 즐거움, 스스로 만들어 나가는 이야기, 상상 속의 단풍 세계 속으로',
     CURRENT_TIMESTAMP),

    ('던전앤파이터',
     'https://ssl.nexon.com/s2/game/m_dnf/brand/common/meta_facebook.jpg',
     'MMORPG',
     '다양한 직업과 시원한 액션, 파밍의 쾌감이 끝없이 펼쳐지는 아라드로 떠나보세요.',
     CURRENT_TIMESTAMP),

    ('월드 오브 워크래프트',
     'https://blz-contentstack-images.akamaized.net/v3/assets/blt9c12f249ac15c7ec/bltb5a24e5ab1e2cfb0/6a88e3589b942efdb6f74110/wow-thumbnail-homepage.jpg',
     'MMORPG',
     '신화와 마법, 끊임없는 모험이 있는 온라인 세계에서 수백만의 강력한 영웅들과 함께',
     CURRENT_TIMESTAMP),

    ('파이널 판타지 XIV',
     'https://static.ff14.co.kr/Contents/2026/04/F9D2D30355AF70A7ADC409397060572660831C7709A937CDE0332811DC508D9E.jpg',
     'MMORPG',
     '환상의 절경 속에서 보고 듣고 느끼는 아름다운 판타지 세계 속으로',
     CURRENT_TIMESTAMP),

    ('검은사막',
     'https://s1.pearlcdn.com/KR/Upload/Manager/MetaTag/fd0733fc3e520260729102139646.jpg?v=639226780476572155',
     'MMORPG',
     '전투부터 생활까지, 광활한 오픈월드에서 내 뜻대로 당신만의 길을 개척하세요.',
     CURRENT_TIMESTAMP),

    ('길드워 2',
     'https://blogfiles.ncsoft.net/news/f7060bea-23cd-43cf-b38d-4638db4d66c3.jpg',
     'MMORPG',
     '발길이 닿는 모든 곳이 새로운 전설이 되는 역동적인 타이리아 대륙을 탐험하세요.',
     CURRENT_TIMESTAMP),

    ('마인크래프트',
     'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRbeDALEtXNlNTtDQLZ2c33FCbinE2HtyfM-tbDz7_JgL8JvXxfhzZdU2w&s=10',
     '샌드박스',
     '블록 하나로 시작해 내 상상 속의 세상을 마음껏 구축해 보세요.',
     CURRENT_TIMESTAMP),

    ('로블록스',
     'https://images.rbxcdn.com/5348266ea6c5e67b19d6a814cbbb70f6.jpg',
     '샌드박스',
     '무한한 공간, 수많은 유저가 직접 창작한 가상 세계에서 당신만의 상상을 현실로',
     CURRENT_TIMESTAMP),

    ('러스트',
     'https://image.api.playstation.com/vulcan/ap/rnd/202507/1713/bf40a44f89e407a38416835514cfbe3a27a464077db140d2.png',
     '서바이벌',
     '가혹한 환경과 예측할 수 없는 타인들 사이에서 오직 본능으로 버텨내세요.',
     CURRENT_TIMESTAMP),

    ('팰월드',
     'https://store-images.s-microsoft.com/image/apps.49778.13654268679289325.ececb946-5639-4e77-b347-9d188d4e7e02.9102215b-349a-4f6d-b9a3-2c14908481f1',
     '서바이벌',
     '미지의 섬에 불시착해 독특한 생명체들과 공존하며 개척해나가는 생존 모험',
     CURRENT_TIMESTAMP),

    ('프로젝트 좀보이드',
     'https://gfn.co.kr/en/games/media/images/art_image-project-zomboid-ae84fdb9.original.jpg',
     '서바이벌',
     '언제 도달할지 모르는 위협 속, 고독과 철저한 현실감이 돋보이는 서바이벌',
     CURRENT_TIMESTAMP),

    ('테라리아',
     'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTLke8u5RC9yrTt7apg8YA1TPR4C_DIPlnd2YphTS7r5w&s=10',
     '샌드박스',
     '땅을 파고 미지의 지하를 탐험하며 거대 보스에 맞서는 2D 샌드박스 세계로',
     CURRENT_TIMESTAMP),

    ('스타듀 밸리',
     'https://img1.kakaocdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/MMT/image/0yFMOCOwGCWKgcp92gZ89TimJDM.jpg',
     'CASUAL',
     '씨앗을 심고 인연을 가꾸며 일상의 따스한 성취감을 느끼는 귀농 라이프',
     CURRENT_TIMESTAMP),

    ('돈 스타브 투게더',
     'https://image.api.playstation.com/vulcan/ap/rnd/202505/2800/eaaaa9b1d01262514c0574fe5c3122592168200523e51c48.png',
     '서바이벌',
     '기괴하고 독특한 어둠 속에서 자원을 관리하며 하루하루 버텨내는 생존',
     CURRENT_TIMESTAMP),

    ('발헤임',
     'https://gfn.co.kr/en/games/media/images/wide_art_image-valheim-6c4086c1.original.jpg',
     '서바이벌',
     '북유럽 신화 바탕의 미지의 사후 세계를 개척하고 오딘의 인정을 받는 바이킹 생존기',
     CURRENT_TIMESTAMP),

    ('래프트',
     'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/648800/capsule_616x353.jpg?t=1727184011',
     '서바이벌',
     '망망대해 위의 작은 뗏목 하나에 의지하며 표류물을 수집해 살아남으세요.',
     CURRENT_TIMESTAMP),

    ('그라운디드',
     'https://image.xportsnews.com/contents/images/upload/article/2022/0929/1664426527865607.jpg',
     '서바이벌',
     '곤충 크기로 줄어든 몸으로 위험 천만한 뒷마당을 헤쳐 나가는 생존 모험',
     CURRENT_TIMESTAMP),

    ('노 맨즈 스카이',
     'https://cdn.011st.com/11dims/resize/1000x1000/quality/75/11src/product/3280967339/B.jpg?184000000',
     '오픈월드',
     '무한한 우주 속 미지의 행성들을 최초로 발굴하며 지평을 넓혀가세요.',
     CURRENT_TIMESTAMP),

    ('씨 오브 시브즈',
     'https://sm.ign.com/ign_kr/game/s/ssi-obeu-s/ssi-obeu-ssibeujeu_sbm8.jpg',
     '오픈월드',
     '바다를 항해하며 보물을 찾고 동료들과 함께 위대한 해적 전설을 써 내려가세요.',
     CURRENT_TIMESTAMP),

    ('몬스터 헌터 와일즈',
     'https://image.api.playstation.com/vulcan/ap/rnd/202409/0506/aa5c40ba185302dfcc88edc276a876fdc6c516c4db07ec9d.png',
     '액션 RPG',
     '살아 움직이는 거대한 자연 속에서 생존과 수렵의 미학을 경험해 보세요.',
     CURRENT_TIMESTAMP),

    ('몬스터 헌터: 월드',
     'https://image.api.playstation.com/vulcan/img/rnd/202010/0106/IyY3JSzHNCVoh7FultMPaE8F.jpg',
     '액션 RPG',
     '몬스터를 수렵하고 장비를 정비하며 한 단계씩 성장하는 완성형 액션 속으로',
     CURRENT_TIMESTAMP),

    ('엘든 링',
     'https://image.api.playstation.com/vulcan/ap/rnd/202501/3109/33315c8299ef46040d85135ae53dea6704de098a2f62ecc2.jpg',
     '액션 RPG',
     '수많은 시련을 극복하고 미지의 위협 끝에서 마침내 왕으로 거듭나는 서사시',
     CURRENT_TIMESTAMP),

    ('EA SPORTS FC 26',
     'https://cdn1.epicgames.com/offer/1d4d85b1051e41ee8f1a099e99d59f3f/EGS_EASPORTSFC26StandardEdition_EACANADA_S1_2560x1440-95509287c0fc4a888e57ccaedf2a2a87',
     '스포츠',
     '세계 최고의 선수들과 정밀한 컨트롤로 펼치는 하이퍼 리얼리즘 피치 속으로',
     CURRENT_TIMESTAMP),

    ('FC 온라인',
     'https://i.ytimg.com/vi/j8rh4W3gPAI/maxresdefault.jpg',
     '스포츠',
     '정밀한 패스 타임과 나만의 전술로 꿈의 팀을 완성해 나가는 축구 시뮬레이션',
     CURRENT_TIMESTAMP),

    ('로켓 리그',
     'https://cdn1.epicgames.com/offer/9773aa1aa54f4f7b80e44bef04986cea/EGS_RocketLeague_PsyonixLLC_S1_2560x1440-1a37e26b20fb4f3ebd825e64bc7914eb',
     '스포츠',
     '정교한 물리 법칙과 차에 대한 세밀한 컨트롤이 만들어내는 독특한 스포츠',
     CURRENT_TIMESTAMP),

    ('철권 8',
     'https://image.api.playstation.com/vulcan/ap/rnd/202212/2101/CxnQHVI3gzboBTHqEEQVWZJ6.png',
     '격투',
     '치열한 수싸움과 주먹의 타격감이 한계까지 몰입시키는 극한의 1v1 대전',
     CURRENT_TIMESTAMP),

    ('마비노기 모바일',
     'https://lwi.nexon.com/m_mabinogim/teaser/meta.jpg',
     'MMORPG',
     '모닥불 앞의 여유와 새로운 모험이 공존하는 감성적인 판타지 라이프 속으로',
     CURRENT_TIMESTAMP),

    ('킹 오브 파이터즈 15',
     'https://cdn2.unrealengine.com/egs-thekingoffightersxvstandardedition-snkcorporation-bundles-g1a-00-1920x1080-5500f4d04c31.jpg',
     '격투',
     '화려한 콤보 연출과 신나는 타격감으로 뜨거운 대전의 열기 속으로',
     CURRENT_TIMESTAMP),

    ('이터널 리턴',
     'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/1049590/67ac60cfb31b42f5a420e641eacbaa8a63d99fd6/page_bg_raw.jpg?t=1779936416',
     'MOBA',
     '세상과는 떨어진 섬 루미아에서 생존에 필요한 무기와 방어구를 제작해 팀원과 생존해나가세요.',
     CURRENT_TIMESTAMP),

    ('Overcooked!',
     'https://image.api.playstation.com/vulcan/img/rnd/202011/2700/hKBAhyUmI0fv5JrDxITp5rRJ.png',
     'PARTY',
     '',
     CURRENT_TIMESTAMP),

    ('피코 파크',
     'https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/1509960/ss_d54c6fd82d8c804939d136356eda364295a788cd.1920x1080.jpg?t=1740033204',
     'CASUAL',
     ' 한 명이라도 협조하지 않으면 깰 수 없는 맵 속에서 피어나는 극강의 팀워크.',
     CURRENT_TIMESTAMP),

    ('Human: Fall Flat',
     'https://image.api.playstation.com/vulcan/ap/rnd/202602/2414/8397d69b6a24f42f0357ffee13e53cc2535493b0692f4f76.jpg',
     'CASUAL',
     '부유하는 초현실적 세계 속에서 모험을 펼치는 물리기반 플랫폼 게임',
     CURRENT_TIMESTAMP),

    ('Gang Beasts',
     'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTYONrq4P1-OlcseY3tl1hBgJKPQtMR1hrnTixhKg6M9Q&s=10',
     'PARTY',
     '흐물거리는 몸으로 친구들을 밀치고 던지고 그리고 승리하세요.',
     CURRENT_TIMESTAMP),

    ('Portal 2',
     'https://gfn.co.kr/en/games/media/images/screenshot-portal-2-d5b10a75.original.jpg',
     'COOP',
     '공간의 물리 법칙을 이용하고 구조를 파악하면서 포탈건으로 연구소를 탈출하세요.',
     CURRENT_TIMESTAMP);

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

-- 이터널 리턴
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
         JOIN game_tag t ON t.name IN ('PVP', 'TEAM', 'COMPETITIVE', 'MOBA')
WHERE g.name_ko = '이터널 리턴';

-- 오버쿡드
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
         JOIN game_tag t ON t.name IN ('COOP', 'CASUAL', 'PARTY')
WHERE g.name_ko = '오버쿡드!';

-- 피코 파크
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
         JOIN game_tag t ON t.name IN ('COOP', 'CASUAL', 'PARTY')
WHERE g.name_ko = '피코 파크';

-- 휴먼 폴 플랫
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
         JOIN game_tag t ON t.name IN ('COOP', 'CASUAL', 'PARTY')
WHERE g.name_ko = '휴먼 폴 플랫';

-- 갱비스트
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
         JOIN game_tag t ON t.name IN ('COOP', 'CASUAL', 'PARTY')
WHERE g.name_ko = '갱비스트';

-- 포탈2
INSERT INTO game_tag_map (game_id, tag_id)
SELECT g.game_id, t.tag_id
FROM game g
         JOIN game_tag t ON t.name IN ('PUZZLE', 'COOP', 'PARTY')
WHERE g.name_ko = '포탈2';

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
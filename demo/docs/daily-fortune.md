# 게임 테마 오늘의 운세

## API
`POST /api/users/me/fortunes/today` (요청 본문 없음, Bearer JWT 필요).
기존 ApiResponse의 data에 date, overallFortune, gameFortunes, dailyQuest, oneLineMessage를 반환합니다.
gameId는 요청 시 DB에 있던 선호 게임 ID와 정확하게 대응합니다.
점수(0~100 정수)와 모든 문구는 Gemini가 생성합니다. 보상은 연출용이며 실제 지급하지 않습니다.

- 200: 최초 생성 또는 저장 결과 반환.
- 401: 인증 없음.
- 409: 사주 계산 필요, 생성 중, 또는 이전 형식 운세 등 상태 충돌.
- 502: 사주 API/Gemini 실패 또는 잘못된 응답.
- 503: Gemini 미설정 또는 이미 실패한 당일 생성 시도.

생성 중이면 프론트는 잠시 후 같은 API를 다시 요청할 수 있습니다.
저장된 결과가 있으면 사주/게임 재조회와 외부 API 호출을 생략합니다.
당일 사주·선호 게임 변경은 이미 저장된 결과에 영향을 주지 않습니다.
선호 게임이 없으면 gameFortunes는 빈 배열입니다.
날짜는 요청 시작 시 Asia/Seoul 자정 기준으로 확정하며, 호출 도중 자정을 넘어도 바뀌지 않습니다.

## 실행 설정
Spring Config Import로 실행 디렉터리의 ../.env, ./.env를 properties 형식으로 읽습니다.
IDE가 별도로 환경변수를 주입해도 됩니다. 환경변수가 파일보다 우선합니다.
GEMINI_API_KEY=키값 (중괄호·export·따옴표 없이) 형식을 사용하세요.
키 자체는 Git에 추가하지 않습니다. .env는 기존 .gitignore에서 제외합니다.
GEMINI_MODEL로 모델을 교체할 수 있으며 기본값은 gemini-3.6-flash입니다.
2026-09-08 실제 호출에서 gemini-2.5-flash는 목록에 표시돼도 신규 사용자 생성 요청에 404를 반환했습니다.
제공자 응답이 안내한 대체 모델로 변경했습니다.
기존 DB_USERNAME, DB_PASSWORD, JWT_SECRET, SAJU_BASE_URL, SAJU_API_KEY도 필요합니다.
Gemini 연결/응답 타임아웃은 5초/60초, 사주 API는 5초/15초입니다.
실제 계정에서 모델 사용 가능 여부는 배포 전에 확인해야 합니다.

## DB 적용
기존 DB에는 src/main/resources/db/migration/V2__add_fortune_generation.sql을 수동 적용하세요.
Flyway 자동 실행은 구성되어 있지 않습니다.
response_json JSON 컬럼과 fortune_generation 테이블을 추가하며 기존 game_fortune 텍스트는 보존합니다.
기존 행에 response_json이 없으면 자동으로 덮어쓰지 않고 409를 반환합니다.
필요하면 운영자가 기존 데이터의 형식을 확인한 뒤 별도로 변환해야 합니다.

DB_INIT_MODE의 기본값은 never입니다. 재시작 시 schema.sql이 기존 데이터를 삭제하지 않도록 변경했습니다.
완전히 새로운 개발용 DB에서만 DB_INIT_MODE=always로 초기화할 수 있습니다.
schema.sql은 기존 테이블을 DROP하므로, 초기화 후 반드시 never로 되돌리세요.
운영 DB에서는 schema.sql을 실행하지 마세요.

## 생성 상태와 장애
fortune_generation의 PRIMARY KEY(user_id, fortune_date)로 여러 서버에서도 한 요청만 생성 권한을 얻습니다.
상태는 PROCESSING -> SUCCEEDED 또는 FAILED입니다.
Gemini 호출 중에는 DB 트랜잭션/행 잠금을 유지하지 않습니다.
검증된 운세 저장과 SUCCEEDED 변경만 짧은 트랜잭션으로 묶습니다.

실패한 당일 시도는 자동 재실행하지 않습니다. 요청 타임아웃 이후에도 제공자에서 호출이
처리되었을 수 있기 때문입니다. 외부 호출의 절대적인 exactly-once를 보장하는 것은 아닙니다.
프로세스 강제 종료로 PROCESSING이 남을 수도 있습니다. updated_at으로 오래된 시도를 찾고
실행 중인 요청이 없음을 확인한 후 운영자가 FAILED로 전환하세요.
사용자별 재시도 API나 자동 만료 후 재생성은 제공하지 않습니다.
운영자가 수동으로 생성 행을 삭제해 재시도시키면 중복 과금 가능성이 있으므로 원 호출 상태를 먼저 확인해야 합니다.
DB 장애로 실패 상태 기록이 실패하면 PROCESSING이 남을 수 있습니다.

## 분석 기준 (서비스용 규칙 v1)
저장된 오행 count를 그대로 사용하고 비율로 재해석하지 않습니다.
최댓값 동률 전체가 strongElements, 최솟값 동률 전체가 weakElements입니다.
오차 0.000001 이내 동일한 분포는 두 배열을 비워 균형 상태로 처리합니다.
음수/비유한값/합계 0은 거부합니다. 출생시각 미상의 부분 사주 수치도 합계 0이 아니면 그대로 사용합니다.
강·약은 오행 분포의 상대적인 이름이며 전통 명리의 신강·신약/용신 판단을 의미하지 않습니다.

오늘 간지는 기존 /v1/saju/calculate에 오늘의 양력 날짜, 시각 null, 고정 gender=male을 보내
day_pillar만 추출합니다. 사용자 생년월일을 재전송하지 않습니다.
성별은 기존 API 필수 필드를 채우는 값이며 일진 해석에 사용하지 않습니다.
이 API가 오늘 날짜와 시각 미상을 허용하고 동일 날짜의 일주를 일관되게 반환하는지는
실제 외부 API 환경에서 확인해야 합니다. 응답을 추측하거나 임의 간지로 대체하지 않습니다.
천간과 지지의 대표 오행을 중복 없이 mainElements로 사용합니다(지장간 가중치 미사용).
오늘 데이터는 인스턴스별 최근 한 날짜만 캐시합니다.

- 오늘 오행이 약한 오행과 같거나 그것을 생하면 보완/준비/점진적 시도.
- 오늘 오행이 약한 오행을 극하면 점검/휴식/무리한 시도 주의.
- 오늘 오행이 강한 오행과 같거나 그것을 생하면 과확장/페이스 조절 주의.
- 강한 오행이 오늘 오행을 생하면 익숙한 역할/꾸준한 실행.
- 해당 요소가 없으면 차분한 준비와 페이스 유지라는 중립 문구.
AI는 이 analysis를 우선하고, 게임 태그로 판단을 바꾸지 않습니다.
실제 문구의 의미적 일관성과 재미는 별도 샘플 평가가 필요합니다.

## 검증
./gradlew test
단위/HTTP 모의 테스트에서는 실제 Gemini·사주 API를 호출하지 않습니다.
MySQL 통합 테스트는 MYSQL_TEST_URL, MYSQL_TEST_USERNAME, MYSQL_TEST_PASSWORD를 지정하면 실행합니다.
이 테스트는 전용 임시 스키마만 만들고 제거하며 application의 schema.sql을 실행하지 않습니다.

구현 시 전체 Gradle 테스트가 통과했고, 별도 임시 MySQL DB에서 운세 통합 테스트 4개도 통과했습니다.
2026-09-08 gemini-3.6-flash로 가상 사주·게임 데이터의 실제 생성과 JSON 검증이 통과했습니다.
외부 사주 API의 오늘 간지 응답은 이 테스트에서 호출하지 않습니다.
GeminiLiveSmokeTest는 GEMINI_LIVE_TEST=true와 GEMINI_API_KEY 환경변수를 명시할 때만 실행되며,
실제 생성 1회에 API 사용량이 발생합니다. 일반 테스트에서는 생략됩니다.

오류 진단 보완: 운세 API는 오류 시 code, message, errors 객체를 반환합니다.
errors.stage로 실패 단계를 구분하고, 외부 HTTP 실패는 errors.upstreamStatus에 상태 코드를 제공합니다.
GEMINI_HTTP_ERROR / SAJU_HTTP_ERROR는 외부 HTTP 오류,
GEMINI_CONNECTION_ERROR / SAJU_CONNECTION_ERROR는 연결·시간 초과,
FORTUNE_INVALID_JSON은 스키마·필드·점수 검증 실패입니다.
FORTUNE_PREVIOUS_ATTEMPT_FAILED는 이전 시도의 실패 상태이며 새로운 Gemini 호출은 하지 않습니다.
이전 오류 원문은 DB에 저장하지 않으므로 이번 변경으로 과거 실패 원인을 복구할 수 없습니다.
서버를 재시작해야 새 오류 응답이 적용됩니다.
로그에는 오류 코드·단계·외부 HTTP 상태만 기록하고 원문 응답, SQL, API 키는 기록하지 않습니다.
운세 컨트롤러 전용 예외 처리이며 다른 API의 오류 응답에는 영향을 주지 않습니다.

Gemini API 계약 참고:
https://ai.google.dev/api/generate-content
https://ai.google.dev/gemini-api/docs/generate-content/structured-output

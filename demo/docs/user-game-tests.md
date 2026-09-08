# 선호 게임 API 검증

`GET /api/users/me/games`는 JWT 로그인 사용자의 선호 게임을 `ApiResponse`로 반환한다.
`data`는 `gameId`, `name`, `coverUrl`, `genre`, `isMain`을 포함하는 배열이다.
대표 게임이 먼저 나오고 나머지는 등록 ID 순서이며, 등록 내역이 없으면 빈 배열이다.
이미지와 장르는 DB 값에 따라 null일 수 있다.

## 서비스·HTTP 테스트

demo 디렉터리에서 실행:

```powershell
.\gradlew.bat test --tests '*UserGame*Test'
```

중복 등록, 6번째 등록, 없는 게임, 입력 검증, 대표 변경·삭제, 사용자 잠금 순서와
잠금 실패, 조회 응답 필드, 미인증 요청의 HTTP 401을 검증한다.
성공 응답은 기존 ApiResponse를 사용하고 오류 응답은 기존 전역 예외 처리 및 SecurityConfig 규약을 유지한다.

## 실제 MySQL 통합 테스트

`MYSQL_TEST_URL`, `MYSQL_TEST_USERNAME`, `MYSQL_TEST_PASSWORD` 환경 변수를 설정한다.
URL 예시: `jdbc:mysql://localhost:3306/mysql?serverTimezone=Asia/Seoul`.
별도의 테스트 서버를 사용하며, 계정에는 데이터베이스 생성·삭제와 트리거 생성 권한이 필요하다.

```powershell
.\gradlew.bat test --tests '*UserGameMySqlIntegrationTest' --rerun-tasks
```

테스트는 무작위 이름의 `user_game_test_<UUID>` 스키마만 생성하고 종료 시 삭제한다.
애플리케이션의 schema.sql이나 기존 데이터베이스 테이블은 실행·수정하지 않는다.
강제 종료로 정리가 실행되지 않았다면 해당 실행에서 생성한 테스트 스키마를 확인해 정리한다.

실제 Mapper XML과 Spring 트랜잭션 프록시를 사용해 다음을 검증한다.

- 기존 4개에서 서로 다른 게임 동시 등록: 하나는 성공, 하나는 409, 최종 5개.
- 서로 다른 게임을 동시에 대표로 지정: 최종 대표 1개.
- 조회 필드 매핑, 대표 우선 정렬, 대표 삭제 후 미지정, 다른 사용자 데이터 보존.
- 대표 지정 SQL 실패 시 이전 대표 해제까지 롤백.

`MYSQL_TEST_URL`이 없으면 MySQL 테스트는 건너뛴다. 단위 테스트 통과만으로 실제 DB 동시성 검증이 완료된 것은 아니다.

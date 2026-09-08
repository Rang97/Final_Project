-- 개발 테스트용: 모델 설정 수정 및 서버 재시작 후 사용.
-- 아래 user_id/date를 확인하고 실행하세요. 실행 중인 PROCESSING과 성공한 운세는 삭제하지 않습니다.
-- 실패한 시도를 지우면 다음 요청은 실제 Gemini를 다시 호출합니다.
SET @retry_user_id = 1;
SET @retry_fortune_date = '2026-09-08';

DELETE fg FROM fortune_generation fg
WHERE fg.user_id = @retry_user_id
  AND fg.fortune_date = @retry_fortune_date
  AND fg.status = 'FAILED'
  AND NOT EXISTS (
      SELECT 1 FROM daily_fortune df
      WHERE df.user_id = fg.user_id AND df.fortune_date = fg.fortune_date
  );

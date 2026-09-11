import { useState } from "react";
import { getTodayFortune } from "../api/fortuneApi";

export function useTodayFortune() {
  const [fortune, setFortune] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchFortune = async () => {
    setIsLoading(true);
    setError(null);
    try {
      const data = await getTodayFortune();
      setFortune(data);
    } catch (err) {
      const status = err.response?.status;
      let message = "운세를 불러오지 못했습니다. 잠시 후 다시 시도해주세요.";
      if (status === 409) {
        message =
          "사주 정보가 없거나 이미 생성 중입니다. 오행 탭에서 사주를 먼저 계산해주세요.";
      } else if (status === 502) {
        message =
          "운세 생성 서버 응답에 문제가 발생했습니다. 잠시 후 다시 시도해주세요.";
      } else if (status === 503) {
        message = "운세 생성 기능이 일시적으로 사용 불가능합니다.";
      }
      setError({ status, message });
    } finally {
      setIsLoading(false);
    }
  };

  return { fortune, isLoading, error, fetchFortune };
}

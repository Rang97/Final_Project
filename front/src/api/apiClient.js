import axios from "axios";
import { useAuthStore } from "../store/authStore";

//axios 로 apiClient 객체를 생성
// (기본주소:백엔드 http://localhost:8080/api)
const apiClient = axios.create({
  baseURL: "http://localhost:8080/api",
  timeout: 5000,
  headers: {
    "Content-Type": "application/json",
  },
});

//요청 interceptor
// Access Token이 있으면 모든 API 요청에 자동으로 추가합니다.

apiClient.interceptors.request.use(
  (config) => {
    const accessToken = useAuthStore.getState().accessToken;
    if (accessToken) {
      config.headers.Authorization = `Bearer ${accessToken}`;
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  },
);

// 응답 interceptor
//저장된 토큰이 있는데 401이 발생하면 만료되거나 잘못된 토큰으로 판단하고 삭제합니다.

apiClient.interceptors.response.use(
  (response) => response,

  (error) => {
    const status = error.response?.status; //에러 상태코드 확인
    const requestUrl = error.config?.url ?? ""; //에러 요청주소 확인(있으면)
    //로그인 요청인지 확인 (참, 거짓)
    const isLoginRequest = requestUrl.includes("/auth/login");
    //jwt 엑세트 토근이 있는지 확인
    const hasAccessToken = Boolean(useAuthStore.getState().accessToken);

    if (status === 401 && !isLoginRequest && hasAccessToken) {
      useAuthStore.getState().clearAuth(); //만료된 토큰 또는 정식토큰이 아닌경우
    }

    console.log({
      status: error.response?.status,
      data: error.response?.data,
      url: error.config?.url,
      accessToken: useAuthStore.getState().accessToken,
    });

    return Promise.reject(error);
  },
);

export default apiClient;

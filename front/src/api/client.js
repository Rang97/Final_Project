import axios from "axios";

export const api = axios.create({
  baseURL: "/api",
  timeout: 5000,
  headers: {
    "Content-Type": "application/json",
  },
});

// 인터셉터: 요청 보낼 때 토큰 자동으로 붙이기
api.interceptors.request.use((config) => {
  // 로컬에서 토큰 꺼내 씀
  const token = localStorage.getItem("accessToken");
  if (token) {
    // 꺼낸 토큰 요청 헤더 자동 붙이기
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

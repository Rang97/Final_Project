import axios from "axios";
import { useAuthStore } from "../store/authStore";

export const api = axios.create({ baseURL: "/api", timeout: 15000 });
api.interceptors.request.use((config) => {
  const { token } = useAuthStore.getState();
  if (token && !config.publicRequest) config.headers.Authorization = `Bearer ${token}`;
  return config;
});
api.interceptors.response.use((response) => response, (error) => {
  const { token, clearSession } = useAuthStore.getState();
  if (error.response?.status === 401 && token &&
      error.config?.headers?.Authorization === `Bearer ${token}`) {
    clearSession("로그인이 만료되었습니다. 다시 로그인해 주세요.");
  }
  return Promise.reject(error);
});

export function authErrorMessage(error) {
  if (!error.response) return "서버에 연결할 수 없습니다. 잠시 후 다시 시도해 주세요.";
  if (error.response.status === 401) return "아이디 또는 비밀번호가 올바르지 않습니다.";
  const data = error.response.data;
  const fields = Object.values(data?.errors ?? {}).filter((value) => typeof value === "string");
  return fields.join(" ") || data?.message || "요청을 처리하지 못했습니다. 다시 시도해 주세요.";
}

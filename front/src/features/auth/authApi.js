import { api } from "../../api/client";
import { useAuthStore } from "../../store/authStore";

export const signup = async (values) => (await api.post("/auth/signup", values, { publicRequest: true })).data;
export const login = async (values) => (await api.post("/auth/login", values, { publicRequest: true })).data;

let restoreRequest;
export function restoreSession() {
  if (restoreRequest) return restoreRequest;
  const { token } = useAuthStore.getState();
  if (!token) {
    useAuthStore.setState({ status: "ready" });
    return Promise.resolve();
  }
  useAuthStore.setState({ status: "loading" });
  restoreRequest = api.get("/auth/me").then(({ data }) => {
    if (useAuthStore.getState().token === token) {
      useAuthStore.setState({ user: data, status: "ready", notice: "" });
    }
  }).catch(() => {
    if (useAuthStore.getState().token === token) useAuthStore.setState({ status: "error" });
  }).finally(() => { restoreRequest = undefined; });
  return restoreRequest;
}

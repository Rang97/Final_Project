import { create } from "zustand";

const storageKey = "guildhub.accessToken";
const readToken = () => {
  try { return sessionStorage.getItem(storageKey); } catch { return null; }
};
const saveToken = (token) => {
  try {
    if (token) sessionStorage.setItem(storageKey, token);
    else sessionStorage.removeItem(storageKey);
  } catch { /* Authentication still works in memory when storage is unavailable. */ }
};

export const useAuthStore = create((set) => ({
  token: readToken(),
  user: null,
  status: "loading",
  notice: "",
  setSession: ({ accessToken, user }) => {
    saveToken(accessToken);
    set({ token: accessToken, user, status: "ready", notice: "" });
  },
  clearSession: (notice = "") => {
    saveToken(null);
    set({ token: null, user: null, status: "ready", notice });
  },
}));

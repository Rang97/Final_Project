import { useEffect } from "react";
import { Navigate, useLocation } from "react-router-dom";
import { useAuthStore } from "../../store/authStore";
import { restoreSession } from "./authApi";

export default function AuthGate({ children }) {
  const status = useAuthStore((state) => state.status);
  useEffect(() => { restoreSession(); }, []);
  if (status === "loading") return <p role="status" className="p-12 text-center">로그인 상태를 확인하고 있습니다.</p>;
  if (status === "error") return (
    <div role="alert" className="p-12 text-center">
      <p>로그인 정보를 확인할 수 없습니다. 서버 연결을 확인해 주세요.</p>
      <button className="m-4 underline" onClick={() => restoreSession()}>다시 시도</button>
      <button className="m-4 underline" onClick={() => useAuthStore.getState().clearSession()}>로그아웃</button>
    </div>
  );
  return children;
}

export function RequireAuth({ children }) {
  const user = useAuthStore((state) => state.user);
  const location = useLocation();
  return user ? children : <Navigate to="/login" replace state={{ from: location.pathname }} />;
}

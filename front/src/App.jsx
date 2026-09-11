import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import HomePage from "./pages/HomePage";
import BoardPage from "./pages/BoardPage";
import BoardDetailPage from "./pages/BoardDetailPage";
import PartyPage from "./pages/PartyPage";
import MyPage from "./pages/MyPage";
import LoginPage from "./pages/LoginPage";
import Layout from "./components/Layout";
import AuthGate, { RequireAuth } from "./features/auth/AuthGate";

export default function App() {
  return (
    <BrowserRouter>
      <AuthGate>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route element={<Layout />}>
          <Route path="/" element={<HomePage />} />
          <Route path="/board" element={<BoardPage />} />
          <Route path="/board/:id" element={<BoardDetailPage />} />
          <Route path="/party" element={<PartyPage />} />
          <Route path="/mypage" element={<RequireAuth><MyPage /></RequireAuth>} />
        </Route>
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
      </AuthGate>
    </BrowserRouter>
  );
}

import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import HomePage from "./pages/HomePage";
import BoardPage from "./pages/BoardPage";
import BoardDetailPage from "./pages/BoardDetailPage";
import PartyPage from "./pages/PartyPage";
import MyPage from "./pages/MyPage";
import LoginPage from "./pages/LoginPage";
import Layout from "./components/Layout";
import PartyCreatePage from "./pages/PartyCreatePage";

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* 로그인 */}
        <Route path="/login" element={<LoginPage />} />
        <Route element={<Layout />}>
          {/* 메인 */}
          <Route path="/" element={<HomePage />} />
          {/* 게시판 */}
          <Route path="/board" element={<BoardPage />} />
          <Route path="/board/:id" element={<BoardDetailPage />} />
          {/* 파티 */}
          <Route path="/party" element={<PartyPage />} />
          <Route path="/party/create" element={<PartyCreatePage />}></Route>
          {/* 마이페이지 */}
          <Route path="/mypage" element={<MyPage />} />
        </Route>
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </BrowserRouter>
  );
}

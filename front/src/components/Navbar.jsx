import { useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";

import { useAuthStore } from "../store/authStore";

const navItems = [
  { label: "홈", to: "/" },
  { label: "게시판", to: "/board" },
  { label: "파티 모집", to: "/party" },
  { label: "마이페이지", to: "/mypage" },
];

export default function Navbar() {
  const [menuOpen, setMenuOpen] = useState(false);
  const navigate = useNavigate();
  const user = useAuthStore((state) => state.user);
  const logout = () => {
    useAuthStore.getState().clearSession();
    setMenuOpen(false);
    navigate("/");
  };

  return (
    <nav
      className="sticky top-0 z-50 flex items-center justify-between px-6 md:px-10 h-16"
      style={{
        background: "rgba(8,8,15,0.85)",
        backdropFilter: "blur(16px)",
        borderBottom: "1px solid rgba(255,255,255,0.07)",
      }}
    >
      {/* Logo */}
      <button
        onClick={() => navigate("/")}
        className="flex items-center gap-2 font-bold text-xl tracking-widest"
        style={{ fontFamily: "'Rajdhani', sans-serif", color: "#e8e8f0" }}
      >
        <span
          className="w-8 h-8 rounded-lg flex items-center justify-center text-sm font-black"
          style={{ background: "linear-gradient(135deg, #7c3aed, #06d6a0)" }}
        >
          G
        </span>
        GUILDHUB
      </button>

      {/* Desktop nav */}
      <ul className="hidden md:flex items-center gap-1">
        {navItems.map((item) => (
          <li key={item.to}>
            <NavLink
              to={item.to}
              end={item.to === "/"}
              className={({ isActive }) =>
                `px-4 py-2 rounded-lg text-sm font-medium transition-all duration-200 ${
                  isActive
                    ? "text-white"
                    : "text-[#6060a0] hover:text-[#c0c0e0]"
                }`
              }
              style={({ isActive }) =>
                isActive
                  ? { background: "rgba(124,58,237,0.18)", color: "#a78bfa" }
                  : {}
              }
            >
              {item.label}
            </NavLink>
          </li>
        ))}
      </ul>

      {/* Right actions */}
      <div className="hidden md:flex items-center gap-3">
        <button
          className="text-sm px-4 py-2 rounded-lg font-medium transition-colors"
          style={{ color: "#a0a0c0" }}
          onClick={() => navigate(user ? "/mypage" : "/login")}
        >
          {user ? user.nickname : "로그인"}
        </button>
        <button
          className="text-sm px-4 py-2 rounded-lg font-semibold transition-all"
          style={{
            background: "linear-gradient(135deg, #7c3aed, #5b21b6)",
            color: "#fff",
          }}
          onClick={user ? logout : () => navigate("/signup")}
        >
          {user ? "로그아웃" : "회원가입"}
        </button>
      </div>

      {/* Mobile hamburger */}
      <button
        className="md:hidden flex flex-col gap-1.5 p-2"
        onClick={() => setMenuOpen(!menuOpen)}
      >
        <span
          className="block w-5 h-0.5 transition-all"
          style={{ background: "#a0a0c0" }}
        />
        <span
          className="block w-5 h-0.5 transition-all"
          style={{ background: "#a0a0c0" }}
        />
        <span
          className="block w-5 h-0.5 transition-all"
          style={{ background: "#a0a0c0" }}
        />
      </button>

      {/* Mobile menu */}
      {menuOpen && (
        <div
          className="absolute top-16 left-0 right-0 flex flex-col p-4 gap-1 md:hidden"
          style={{
            background: "#10101c",
            borderBottom: "1px solid rgba(255,255,255,0.07)",
          }}
        >
          {navItems.map((item) => (
            <NavLink
              key={item.to}
              to={item.to}
              end={item.to === "/"}
              onClick={() => setMenuOpen(false)}
              className={({ isActive }) =>
                `px-4 py-3 rounded-lg text-sm font-medium ${
                  isActive ? "text-[#a78bfa]" : "text-[#a0a0c0]"
                }`
              }
              style={({ isActive }) =>
                isActive ? { background: "rgba(124,58,237,0.15)" } : {}
              }
            >
              {item.label}
            </NavLink>
          ))}
          <div
            className="flex gap-2 mt-2 pt-2"
            style={{ borderTop: "1px solid rgba(255,255,255,0.07)" }}
          >
            <button
              className="flex-1 py-2 rounded-lg text-sm"
              style={{ color: "#a0a0c0", background: "rgba(255,255,255,0.05)" }}
              onClick={() => {
                setMenuOpen(false);
                navigate(user ? "/mypage" : "/login");
              }}
            >
              {user ? user.nickname : "로그인"}
            </button>
            <button
              className="flex-1 py-2 rounded-lg text-sm font-semibold"
              style={{
                background: "linear-gradient(135deg, #7c3aed, #5b21b6)",
                color: "#fff",
              }}
              onClick={() => {
                setMenuOpen(false);
                if (user) logout();
                else navigate("/signup");
              }}
            >
              {user ? "로그아웃" : "회원가입"}
            </button>
          </div>
        </div>
      )}
    </nav>
  );
}

import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function LoginPage() {
  const [mode, setMode] = useState("login");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [nickname, setNickname] = useState("");
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    navigate("/");
  };

  return (
    <div
      className="min-h-screen flex items-center justify-center px-4 relative overflow-hidden"
      style={{ background: "#08080f" }}
    >
      {/* Background glow */}
      <div
        className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[600px] h-[600px] rounded-full blur-3xl opacity-15 pointer-events-none"
        style={{
          background: "radial-gradient(ellipse, #7c3aed, transparent 65%)",
        }}
      />
      <div
        className="absolute bottom-0 right-0 w-80 h-80 rounded-full blur-3xl opacity-10 pointer-events-none"
        style={{ background: "#06d6a0" }}
      />

      <div className="relative w-full max-w-md">
        {/* Logo */}
        <div className="text-center mb-8">
          <button
            onClick={() => navigate("/")}
            className="inline-flex items-center gap-2 font-bold text-2xl tracking-widest"
            style={{ fontFamily: "'Rajdhani', sans-serif" }}
          >
            <span
              className="w-10 h-10 rounded flex items-center justify-center text-base font-black"
              style={{
                background: "linear-gradient(135deg, #7c3aed, #06d6a0)",
              }}
            >
              G
            </span>
            GUILDHUB
          </button>
          <p className="mt-3 text-sm" style={{ color: "#6060a0" }}>
            {mode === "login"
              ? "다시 오셨군요! 로그인해주세요."
              : "게이밍 커뮤니티에 합류하세요."}
          </p>
        </div>

        {/* Card */}
        <div
          className="rounded p-8"
          style={{
            background: "#10101c",
            border: "1px solid rgba(255,255,255,0.08)",
          }}
        >
          {/* Tab toggle */}
          <div
            className="flex gap-1 p-1 rounded mb-8"
            style={{ background: "rgba(255,255,255,0.04)" }}
          >
            <button
              onClick={() => setMode("login")}
              className="flex-1 py-2 rounded-lg text-sm font-medium transition-all"
              style={
                mode === "login"
                  ? { background: "rgba(124,58,237,0.25)", color: "#a78bfa" }
                  : { color: "#6060a0" }
              }
            >
              로그인
            </button>
            <button
              onClick={() => setMode("register")}
              className="flex-1 py-2 rounded-lg text-sm font-medium transition-all"
              style={
                mode === "register"
                  ? { background: "rgba(124,58,237,0.25)", color: "#a78bfa" }
                  : { color: "#6060a0" }
              }
            >
              회원가입
            </button>
          </div>

          <form onSubmit={handleSubmit} className="flex flex-col gap-4">
            {mode === "register" && (
              <div>
                <label
                  className="block text-xs font-medium mb-2"
                  style={{ color: "#6060a0" }}
                >
                  닉네임
                </label>
                <input
                  type="text"
                  placeholder="게임에서 사용하는 닉네임"
                  value={nickname}
                  onChange={(e) => setNickname(e.target.value)}
                  required
                  className="w-full px-4 py-3 rounded text-sm outline-none transition-all"
                  style={{
                    background: "rgba(255,255,255,0.04)",
                    border: "1px solid rgba(255,255,255,0.1)",
                    color: "#e8e8f0",
                  }}
                />
              </div>
            )}

            <div>
              <label
                className="block text-xs font-medium mb-2"
                style={{ color: "#6060a0" }}
              >
                이메일
              </label>
              <input
                type="email"
                placeholder="your@email.com"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
                className="w-full px-4 py-3 rounded text-sm outline-none transition-all"
                style={{
                  background: "rgba(255,255,255,0.04)",
                  border: "1px solid rgba(255,255,255,0.1)",
                  color: "#e8e8f0",
                }}
              />
            </div>

            <div>
              <div className="flex items-center justify-between mb-2">
                <label
                  className="text-xs font-medium"
                  style={{ color: "#6060a0" }}
                >
                  비밀번호
                </label>
                {mode === "login" && (
                  <button
                    type="button"
                    className="text-xs"
                    style={{ color: "#7c3aed" }}
                  >
                    비밀번호 찾기
                  </button>
                )}
              </div>
              <input
                type="password"
                placeholder="••••••••"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
                className="w-full px-4 py-3 rounded text-sm outline-none transition-all"
                style={{
                  background: "rgba(255,255,255,0.04)",
                  border: "1px solid rgba(255,255,255,0.1)",
                  color: "#e8e8f0",
                }}
              />
            </div>

            {mode === "register" && (
              <div>
                <label
                  className="block text-xs font-medium mb-2"
                  style={{ color: "#6060a0" }}
                >
                  비밀번호 확인
                </label>
                <input
                  type="password"
                  placeholder="••••••••"
                  required
                  className="w-full px-4 py-3 rounded text-sm outline-none"
                  style={{
                    background: "rgba(255,255,255,0.04)",
                    border: "1px solid rgba(255,255,255,0.1)",
                    color: "#e8e8f0",
                  }}
                />
              </div>
            )}

            <button
              type="submit"
              className="w-full py-3 rounded font-semibold text-sm mt-2 transition-transform hover:scale-[1.02]"
              style={{
                background: "linear-gradient(135deg, #7c3aed, #5b21b6)",
                color: "#fff",
              }}
            >
              {mode === "login" ? "로그인" : "회원가입"}
            </button>
          </form>

          {/* Divider */}
          <div className="flex items-center gap-3 my-6">
            <div
              className="flex-1 h-px"
              style={{ background: "rgba(255,255,255,0.08)" }}
            />
            <span className="text-xs" style={{ color: "#6060a0" }}>
              또는
            </span>
            <div
              className="flex-1 h-px"
              style={{ background: "rgba(255,255,255,0.08)" }}
            />
          </div>

          {/* Social logins */}
          <div className="flex flex-col gap-3">
            {[
              { label: "Google로 계속하기", icon: "G", color: "#ea4335" },
              { label: "Discord로 계속하기", icon: "D", color: "#5865f2" },
            ].map((social) => (
              <button
                key={social.label}
                className="w-full flex items-center justify-center gap-3 py-3 rounded text-sm font-medium transition-colors"
                style={{
                  background: "rgba(255,255,255,0.04)",
                  border: "1px solid rgba(255,255,255,0.09)",
                  color: "#c0c0e0",
                }}
              >
                <span
                  className="w-5 h-5 rounded-full flex items-center justify-center text-xs font-bold"
                  style={{ background: social.color, color: "#fff" }}
                >
                  {social.icon}
                </span>
                {social.label}
              </button>
            ))}
          </div>
        </div>

        <p className="text-center mt-6 text-xs" style={{ color: "#6060a0" }}>
          {mode === "login" ? (
            <>
              계정이 없으신가요?{" "}
              <button
                onClick={() => setMode("register")}
                style={{ color: "#a78bfa" }}
              >
                회원가입
              </button>
            </>
          ) : (
            <>
              이미 계정이 있으신가요?{" "}
              <button
                onClick={() => setMode("login")}
                style={{ color: "#a78bfa" }}
              >
                로그인
              </button>
            </>
          )}
        </p>
      </div>
    </div>
  );
}

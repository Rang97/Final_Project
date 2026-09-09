import { useNavigate } from "react-router-dom";

const gameCards = [
  {
    title: "리그 오브 레전드",
    sub: "MOBA · 5v5",
    bg: "#3b1f8c",
    img: "https://images.unsplash.com/photo-1542751371-adc38448a05e?w=300&h=420&fit=crop&auto=format",
    rotate: -14,
    tx: -1,
    scale: 0.78,
    z: 1,
  },
  {
    title: "발로란트",
    sub: "FPS · 5v5",
    bg: "#7c1d1d",
    img: "https://images.unsplash.com/photo-1511512578047-dfb367046420?w=300&h=420&fit=crop&auto=format",
    rotate: -7,
    tx: -0.5,
    scale: 0.88,
    z: 2,
  },
  {
    title: "배틀그라운드",
    sub: "Battle Royale",
    bg: "#1a3a2a",
    img: "https://images.unsplash.com/photo-1550745165-9bc0b252726f?w=300&h=420&fit=crop&auto=format",
    rotate: 0,
    tx: 0,
    scale: 1,
    z: 5,
  },
  {
    title: "오버워치 2",
    sub: "팀 슈터",
    bg: "#1a2a4a",
    img: "https://images.unsplash.com/photo-1493711662062-fa541adb3fc8?w=300&h=420&fit=crop&auto=format",
    rotate: 7,
    tx: 0.5,
    scale: 0.88,
    z: 2,
  },
  {
    title: "메이플스토리",
    sub: "MMORPG",
    bg: "#3a1a3a",
    img: "https://images.unsplash.com/photo-1519326844852-704caea5679e?w=300&h=420&fit=crop&auto=format",
    rotate: 14,
    tx: 1,
    scale: 0.78,
    z: 1,
  },
];

const CARD_W = 220;
const CARD_H = 320;
const SPREAD = 170;

export default function HomePage() {
  const navigate = useNavigate();

  return (
    <div style={{ background: "#07070e", minHeight: "100vh" }}>
      {/* Hero */}
      <section
        className="relative overflow-hidden flex flex-col items-center"
        style={{ minHeight: "92vh" }}
      >
        {/* Subtle grid texture */}
        <div
          className="absolute inset-0 pointer-events-none"
          style={{
            backgroundImage:
              "linear-gradient(rgba(255,255,255,0.025) 1px, transparent 1px), linear-gradient(90deg, rgba(255,255,255,0.025) 1px, transparent 1px)",
            backgroundSize: "48px 48px",
          }}
        />

        {/* Purple radial glow behind cards */}
        <div
          className="absolute bottom-0 left-1/2 -translate-x-1/2 pointer-events-none"
          style={{
            width: 700,
            height: 500,
            background: "radial-gradient(ellipse, rgba(124,58,237,0.22) 0%, transparent 70%)",
            filter: "blur(40px)",
          }}
        />

        {/* Floating decorative game art */}
        <img
          src="https://images.unsplash.com/photo-1550745165-9bc0b252726f?w=80&h=80&fit=crop&auto=format"
          alt=""
          className="absolute hidden md:block rounded-xl opacity-70 pointer-events-none"
          style={{ width: 70, height: 70, top: "18%", left: "6%", transform: "rotate(-12deg)", filter: "drop-shadow(0 8px 24px #7c3aed55)" }}
        />
        <img
          src="https://images.unsplash.com/photo-1493711662062-fa541adb3fc8?w=80&h=80&fit=crop&auto=format"
          alt=""
          className="absolute hidden md:block rounded-xl opacity-60 pointer-events-none"
          style={{ width: 60, height: 60, top: "28%", right: "7%", transform: "rotate(10deg)", filter: "drop-shadow(0 8px 24px #06d6a055)" }}
        />

        {/* Badge */}
        <div className="mt-20 mb-6">
          <span
            className="inline-flex items-center gap-2 text-xs font-semibold tracking-widest uppercase px-4 py-1.5 rounded"
            style={{
              background: "rgba(124,58,237,0.15)",
              color: "#a78bfa",
              border: "1px solid rgba(124,58,237,0.3)",
            }}
          >
            <span
              className="w-1.5 h-1.5 rounded-full"
              style={{ background: "#a78bfa", boxShadow: "0 0 6px #a78bfa" }}
            />
            게이머를 위한 커뮤니티
          </span>
        </div>

        {/* Headline */}
        <h1
          className="text-center font-bold leading-none px-4 mb-5"
          style={{
            fontFamily: "'Rajdhani', sans-serif",
            fontSize: "clamp(3rem, 8vw, 6.5rem)",
            letterSpacing: "-0.01em",
            color: "#fff",
          }}
        >
          함께 플레이하고
          <br />
          <span
            style={{
              background: "linear-gradient(90deg, #7c3aed, #06d6a0)",
              WebkitBackgroundClip: "text",
              WebkitTextFillColor: "transparent",
            }}
          >
            함께 성장하세요
          </span>
        </h1>

        <p
          className="text-center max-w-md px-6 mb-12"
          style={{ color: "rgba(240,240,250,0.45)", fontSize: "1rem", lineHeight: 1.7 }}
        >
          게임 파티를 구하고, 정보를 나누고,<br />길드원을 찾는 최고의 게이밍 커뮤니티
        </p>

        {/* CTA buttons */}
        <div className="flex items-center gap-3 mb-20 flex-wrap justify-center px-4">
          <button
            onClick={() => navigate("/party")}
            className="px-7 py-2.5 rounded font-semibold text-sm transition-all hover:brightness-110"
            style={{ background: "linear-gradient(135deg, #7c3aed, #5b21b6)", color: "#fff" }}
          >
            파티 찾기
          </button>
          <button
            onClick={() => navigate("/board")}
            className="px-7 py-2.5 rounded font-semibold text-sm transition-colors"
            style={{
              background: "rgba(255,255,255,0.06)",
              color: "#c0c0e0",
              border: "1px solid rgba(255,255,255,0.1)",
            }}
          >
            게시판 보기
          </button>
        </div>

        {/* Fan of cards */}
        <div
          className="relative flex-shrink-0"
          style={{
            width: "100%",
            height: CARD_H + 60,
            perspective: 1000,
          }}
        >
          {gameCards.map((card, i) => {
            const isCenter = card.rotate === 0;
            const offsetX = card.tx * SPREAD;
            return (
              <div
                key={card.title}
                onClick={() => navigate("/party")}
                className="absolute cursor-pointer overflow-hidden"
                style={{
                  width: CARD_W,
                  height: CARD_H,
                  left: "50%",
                  bottom: isCenter ? 0 : 30,
                  borderRadius: 16,
                  transform: `translateX(calc(-50% + ${offsetX}px)) rotate(${card.rotate}deg) scale(${card.scale})`,
                  transformOrigin: "bottom center",
                  zIndex: card.z,
                  transition: "transform 0.2s ease, z-index 0s",
                  boxShadow: isCenter
                    ? "0 30px 80px rgba(0,0,0,0.7), 0 0 40px rgba(124,58,237,0.3)"
                    : "0 20px 50px rgba(0,0,0,0.6)",
                }}
              >
                {/* Card image */}
                <img
                  src={card.img}
                  alt={card.title}
                  className="absolute inset-0 w-full h-full object-cover"
                />
                {/* Gradient overlay */}
                <div
                  className="absolute inset-0"
                  style={{
                    background: `linear-gradient(to top, ${card.bg}ee 0%, ${card.bg}88 40%, transparent 100%)`,
                  }}
                />
                {/* Card text */}
                <div className="absolute bottom-0 left-0 right-0 p-4">
                  <p
                    className="font-bold text-lg leading-tight"
                    style={{ fontFamily: "'Rajdhani', sans-serif", color: "#fff" }}
                  >
                    {card.title}
                  </p>
                  <p className="text-xs mt-0.5" style={{ color: "rgba(255,255,255,0.55)" }}>
                    {card.sub}
                  </p>
                </div>
                {/* Center card badge */}
                {isCenter && (
                  <div
                    className="absolute top-3 right-3 flex items-center gap-1.5 px-2.5 py-1 rounded-full text-xs font-semibold"
                    style={{ background: "rgba(0,0,0,0.55)", backdropFilter: "blur(8px)", color: "#fff" }}
                  >
                    <span className="w-1.5 h-1.5 rounded-full" style={{ background: "#06d6a0" }} />
                    인기
                  </div>
                )}
              </div>
            );
          })}
        </div>
      </section>
    </div>
  );
}

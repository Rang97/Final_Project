import { useState } from "react";

const tabs = ["프로필", "내 파티", "작성 글", "설정"];

const myParties = [
  { id: 1, game: "리그 오브 레전드", title: "다이아 듀오 구합니다", status: "모집 중", date: "2026-09-05", members: "1/2", accent: "#7c3aed" },
  { id: 2, game: "발로란트", title: "레디언트 5스택", status: "완료", date: "2026-08-28", members: "5/5", accent: "#ef4444" },
  { id: 3, game: "배틀그라운드", title: "닭저녁 도전 스쿼드", status: "모집 중", date: "2026-09-08", members: "2/4", accent: "#06d6a0" },
];

const myPosts = [
  { id: 1, category: "공략/팁", title: "다이아 정글 핵심 와드 포지션 정리", game: "리그 오브 레전드", date: "2026-09-03", views: 2140, likes: 178 },
  { id: 2, category: "자유", title: "드디어 다이아 승격!! 3개월의 여정", game: "리그 오브 레전드", date: "2026-08-20", views: 980, likes: 92 },
  { id: 3, category: "질문", title: "에코 vs 조이 미드 매치업 어떻게 하시나요?", game: "리그 오브 레전드", date: "2026-08-11", views: 430, likes: 31 },
];

const gameStats = [
  { game: "리그 오브 레전드", tier: "다이아 I", lp: "82 LP", winRate: "58%", kda: "4.2", matches: 214 },
  { game: "발로란트", tier: "플래티넘 III", winRate: "52%", kda: "1.8", matches: 87 },
];

export default function MyPage() {
  const [activeTab, setActiveTab] = useState("프로필");

  return (
    <div className="max-w-5xl mx-auto px-6 md:px-10 py-12">
      {/* Profile header card */}
      <div
        className="rounded overflow-hidden mb-8"
        style={{ background: "#10101c", border: "1px solid rgba(255,255,255,0.07)" }}
      >
        {/* Banner */}
        <div
          className="h-32 relative"
          style={{ background: "linear-gradient(135deg, #7c3aed33, #06d6a022), #1a1a2e" }}
        >
          <div
            className="absolute inset-0 opacity-30"
            style={{ background: "radial-gradient(ellipse at 30% 50%, #7c3aed, transparent 60%)" }}
          />
        </div>

        {/* Avatar + info */}
        <div className="px-6 pb-6 relative">
          <div className="flex items-end justify-between gap-4 -mt-10 mb-4 flex-wrap">
            <div className="flex items-end gap-4">
              <div
                className="w-20 h-20 rounded flex items-center justify-center text-3xl font-black relative z-10"
                style={{
                  background: "linear-gradient(135deg, #7c3aed, #06d6a0)",
                  border: "3px solid #10101c",
                  fontFamily: "'Rajdhani', sans-serif",
                }}
              >
                X
              </div>
              <div className="pb-1">
                <h2 className="text-2xl font-bold" style={{ fontFamily: "'Rajdhani', sans-serif" }}>Xenocraft</h2>
                <p className="text-sm" style={{ color: "#6060a0" }}>@xenocraft · 가입일 2024.03</p>
              </div>
            </div>
            <button
              className="px-5 py-2 rounded text-sm font-medium"
              style={{ background: "rgba(255,255,255,0.06)", color: "#c0c0e0", border: "1px solid rgba(255,255,255,0.1)" }}
            >
              프로필 편집
            </button>
          </div>

          {/* Bio */}
          <p className="text-sm mb-5" style={{ color: "#a0a0c0" }}>
            리그 오브 레전드 다이아 정글 메인 | 발로란트 플래티넘 | 즐겜 추구 🎮
          </p>

          {/* Quick stats */}
          <div className="grid grid-cols-3 md:grid-cols-6 gap-4">
            {[
              { label: "작성 글", value: "31" },
              { label: "파티 참여", value: "47" },
              { label: "받은 좋아요", value: "1,204" },
              { label: "팔로워", value: "218" },
              { label: "팔로잉", value: "94" },
              { label: "레벨", value: "42" },
            ].map((stat) => (
              <div key={stat.label} className="text-center">
                <p className="text-xl font-bold" style={{ fontFamily: "'Rajdhani', sans-serif", color: "#a78bfa" }}>{stat.value}</p>
                <p className="text-xs" style={{ color: "#6060a0" }}>{stat.label}</p>
              </div>
            ))}
          </div>
        </div>
      </div>

      {/* Tabs */}
      <div className="flex gap-1 mb-8 p-1 rounded w-fit" style={{ background: "#10101c" }}>
        {tabs.map((tab) => (
          <button
            key={tab}
            onClick={() => setActiveTab(tab)}
            className="px-5 py-2 rounded-lg text-sm font-medium transition-all"
            style={
              activeTab === tab
                ? { background: "rgba(124,58,237,0.25)", color: "#a78bfa" }
                : { color: "#6060a0" }
            }
          >
            {tab}
          </button>
        ))}
      </div>

      {/* Tab: 프로필 */}
      {activeTab === "프로필" && (
        <div className="flex flex-col gap-6">
          {gameStats.map((stat) => (
            <div
              key={stat.game}
              className="rounded p-6"
              style={{ background: "#10101c", border: "1px solid rgba(255,255,255,0.07)" }}
            >
              <div className="flex items-center justify-between mb-5">
                <h3 className="font-bold text-lg" style={{ fontFamily: "'Rajdhani', sans-serif" }}>{stat.game}</h3>
                <span
                  className="text-sm font-semibold px-3 py-1 rounded-lg"
                  style={{ background: "rgba(124,58,237,0.2)", color: "#a78bfa" }}
                >
                  {stat.tier}
                </span>
              </div>
              <div className="grid grid-cols-3 gap-4">
                {[
                  { label: "승률", value: stat.winRate },
                  { label: "KDA", value: stat.kda },
                  { label: "게임 수", value: String(stat.matches) },
                ].map((item) => (
                  <div key={item.label} className="text-center py-4 rounded" style={{ background: "rgba(255,255,255,0.04)" }}>
                    <p className="text-2xl font-bold mb-1" style={{ fontFamily: "'Rajdhani', sans-serif", color: "#e8e8f0" }}>{item.value}</p>
                    <p className="text-xs" style={{ color: "#6060a0" }}>{item.label}</p>
                  </div>
                ))}
              </div>
            </div>
          ))}
        </div>
      )}

      {/* Tab: 내 파티 */}
      {activeTab === "내 파티" && (
        <div className="flex flex-col gap-3">
          {myParties.map((party) => (
            <div
              key={party.id}
              className="flex items-center gap-4 px-5 py-4 rounded"
              style={{ background: "#10101c", border: "1px solid rgba(255,255,255,0.07)" }}
            >
              <div className="w-1 h-12 rounded-full flex-shrink-0" style={{ background: party.accent }} />
              <div className="flex-1 min-w-0">
                <p className="font-semibold text-sm truncate mb-0.5" style={{ color: "#e8e8f0" }}>{party.title}</p>
                <p className="text-xs" style={{ color: "#6060a0" }}>{party.game} · {party.date}</p>
              </div>
              <span className="text-xs font-semibold px-2 py-0.5 rounded" style={{ color: "#6060a0" }}>{party.members}</span>
              <span
                className="text-xs font-semibold px-3 py-1 rounded-lg"
                style={
                  party.status === "모집 중"
                    ? { background: "rgba(6,214,160,0.15)", color: "#06d6a0" }
                    : { background: "rgba(255,255,255,0.06)", color: "#6060a0" }
                }
              >
                {party.status}
              </span>
            </div>
          ))}
        </div>
      )}

      {/* Tab: 작성 글 */}
      {activeTab === "작성 글" && (
        <div className="flex flex-col gap-3">
          {myPosts.map((post) => (
            <div
              key={post.id}
              className="flex items-start gap-4 px-5 py-4 rounded cursor-pointer"
              style={{ background: "#10101c", border: "1px solid rgba(255,255,255,0.07)" }}
            >
              <span
                className="text-xs font-semibold px-2 py-0.5 rounded flex-shrink-0"
                style={{ background: "rgba(124,58,237,0.15)", color: "#a78bfa" }}
              >
                {post.category}
              </span>
              <div className="flex-1 min-w-0">
                <p className="text-sm font-medium truncate mb-0.5" style={{ color: "#e8e8f0" }}>{post.title}</p>
                <p className="text-xs" style={{ color: "#6060a0" }}>{post.game} · {post.date}</p>
              </div>
              <div className="hidden md:flex gap-4 text-xs flex-shrink-0" style={{ color: "#6060a0" }}>
                <span>👁 {post.views.toLocaleString()}</span>
                <span>👍 {post.likes}</span>
              </div>
            </div>
          ))}
        </div>
      )}

      {/* Tab: 설정 */}
      {activeTab === "설정" && (
        <div
          className="rounded p-6 flex flex-col gap-6"
          style={{ background: "#10101c", border: "1px solid rgba(255,255,255,0.07)" }}
        >
          {[
            { label: "닉네임", value: "Xenocraft" },
            { label: "이메일", value: "xeno@example.com" },
            { label: "자기소개", value: "리그 오브 레전드 다이아 정글 메인" },
          ].map((field) => (
            <div key={field.label}>
              <label className="block text-xs mb-2 font-medium" style={{ color: "#6060a0" }}>{field.label}</label>
              <input
                defaultValue={field.value}
                className="w-full px-4 py-2.5 rounded text-sm outline-none"
                style={{ background: "rgba(255,255,255,0.04)", border: "1px solid rgba(255,255,255,0.1)", color: "#e8e8f0" }}
              />
            </div>
          ))}
          <div className="pt-2">
            <button
              className="px-6 py-2.5 rounded text-sm font-semibold"
              style={{ background: "linear-gradient(135deg, #7c3aed, #5b21b6)", color: "#fff" }}
            >
              변경사항 저장
            </button>
          </div>
        </div>
      )}
    </div>
  );
}

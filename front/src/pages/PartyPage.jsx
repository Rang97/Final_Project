import { useState } from "react";

const sortOptions = ["최신순", "인기순", "마감임박"];

const parties = [
  {
    id: 1,
    game: "리그 오브 레전드",
    title: "다이아 랭크 듀오 구합니다 — 원딜 or 서폿",
    description: "오후 9시~새벽 1시 주로 플레이. 욕설 금지, 소통 중시합니다.",
    host: "Xenocraft",
    hostTier: "다이아 I",
    slots: 1,
    totalSlots: 2,
    mode: "랭크",
    tags: ["원딜", "서폿", "소통 중시"],
    date: "2시간 전",
  },
  {
    id: 2,
    game: "발로란트",
    title: "레디언트 5스택 파티원 2명 모집",
    description: "주말 스크림 팀 구성 중. 포지션 무관, 소통 필수.",
    host: "NightOwl",
    hostTier: "레디언트",
    slots: 2,
    totalSlots: 5,
    mode: "경쟁전",
    tags: ["5스택", "스크림", "주말"],
    date: "4시간 전",
  },
  {
    id: 3,
    game: "배틀그라운드",
    title: "스쿼드 닭저녁 도전! 초보 환영",
    description: "부담 없이 즐겁게 한 판 하실 분. 승패보다 재미 중시.",
    host: "ChickenLover",
    hostTier: "골드",
    slots: 3,
    totalSlots: 4,
    mode: "일반",
    tags: ["초보 환영", "즐겜", "스쿼드"],
    date: "30분 전",
  },
  {
    id: 4,
    game: "오버워치 2",
    title: "플래티넘 탱커 파티 모집 — 딜/힐 구함",
    description: "시즌 컷 올리는 게 목표. 분위기 좋은 분들만.",
    host: "IronClad",
    hostTier: "플래티넘 II",
    slots: 2,
    totalSlots: 3,
    mode: "랭크",
    tags: ["딜러", "힐러"],
    date: "1시간 전",
  },
  {
    id: 5,
    game: "리그 오브 레전드",
    title: "챌린저 코치 무료 강의 — 골드 이하 신청 가능",
    description: "보이스 필수. 리플레이 분석 후 피드백 제공합니다.",
    host: "ProCoach",
    hostTier: "챌린저",
    slots: 3,
    totalSlots: 3,
    mode: "코칭",
    tags: ["코칭", "골드 이하", "보이스"],
    date: "5시간 전",
  },
  {
    id: 6,
    game: "메이플스토리",
    title: "아케인리버 일반몹 사냥 파티 — 보스 협력 가능",
    description: "주 3회 이상 접속하시는 분. 지역 무관.",
    host: "MapleVet",
    hostTier: "Lv.280+",
    slots: 4,
    totalSlots: 6,
    mode: "협동",
    tags: ["사냥", "보스", "주3회"],
    date: "3시간 전",
  },
];

export default function PartyPage() {
  const [activeSort, setActiveSort] = useState("최신순");
  const [joined, setJoined] = useState([]);

  const handleJoin = (id) => {
    setJoined((prev) =>
      prev.includes(id) ? prev.filter((x) => x !== id) : [...prev, id],
    );
  };

  return (
    <div className="max-w-6xl mx-auto px-6 md:px-10 py-12">
      {/* Header */}
      <div className="flex items-end justify-between mb-10 flex-wrap gap-4">
        <div>
          <p
            className="text-xs uppercase tracking-widest mb-2"
            style={{ color: "#7c3aed" }}
          >
            파티 찾기
          </p>
          <h1
            className="text-4xl font-bold"
            style={{ fontFamily: "'Rajdhani', sans-serif" }}
          >
            파티 모집
          </h1>
        </div>
        <button
          className="px-5 py-2 rounded text-sm font-semibold"
          style={{
            background: "linear-gradient(135deg, #7c3aed, #5b21b6)",
            color: "#fff",
          }}
        >
          + 파티 만들기
        </button>
      </div>

      {/* Sort only */}
      <div className="flex items-center gap-2 mb-8">
        {sortOptions.map((opt) => (
          <button
            key={opt}
            onClick={() => setActiveSort(opt)}
            className="px-4 py-1.5 rounded text-sm font-medium transition-all"
            style={
              activeSort === opt
                ? {
                    background: "rgba(124,58,237,0.2)",
                    color: "#a78bfa",
                    border: "1px solid rgba(124,58,237,0.4)",
                  }
                : {
                    background: "#0f0f1a",
                    color: "#4a4a70",
                    border: "1px solid rgba(255,255,255,0.06)",
                  }
            }
          >
            {opt}
          </button>
        ))}
      </div>

      {/* Cards */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {parties.map((party) => {
          const isJoined = joined.includes(party.id);
          const isFull = party.slots === 0;
          const filled = party.totalSlots - party.slots;

          return (
            <div
              key={party.id}
              className="rounded flex flex-col overflow-hidden transition-transform hover:-translate-y-0.5"
              style={{
                background: "#0f0f1a",
                border: "1px solid rgba(255,255,255,0.07)",
              }}
            >
              {/* Top accent line — single consistent color */}
              <div
                className="h-0.5 w-full"
                style={{
                  background: "linear-gradient(90deg, #7c3aed, transparent)",
                }}
              />

              <div className="p-5 flex flex-col gap-3 flex-1">
                {/* Game + mode */}
                <div className="flex items-center gap-2 flex-wrap">
                  <span
                    className="text-xs font-semibold px-2 py-0.5 rounded"
                    style={{
                      background: "rgba(124,58,237,0.15)",
                      color: "#a78bfa",
                    }}
                  >
                    {party.game}
                  </span>
                  <span
                    className="text-xs px-2 py-0.5 rounded"
                    style={{
                      background: "rgba(255,255,255,0.05)",
                      color: "#4a4a70",
                    }}
                  >
                    {party.mode}
                  </span>
                  <span
                    className="ml-auto text-xs"
                    style={{ color: "#4a4a70" }}
                  >
                    {party.date}
                  </span>
                </div>

                {/* Title */}
                <p
                  className="font-bold text-sm leading-snug"
                  style={{
                    fontFamily: "'Rajdhani', sans-serif",
                    color: "#e8e8f0",
                    fontSize: "1rem",
                  }}
                >
                  {party.title}
                </p>

                {/* Description */}
                <p
                  className="text-xs leading-relaxed"
                  style={{ color: "#4a4a70" }}
                >
                  {party.description}
                </p>

                {/* Tags */}
                <div className="flex flex-wrap gap-1">
                  {party.tags.map((tag) => (
                    <span
                      key={tag}
                      className="text-xs px-2 py-0.5 rounded"
                      style={{
                        background: "rgba(255,255,255,0.04)",
                        color: "#6060a0",
                      }}
                    >
                      #{tag}
                    </span>
                  ))}
                </div>

                {/* Divider */}
                <div
                  className="h-px"
                  style={{ background: "rgba(255,255,255,0.05)" }}
                />

                {/* Host + slots */}
                <div className="flex items-center gap-3">
                  <div
                    className="w-7 h-7 rounded flex items-center justify-center text-xs font-bold flex-shrink-0"
                    style={{
                      background: "rgba(124,58,237,0.2)",
                      color: "#a78bfa",
                    }}
                  >
                    {party.host[0]}
                  </div>
                  <div className="flex-1 min-w-0">
                    <p
                      className="text-xs font-semibold truncate"
                      style={{ color: "#c0c0e0" }}
                    >
                      {party.host}
                    </p>
                    <p className="text-xs" style={{ color: "#4a4a70" }}>
                      {party.hostTier}
                    </p>
                  </div>
                  {/* Slot dots */}
                  <div className="flex items-center gap-1 flex-shrink-0">
                    {Array.from({ length: party.totalSlots }).map((_, i) => (
                      <div
                        key={i}
                        className="w-2 h-2 rounded-full"
                        style={{
                          background:
                            i < filled ? "#7c3aed" : "rgba(255,255,255,0.1)",
                        }}
                      />
                    ))}
                    <span
                      className="text-xs ml-1.5"
                      style={{ color: "#4a4a70" }}
                    >
                      {filled}/{party.totalSlots}
                    </span>
                  </div>
                </div>

                {/* Join button */}
                <button
                  onClick={() => handleJoin(party.id)}
                  disabled={isFull && !isJoined}
                  className="w-full py-2.5 rounded text-sm font-semibold transition-all mt-1"
                  style={
                    isJoined
                      ? {
                          background: "rgba(6,214,160,0.12)",
                          color: "#06d6a0",
                          border: "1px solid rgba(6,214,160,0.25)",
                        }
                      : isFull
                        ? {
                            background: "#1a1a2e",
                            color: "#4a4a70",
                            cursor: "not-allowed",
                          }
                        : {
                            background:
                              "linear-gradient(135deg, #7c3aed, #5b21b6)",
                            color: "#fff",
                          }
                  }
                >
                  {isJoined ? "✓ 참가 완료" : isFull ? "마감" : "참가 신청"}
                </button>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
}

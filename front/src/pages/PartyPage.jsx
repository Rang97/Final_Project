import { useEffect, useState } from "react";
import {
  Listbox,
  ListboxButton,
  ListboxOptions,
  ListboxOption,
} from "@headlessui/react";
import { api } from "../api/client";
import { useNavigate } from "react-router-dom";
import { BiSolidUpArrow } from "react-icons/bi";
import ErrorToast from "../components/ErrorToast";

// 정렬 라벨: 백엔드 PartySortBy enum 매핑

// 오행 그룹
const elementSortOptions = [
  { label: "목", value: "WOOD" },
  { label: "화", value: "FIRE" },
  { label: "토", value: "EARTH" },
  { label: "금", value: "METAL" },
  { label: "수", value: "WATER" },
];

// 방제 / 장르 그룹
const titleGenreOptions = [
  { label: "최신순", value: undefined },
  { label: "방제", value: "TITLE" },
  { label: "장르", value: "GENRE" },
];

// 인원수 / 궁합매칭 그룹
const memberSortOptions = [
  { label: "인원수", value: "MEMBER_COUNT" },
  { label: "궁합매칭", value: "CHEMISTRY_MATCH" },
];

// 궁합 유형 한글 라벨 매핑
const chemistryLabels = {
  SYNERGY: "상생",
  RIVAL: "상극",
  BALANCED: "균형",
};

// 상대 시간 계산 로직
function getRelativeTime(createdAt) {
  const diffMs = new Date() - new Date(createdAt);
  const diffMin = Math.floor(diffMs / 60000);

  if (diffMin < 1) return "방금 전";
  if (diffMin < 60) return `${diffMin}분 전`;

  const diffHour = Math.floor(diffMin / 60);
  if (diffHour < 24) return `${diffHour}시간 전`;

  const diffDay = Math.floor(diffHour / 24);
  return `${diffDay}일 전`;
}

// 정렬 드롭다운 공통 컴포넌트
function SortDropdown({ placeholder, options, sortBy, onChange }) {
  const selected = options.find((opt) => opt.value === sortBy);

  return (
    <Listbox value={selected ? selected.value : ""} onChange={onChange}>
      <ListboxButton
        className="w-40 px-4 py-1.5 rounded text-sm font-medium outline-none text-left whitespace-nowrap overflow-hidden text-ellipsis"
        style={{
          background: "#0f0f1a",
          color: selected ? "#F1FF5E" : "#c0c0e0",
        }}
      >
        {selected ? selected.label : placeholder}
      </ListboxButton>
      <ListboxOptions
        transition
        anchor="bottom start"
        className="mt-1 rounded text-sm overflow-hidden z-10 origin-top outline-none transition duration-150 ease-out data-[closed]:opacity-0 data-[closed]:-translate-y-2"
        style={{
          background: "#0f0f1a",
        }}
      >
        <ListboxOption
          value=""
          className="px-4 py-2 cursor-pointer transition-colors text-[#9CA3AF] aria-selected:text-[#F1FF5E] hover:text-[#F1FF5E] hover:bg-[rgba(124,58,237,0.25)] data-[focus]:bg-[rgba(124,58,237,0.2)]"
        >
          {placeholder}
        </ListboxOption>
        {options.map((opt) => (
          <ListboxOption
            key={opt.value}
            value={opt.value}
            className="px-4 py-2 cursor-pointer transition-colors text-[#c0c0e0] aria-selected:text-[#F1FF5E] hover:text-[#F1FF5E] hover:bg-[rgba(124,58,237,0.25)] data-[focus]:bg-[rgba(124,58,237,0.2)]"
          >
            {opt.label}
          </ListboxOption>
        ))}
      </ListboxOptions>
    </Listbox>
  );
}

export default function PartyPage() {
  const [parties, setParties] = useState([]);
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);
  const [sortBy, setSortBy] = useState(undefined); // 기본 최신순
  const [ascending, setAscending] = useState(true);
  const [joined, setJoined] = useState([]);
  const [error, setError] = useState(null);

  // 드롭다운 선택 -> sortBy 갱신
  const handleSortChange = (value) => {
    setSortBy(value || undefined); // 빈 값 선택 시 정렬 해제 (최신순)
  };

  useEffect(() => {
    api
      .get("/party/party-list", { params: { sortBy, ascending } })
      .then((res) => {
        setParties(res.data);
        setError(null);
      })
      .catch((err) => {
        console.error(err);
        setError("파티 목록을 불러오지 못했습니다.");
      })
      .finally(() => setLoading(false));
  }, [sortBy, ascending]);

  const handleJoin = async (id) => {
    try {
      await api.post(`/party/${id}/join`);
      setJoined((prev) => [...prev, id]);
      setError(null);
    } catch (err) {
      setError(err.response?.data?.message ?? "참가에 실패했습니다.");
    }
  };

  if (loading) {
    return (
      <div
        className="max-w-6xl mx-auto px-6 md:px-10 py-12 text-center"
        style={{ color: "#6060a0" }}
      >
        불러오는 중...
      </div>
    );
  }

  return (
    <div className="max-w-6xl mx-auto px-6 md:px-10 py-12">
      {/* 헤더 */}
      <div className="flex items-end justify-between mb-10 flex-wrap gap-4">
        <div>
          <p
            className="text-xs uppercase tracking-widest mb-2"
            style={{ color: "#5690CC" }}
          >
            파티를 찾아 참여해 보세요!
          </p>
          <h1
            className="text-4xl font-bold"
            style={{ fontFamily: "'Rajdhani', sans-serif" }}
          >
            파티 모집
          </h1>
        </div>
        <button
          className="brand-gradient-btn px-5 py-2 rounded-4xl text-sm font-semibold text-white"
          onClick={() => navigate("/party/create")}
        >
          파티 생성
        </button>
      </div>

      {/* 정렬 */}
      <div
        className="flex items-center gap-3 mb-8"
        style={{
          color: "#c0c0e0",
        }}
      >
        <div className="flex items-center gap-3 mb-8">
          <SortDropdown
            placeholder="오행"
            options={elementSortOptions}
            sortBy={sortBy}
            onChange={handleSortChange}
          />
          <SortDropdown
            placeholder="방제 / 장르"
            options={titleGenreOptions}
            sortBy={sortBy}
            onChange={handleSortChange}
          />
          <SortDropdown
            placeholder="인원수 / 궁합매칭"
            options={memberSortOptions}
            sortBy={sortBy}
            onChange={handleSortChange}
          />
          <button
            className="px-4 py-1.5 rounded text-sm font-medium"
            onClick={() => setAscending((prev) => !prev)}
            style={{
              background: "#0f0f1a",
              color: ascending ? "#F1FF5E" : "#c0c0e0",
              transition: "all 0.3s ease-in 0s",
            }}
          >
            <span
              style={{
                display: "inline-block",
                transition: "transform 0.25s ease",
                transform: ascending ? "rotate(0deg)" : "rotate(180deg)",
              }}
            >
              <BiSolidUpArrow size={16} />
            </span>
          </button>
        </div>
      </div>

      {/* error */}
      <ErrorToast message={error} onClose={() => setError(null)} />

      {/* 파티 카드 */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {parties.map((party) => {
          const isJoined = joined.includes(party.partyId);
          const isFull = party.status === "FULL";

          return (
            <div
              key={party.partyId}
              className="party-card relative flex flex-col overflow-hidden rounded-2x1 border border-[rgba(58,154,255,0.15)] shadow-[0_2px_10px_rgba(0,0,0,0.3)] transition-all duration-200 hover:-translate-y-1 hover:shadow-[0_10px_30px_rgba(58,154,255,0.2)]"
              style={{
                minHeight: "260px",
                backgroundImage: party.coverUrl
                  ? `linear-gradient(to bottom right, rgba(7,7,14,0.9) 0%, rgba(7,7,14,0.55) 55%, rgba(7,7,14,0.15) 100%), url(${party.coverUrl})`
                  : "linear-gradient(160deg, #1C0770 0%, #0f0f1a 70%)",
                backgroundSize: "cover",
                backgroundPosition: "center",
              }}
            >
              <svg
                className="absolute inset-0 pointer-events-none z-10"
                width="100%"
                height="100%"
              >
                <defs>
                  <linearGradient
                    id={`cardGradient-${party.partyId}`}
                    x1="0%"
                    y1="0%"
                    x2="100%"
                    y2="100%"
                  >
                    <stop offset="0%" stopColor="#3A9AFF" />
                    <stop offset="100%" stopColor="#F1FF5E" />
                  </linearGradient>
                </defs>
                <rect
                  x="1"
                  y="1"
                  width="calc(100% - 2px)"
                  height="calc(100% - 2px)"
                  rx="16"
                  ry="16"
                  pathLength="100"
                  fill="none"
                  stroke={`url(#cardGradient-${party.partyId})`}
                  strokeWidth="2"
                  className="card-outline"
                />
              </svg>
              <div className="p-5 flex flex-col justify-between flex-1 gap-3 ">
                {/* 게임 배지 + 궁합유형 태그 + 시간 */}
                <div className="flex items-center gap-2 flex-wrap ">
                  <span
                    className="text-xs font-semibold px-2 py-0.5 rounded"
                    style={{
                      background: "rgba(15,15,26,0.85)",
                      color: "#3A9AFF",
                      border: "1px solid rgba(58,154,255,0.4)",
                    }}
                  >
                    {party.gameName}
                  </span>
                  <span
                    className="text-xs font-semibold px-2 py-0.5 rounded"
                    style={{
                      background: "rgba(15,15,26,0.85)",
                      color: "#F1FF5E",
                      border: "1px solid rgba(241,255,94,0.4)",
                    }}
                  >
                    {chemistryLabels[party.chemistryType]}
                  </span>
                  <span
                    className="ml-auto text-xs"
                    style={{ color: "rgba(255,255,255,0.75)" }}
                  >
                    {getRelativeTime(party.createdAt)}
                  </span>
                </div>

                {/* 방제 */}
                <p
                  className="font-bold text-lg leading-snug"
                  style={{
                    fontFamily: "'Rajdhani', sans-serif",
                    color: "#fff",
                    textShadow: "0 2px 8px rgba(0,0,0,0.6)",
                  }}
                >
                  {party.title}
                </p>

                {/* 인원수 + 방장 닉네임 */}
                <div className="flex flex-col gap-1.5">
                  <div className="flex items-center gap-1">
                    {Array.from({ length: party.maxMemberCount }).map(
                      (_, i) => (
                        <div
                          key={i}
                          className="w-2 h-2"
                          style={{
                            background:
                              i < party.nowMemberCount
                                ? "#3A9AFF"
                                : "rgba(255,255,255,0.2)",
                          }}
                        />
                      ),
                    )}
                    <span
                      className="text-xs ml-1"
                      style={{ color: "rgba(255,255,255,0.75)" }}
                    >
                      {party.nowMemberCount}/{party.maxMemberCount}
                    </span>
                  </div>

                  <span
                    className="text-xs"
                    style={{ color: "rgba(255,255,255,0.5)" }}
                  >
                    {party.hostNickname}
                  </span>
                </div>

                {/* 참가 버튼 */}
                <button
                  onClick={() => !isJoined && handleJoin(party.partyId)}
                  disabled={isFull && !isJoined}
                  className={`w-full py-2.5 rounded-4xl text-sm font-semibold transition-all ${
                    !isJoined && !isFull ? "brand-gradient-btn text-white" : ""
                  }`}
                  style={
                    isJoined
                      ? {
                          background: "rgba(241,255,94,0.12)",
                          color: "#F1FF5E",
                          border: "1px solid rgba(241,255,94,0.3)",
                        }
                      : isFull
                        ? {
                            background: "#1a1a2e",
                            color: "#4a4a70",
                            cursor: "not-allowed",
                          }
                        : undefined
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

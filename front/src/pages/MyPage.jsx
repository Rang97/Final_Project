import { useState } from "react";

const tabs = ["선호 게임", "오행", "내 파티", "작성 글", "설정"];

// 이미지 경로가 포함된 게임 목록 데이터
const availableGames = [
  {
    gameId: 1,
    id: "lol",
    name: "리그 오브 레전드",
    category: "MOBA",
    image:
      "https://images.unsplash.com/photo-1542751371-adc38448a05e?auto=format&fit=crop&w=600&q=80",
  },
  {
    gameId: 2,
    id: "val",
    name: "발로란트",
    category: "FPS",
    image:
      "https://images.unsplash.com/photo-1538481199705-c710c4e965fc?auto=format&fit=crop&w=600&q=80",
  },
  {
    gameId: 3,
    id: "pubg",
    name: "배틀그라운드",
    category: "Battle Royale",
    image:
      "https://images.unsplash.com/photo-1511512578047-dfb367046420?auto=format&fit=crop&w=600&q=80",
  },
  {
    gameId: 4,
    id: "ow",
    name: "오버워치 2",
    category: "FPS",
    image:
      "https://images.unsplash.com/photo-1550745165-9bc0b252726f?auto=format&fit=crop&w=600&q=80",
  },
  {
    gameId: 5,
    id: "lostark",
    name: "로스트아크",
    category: "MMORPG",
    image:
      "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?auto=format&fit=crop&w=600&q=80",
  },
  {
    gameId: 6,
    id: "tft",
    name: "전략적 팀 전투",
    category: "Auto Battler",
    image:
      "https://images.unsplash.com/photo-1612287230202-1ff1d85d1bdf?auto=format&fit=crop&w=600&q=80",
  },
];

const myParties = [
  {
    id: 1,
    game: "리그 오브 레전드",
    title: "다이아 듀오 구합니다",
    status: "모집 중",
    date: "2026-09-05",
    members: "1/2",
  },
  {
    id: 2,
    game: "발로란트",
    title: "레디언트 5스택",
    status: "완료",
    date: "2026-08-28",
    members: "5/5",
  },
  {
    id: 3,
    game: "배틀그라운드",
    title: "닭저녁 도전 스쿼드",
    status: "모집 중",
    date: "2026-09-08",
    members: "2/4",
  },
];

const myPosts = [
  {
    id: 1,
    title: "드디어 승격 성공했습니다!!",
    game: "리그 오브 레전드",
    date: "2026-08-20",
    views: 980,
    likes: 92,
  },
  {
    id: 2,
    title: "에코 vs 조이 미드 매치업 어떻게 하시나요?",
    game: "리그 오브 레전드",
    date: "2026-08-11",
    views: 430,
    likes: 31,
  },
  {
    id: 3,
    title: "스킨 쿠폰 나눔합니다",
    game: "발로란트",
    date: "2026-08-01",
    views: 120,
    likes: 15,
  },
];

// 오행 스탯 데이터
const fiveElements = [
  {
    name: "목 (Wood)",
    attr: "성장/기획",
    value: 85,
    colorClass: "bg-[#3A9AFF]",
  },
  {
    name: "화 (Fire)",
    attr: "공격/열정",
    value: 92,
    colorClass: "bg-[#FF5E5E]",
  },
  {
    name: "토 (Earth)",
    attr: "방어/안정",
    value: 60,
    colorClass: "bg-[#F1FF5E]",
  },
  {
    name: "금 (Metal)",
    attr: "결단/원거리",
    value: 78,
    colorClass: "bg-[#A0A0C0]",
  },
  {
    name: "수 (Water)",
    attr: "유연/지략",
    value: 88,
    colorClass: "bg-[#3A9AFF]",
  },
];

// 전달받은 운세 API 데이터 구조
const fortuneData = {
  overallFortune: {
    score: 75,
    title: "점진적인 준비와 협력으로 빛나는 하루",
    content:
      "오늘은 부족한 기운을 보완하며 새로운 시도를 차근차근 준비하기에 좋은 날입니다. 무리하게 세력을 확장하거나 독단적으로 플레이하기보다는 페이스를 조절하며 팀원들과 호흡을 맞추는 것이 핵심입니다.",
  },
  gameFortunes: [
    {
      gameId: 1,
      score: 76,
      title: "합류 타이밍과 팀워크 중심의 플레이",
      content:
        "혼자 라인을 무리하게 밀어붙이기보다 맵 판단과 팀원과의 소통에 집중해 보세요. 차근차근 전력을 다지며 적절한 타이밍에 합류하는 전술이 팀 전체에 긍정적인 흐름을 가져옵니다.",
      buff: {
        name: "협동의 시너지",
        effect: "아군과 합류하여 교전 시 시야 확보 및 집중력 향상",
      },
      caution: {
        name: "과욕 주의",
        effect: "무리한 1대1 교전 시도로 인한 페이스 난조",
      },
    },
  ],
};

function getScoreColorClass(score) {
  if (score >= 80) return "text-[#F1FF5E]";
  if (score >= 60) return "text-[#3A9AFF]";
  return "text-[#7a8fff]";
}

function getScoreHex(score) {
  if (score >= 80) return "#F1FF5E";
  if (score >= 60) return "#3A9AFF";
  return "#7a8fff";
}

export default function MyPage() {
  const [activeTab, setActiveTab] = useState("선호 게임");
  const [selectedGames, setSelectedGames] = useState(["lol", "val"]);

  // 생년월일시 상태
  const [birthDate, setBirthDate] = useState("1998-05-15");
  const [birthTime, setBirthTime] = useState("14:30");

  const { overallFortune, gameFortunes } = fortuneData;
  const overallColorClass = getScoreColorClass(overallFortune.score);
  const overallHex = getScoreHex(overallFortune.score);

  const toggleGame = (id) => {
    if (selectedGames.includes(id)) {
      setSelectedGames(selectedGames.filter((g) => g !== id));
    } else {
      setSelectedGames([...selectedGames, id]);
    }
  };

  return (
    <div className="bg-[#06040f] min-h-screen text-white selection:bg-[#F1FF5E] selection:text-[#1C0770]">
      <div className="max-w-4xl mx-auto px-6 md:px-10 py-10">
        {/* Header */}
        <div className="mb-8 border-b border-[#3A9AFF]/10 pb-6">
          <p className="text-xs uppercase tracking-widest mb-1 font-bold text-[#3A9AFF]">
            마이페이지
          </p>
          <h1 className="text-3xl md:text-4xl font-extrabold font-['Rajdhani'] text-[#f0f0fa]">
            Hello
          </h1>
          <p className="text-xs text-[#f0f0fa]/40 mt-1">
            @1234 · 가입일 2024.03
          </p>
        </div>

        {/* ✦ 오늘의 종합 게임 운세 */}
        <div className="rounded mb-12 overflow-hidden relative bg-[#0d0b1e] border border-[#3A9AFF]/20">
          <div className="relative p-8 py-10 flex flex-col md:flex-row gap-6 items-start">
            {/* Score ring */}
            <div className="flex flex-col items-center gap-2 flex-shrink-0">
              <div className="relative flex items-center justify-center w-24 h-20">
                <svg className="w-24 h-24 absolute -rotate-90">
                  <circle
                    cx="50"
                    cy="50"
                    r="40"
                    fill="none"
                    stroke="rgba(255,255,255,0.06)"
                    strokeWidth="6"
                  />
                  <circle
                    cx="50"
                    cy="50"
                    r="40"
                    fill="none"
                    stroke={overallHex}
                    strokeWidth="6"
                    strokeLinecap="round"
                    strokeDasharray={`${(overallFortune.score / 100) * 251.2} 251.2`}
                  />
                </svg>
                <div className="text-center z-10">
                  <p
                    className={`text-2xl font-black font-['Rajdhani'] leading-none ${overallColorClass}`}
                  >
                    {overallFortune.score}
                  </p>
                  <p className="text-xs text-[#f0f0fa]/35">/ 100</p>
                </div>
              </div>
            </div>

            {/* 오늘의 운세 설명*/}
            <div className="flex-1 min-w-0">
              <h3
                className={`font-bold text-xl mb-2 font-['Rajdhani'] ${overallColorClass}`}
              >
                {overallFortune.title}
              </h3>
              <p className="text-sm leading-relaxed text-[#f0f0fa]/75">
                {overallFortune.content}
              </p>
            </div>
          </div>

          {/* 선호 게임 운세 */}
          {gameFortunes.length > 0 && (
            <div className="border-t border-white/10 p-6 ">
              <p className="text-xs font-bold text-[#3A9AFF] uppercase tracking-wider mb-4">
                오늘의 게임 운세
              </p>
              <div className="grid grid-cols-1 gap-4">
                {gameFortunes.map((gf) => {
                  const matchedGame = availableGames.find(
                    (g) => g.gameId === gf.gameId,
                  );
                  if (!matchedGame || !selectedGames.includes(matchedGame.id))
                    return null;

                  const gfColorClass = getScoreColorClass(gf.score);

                  return (
                    <div key={gf.gameId}>
                      <div className="flex justify-between items-center mb-2">
                        <span className="font-bold text-sm text-[#f0f0fa]">
                          {matchedGame.name}
                        </span>
                      </div>
                      <p
                        className={`text-xs font-semibold mb-1 ${gfColorClass}`}
                      >
                        {gf.title}
                      </p>
                      <p className="text-xs leading-relaxed text-[#f0f0fa]/60 mb-3">
                        {gf.content}
                      </p>

                      <div className="grid grid-cols-1 sm:grid-cols-2 gap-2 text-xs">
                        {gf.buff && (
                          <div className="p-2.5 rounded bg-[#3A9AFF]/10 border border-[#3A9AFF]/20">
                            <span className="font-bold text-[#3A9AFF] block mb-0.5">
                              버프 : {gf.buff.name}
                            </span>
                            <span className="text-[#f0f0fa]/60 text-[11px]">
                              {gf.buff.effect}
                            </span>
                          </div>
                        )}
                        {gf.caution && (
                          <div className="p-2.5 rounded bg-[#FF5E5E]/10 border border-[#FF5E5E]/20">
                            <span className="font-bold text-[#FF5E5E] block mb-0.5">
                              주의 : {gf.caution.name}
                            </span>
                            <span className="text-[#f0f0fa]/60 text-[11px]">
                              {gf.caution.effect}
                            </span>
                          </div>
                        )}
                      </div>
                    </div>
                  );
                })}
              </div>
            </div>
          )}
        </div>

        {/* Navigation Tabs */}
        <div className="w-full flex gap-2 mb-8 border-b border-[#3A9AFF]/15 pb-3 overflow-x-auto">
          {tabs.map((tab) => (
            <button
              key={tab}
              onClick={() => setActiveTab(tab)}
              className={`px-5 py-2 rounded text-sm font-bold transition-all cursor-pointer whitespace-nowrap ${
                activeTab === tab
                  ? "bg-[#261CC1]/40 text-[#F1FF5E] border border-[#F1FF5E]/30"
                  : "text-[#f0f0fa]/50 hover:text-white"
              }`}
            >
              {tab}
            </button>
          ))}
        </div>

        {/* Tab 1: 선호 게임 */}
        {activeTab === "선호 게임" && (
          <div className="bg-[#0d0b1e] border border-[#3A9AFF]/10 rounded p-6 md:p-8 shadow-xl">
            <div className="mb-6 flex justify-between items-end">
              <div>
                <h2 className="text-lg font-bold text-[#f0f0fa] mb-1 font-['Rajdhani']">
                  선호 게임 목록
                </h2>
              </div>
            </div>

            <div className="grid grid-cols-2 md:grid-cols-3 gap-4">
              {availableGames.map((game) => {
                const isSelected = selectedGames.includes(game.id);
                return (
                  <div
                    key={game.id}
                    onClick={() => toggleGame(game.id)}
                    className="group relative h-80 md:h-96 rounded overflow-hidden cursor-pointer transition-all"
                  >
                    <img
                      src={game.image}
                      alt={game.name}
                      className="w-full h-full object-cover transition-transform duration-300 group-hover:scale-110"
                    />
                    <div className="absolute inset-0 bg-gradient-to-t from-[#3A9AFF]/50 via-[#0d0b1e]/10 to-transparent" />

                    <div className="absolute inset-0 p-4 flex flex-col justify-between">
                      <div className="flex justify-between items-start">
                        <span className="text-[10px] font-bold px-2.5 py-0.5 rounded bg-[#06040f]/80 text-[#3A9AFF] border border-[#3A9AFF]/20 backdrop-blur-sm">
                          {game.category}
                        </span>
                      </div>
                      <h3 className="font-bold text-sm md:text-base text-[#f0f0fa] tracking-wide">
                        {game.name}
                      </h3>
                    </div>
                  </div>
                );
              })}
            </div>
          </div>
        )}

        {/* Tab 2: 오행 */}
        {activeTab === "오행" && (
          <div className="bg-[#0d0b1e] border border-[#3A9AFF]/10 rounded p-6 md:p-8 shadow-xl">
            <div className="mb-6">
              <h2 className="text-lg font-bold text-[#f0f0fa] mb-1 font-['Rajdhani']">
                오행 플레이 성향 분석
              </h2>
              <p className="text-xs text-[#f0f0fa]/40">
                내 게임 스타일과 매칭된 오행 기운 수치입니다.
              </p>
            </div>

            <div className="flex flex-col gap-5">
              {fiveElements.map((elem) => (
                <div key={elem.name} className="flex flex-col gap-2">
                  <div className="flex justify-between items-center text-xs">
                    <span className="font-bold text-[#f0f0fa]">
                      {elem.name}{" "}
                      <span className="text-[#f0f0fa]/40 font-normal ml-2">
                        ({elem.attr})
                      </span>
                    </span>
                    <span className="font-bold text-[#3A9AFF]">
                      {elem.value}%
                    </span>
                  </div>
                  <div className="w-full h-2 rounded bg-white/5 overflow-hidden">
                    <div
                      className={`h-full rounded transition-all duration-500 ${elem.colorClass}`}
                      style={{ width: `${elem.value}%` }}
                    />
                  </div>
                </div>
              ))}
            </div>
          </div>
        )}

        {/* Tab 3: 내 파티 */}
        {activeTab === "내 파티" && (
          <div className="flex flex-col gap-3">
            {myParties.map((party) => (
              <div
                key={party.id}
                className="flex items-center justify-between gap-4 px-6 md:px-8 py-5 rounded bg-[#0d0b1e] border border-[#3A9AFF]/10 hover:border-[#3A9AFF]/50 transition-all cursor-pointer"
              >
                <div className="flex-1 min-w-0 pr-4">
                  <p className="font-bold text-sm text-[#f0f0fa] truncate mb-1">
                    {party.title}
                  </p>
                  <p className="text-xs text-[#f0f0fa]/40">
                    {party.game} · {party.date}
                  </p>
                </div>
                <div className="flex items-center gap-4">
                  <span className="text-xs font-medium text-[#f0f0fa]/60">
                    {party.members}
                  </span>
                  <span
                    className={`text-xs font-bold px-3 py-1 rounded ${
                      party.status === "모집 중"
                        ? "bg-[#F1FF5E]/10 text-[#F1FF5E] border border-[#F1FF5E]/30"
                        : "bg-white/5 text-[#f0f0fa]/40 border border-[#3A9AFF]/10"
                    }`}
                  >
                    {party.status}
                  </span>
                </div>
              </div>
            ))}
          </div>
        )}

        {/* Tab 4: 작성 글 */}
        {activeTab === "작성 글" && (
          <div className="flex flex-col gap-3">
            {myPosts.map((post) => (
              <div
                key={post.id}
                className="flex items-center justify-between gap-4 px-6 md:px-8 py-5 rounded bg-[#0d0b1e] border border-[#3A9AFF]/10 hover:border-[#3A9AFF]/50 transition-all cursor-pointer"
              >
                <div className="flex-1 min-w-0 pr-4">
                  <div className="flex items-center gap-2 mb-1.5">
                    <p className="text-sm font-bold text-[#f0f0fa] truncate">
                      {post.title}
                    </p>
                  </div>
                  <p className="text-xs text-[#f0f0fa]/40">
                    {post.game} · {post.date}
                  </p>
                </div>
                <div className="hidden md:flex items-center gap-4 text-xs text-[#f0f0fa]/40">
                  <span>👁 {post.views.toLocaleString()}</span>
                  <span>👍 {post.likes}</span>
                </div>
              </div>
            ))}
          </div>
        )}

        {/* Tab 5: 설정 */}
        {activeTab === "설정" && (
          <div className="bg-[#0d0b1e] border border-[#3A9AFF]/10 rounded p-6 md:p-8 shadow-xl flex flex-col gap-8">
            {/* 기본 정보 설정 */}
            <div className="flex flex-col gap-4">
              <h2 className="text-base font-bold text-[#f0f0fa] font-['Rajdhani']">
                계정 설정
              </h2>
              {[
                { label: "닉네임", value: "Xenocraft", type: "text" },
                { label: "이메일", value: "xeno@example.com", type: "email" },
              ].map((field) => (
                <div key={field.label}>
                  <label className="block text-xs font-semibold text-[#f0f0fa]/60 mb-2 uppercase tracking-wider">
                    {field.label}
                  </label>
                  <input
                    type={field.type}
                    defaultValue={field.value}
                    className="w-full px-4 py-3 rounded text-sm bg-white/5 border border-[#3A9AFF]/15 text-[#f0f0fa] focus:outline-none focus:border-[#3A9AFF] transition-colors"
                  />
                </div>
              ))}
            </div>

            <hr className="border-[#3A9AFF]/10" />

            {/* 사주/운세 정보 설정 (생년월일시) */}
            <div className="flex flex-col gap-4">
              <div>
                <h2 className="text-base font-bold text-[#f0f0fa] font-['Rajdhani'] mb-1">
                  사주 정보 (운세 분석용)
                </h2>
                <p className="text-xs text-[#f0f0fa]/40">
                  정확한 운세 및 오행 성향 분석을 위한 생년월일과 출생시입니다.
                </p>
              </div>

              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                  <label className="block text-xs font-semibold text-[#f0f0fa]/60 mb-2 uppercase tracking-wider">
                    생년월일
                  </label>
                  <input
                    type="date"
                    value={birthDate}
                    onChange={(e) => setBirthDate(e.target.value)}
                    className="w-full px-4 py-3 rounded text-sm bg-white/5 border border-[#3A9AFF]/15 text-[#f0f0fa] focus:outline-none focus:border-[#3A9AFF] transition-colors scheme-dark"
                  />
                </div>

                <div>
                  <label className="block text-xs font-semibold text-[#f0f0fa]/60 mb-2 uppercase tracking-wider">
                    출생시
                  </label>
                  <input
                    type="time"
                    value={birthTime}
                    onChange={(e) => setBirthTime(e.target.value)}
                    className="w-full px-4 py-3 rounded text-sm bg-white/5 border border-[#3A9AFF]/15 text-[#f0f0fa] focus:outline-none focus:border-[#3A9AFF] transition-colors scheme-dark"
                  />
                </div>
              </div>
            </div>

            <hr className="border-[#3A9AFF]/10" />

            {/* 선호 게임 관리 */}
            <div>
              <h2 className="text-base font-bold text-[#f0f0fa] font-['Rajdhani'] mb-1">
                선호 게임 관리
              </h2>
              <p className="text-xs text-[#f0f0fa]/40 mb-4">
                등록할 선호 게임을 추가하거나 삭제할 수 있습니다.
              </p>

              <div className="flex flex-wrap gap-2">
                {availableGames.map((game) => {
                  const isSelected = selectedGames.includes(game.id);
                  return (
                    <button
                      key={game.id}
                      onClick={() => toggleGame(game.id)}
                      className={`px-4 py-2 rounded text-xs font-bold border transition-all flex items-center gap-2 cursor-pointer ${
                        isSelected
                          ? "bg-[#F1FF5E]/10 border-[#F1FF5E]/30 text-[#F1FF5E]"
                          : "bg-white/5 border-[#3A9AFF]/15 text-[#f0f0fa]/40 hover:text-white"
                      }`}
                    >
                      <span>{game.name}</span>
                      <span>{isSelected ? "✕" : "+"}</span>
                    </button>
                  );
                })}
              </div>
            </div>

            {/* 저장 버튼 */}
            <div className="pt-2 flex justify-end border-t border-[#3A9AFF]/10">
              <button
                onClick={() => alert("설정이 성공적으로 저장되었습니다.")}
                className="px-6 py-2.5 rounded text-sm font-bold bg-[#F1FF5E] text-[#06040f] hover:brightness-110 transition-all cursor-pointer"
              >
                변경사항 저장
              </button>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

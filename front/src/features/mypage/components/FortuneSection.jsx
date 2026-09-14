import { useTodayFortune } from "../hooks/useTodayFortune";

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

export default function FortuneSection({ games = [] }) {
  const { fortune, isLoading, error, fetchFortune } = useTodayFortune();

  if (!fortune && !isLoading && !error) {
    return (
      <div className="rounded mb-12 overflow-hidden bg-[#0d0b1e] border border-[#3A9AFF]/20 p-8 text-center">
        <p className="text-sm text-[#f0f0fa]/60 mb-4">
          오늘의 게임 운세를 아직 확인하지 않았습니다.
        </p>
        <button
          onClick={fetchFortune}
          className="px-6 py-2.5 rounded text-sm font-bold bg-[#F1FF5E] text-[#06040f] hover:brightness-110 transition-all"
        >
          오늘의 운세 보기
        </button>
      </div>
    );
  }

  if (isLoading) {
    return (
      <div className="rounded mb-12 overflow-hidden bg-[#0d0b1e] border border-[#3A9AFF]/20 p-8 text-center">
        <p className="text-sm text-[#f0f0fa]/60">운세를 불러오는 중...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="rounded mb-12 overflow-hidden bg-[#0d0b1e] border border-[#3A9AFF]/20 p-8 text-center">
        <p className="text-sm text-red-400 mb-4">{error.message}</p>
        <button
          onClick={fetchFortune}
          className="px-6 py-2.5 rounded text-sm font-bold bg-white/10 text-white hover:bg-white/20 transition-all"
        >
          다시 시도
        </button>
      </div>
    );
  }

  const { overallFortune, gameFortunes, dailyQuest, oneLineMessage } = fortune;
  const overallColorClass = getScoreColorClass(overallFortune.score);
  const overallHex = getScoreHex(overallFortune.score);

  return (
    <div className="rounded mb-12 overflow-hidden relative bg-[#0d0b1e] border border-[#3A9AFF]/20">
      <div className="relative p-8 py-10 flex flex-col md:flex-row gap-6 items-start">
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

        <div className="flex-1 min-w-0">
          <h3
            className={`font-bold text-xl mb-2 font-['Rajdhani'] ${overallColorClass}`}
          >
            {overallFortune.title}
          </h3>
          <p className="text-sm leading-relaxed text-[#f0f0fa]/75">
            {overallFortune.content}
          </p>
          {oneLineMessage && (
            <p className="text-xs text-[#F1FF5E] mt-3">"{oneLineMessage}"</p>
          )}
        </div>
      </div>

      {gameFortunes?.length > 0 && (
        <div className="border-t border-white/10 p-6">
          <p className="text-xs font-bold text-[#3A9AFF] uppercase tracking-wider mb-4">
            오늘의 게임 운세
          </p>
          <div className="grid grid-cols-1 gap-4">
            {gameFortunes.map((gf) => {
              const matchedGame = games.find((g) => g.gameId === gf.gameId);
              const gfColorClass = getScoreColorClass(gf.score);

              return (
                <div key={gf.gameId}>
                  {matchedGame && (
                    <span className="font-bold text-sm text-[#f0f0fa] block mb-2">
                      {matchedGame.name}
                    </span>
                  )}
                  <p className={`text-xs font-semibold mb-1 ${gfColorClass}`}>
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

      {dailyQuest && (
        <div className="border-t border-white/10 p-6">
          <p className="text-xs font-bold text-[#F1FF5E] uppercase tracking-wider mb-2">
            오늘의 퀘스트
          </p>
          <p className="text-sm font-bold text-[#f0f0fa] mb-1">
            {dailyQuest.title}
          </p>
          <p className="text-xs text-[#f0f0fa]/60 mb-1">{dailyQuest.mission}</p>
          <p className="text-xs text-[#F1FF5E]/80">보상: {dailyQuest.reward}</p>
        </div>
      )}
    </div>
  );
}

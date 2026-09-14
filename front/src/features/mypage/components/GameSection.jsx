import { useState } from "react";
import { useMyGames } from "../hooks/useMyGames";

// 게임 이미지가 없을 때(coverUrl이 null) 보여줄 기본 배경
const FALLBACK_COVER =
  "https://images.unsplash.com/photo-1550745165-9bc0b252726f?auto=format&fit=crop&w=600&q=80";

// 마이페이지 "선호 게임" 탭
// - 목록 조회: GET /api/users/me/games
// - 삭제: DELETE /api/users/me/games/{gameId}
// - 대표 지정: PATCH /api/users/me/games/{gameId}/main
// - 새 게임 "추가"는 전체 게임 카탈로그(GET /api/games)가 아직 없어서 미구현 상태
//   (백엔드에서 해당 API 나오면 여기에 추가 버튼/모달을 붙이면 됨)
export default function GameSection() {
  const { games, isLoading, error, isMutating, isMaxed, removeGame, makeMain } =
    useMyGames();

  const [actionError, setActionError] = useState(null);

  const handleRemove = async (gameId) => {
    setActionError(null);
    try {
      await removeGame(gameId);
    } catch (err) {
      const status = err.response?.status;
      if (status === 404) {
        setActionError("이미 삭제되었거나 등록되지 않은 게임입니다.");
      } else {
        setActionError("게임 삭제에 실패했습니다. 다시 시도해주세요.");
      }
    }
  };

  const handleMakeMain = async (gameId) => {
    setActionError(null);
    try {
      await makeMain(gameId);
    } catch (err) {
      const status = err.response?.status;
      if (status === 404) {
        setActionError("등록된 선호 게임이 없어 대표로 지정할 수 없습니다.");
      } else {
        setActionError("대표 게임 지정에 실패했습니다. 다시 시도해주세요.");
      }
    }
  };

  if (isLoading) {
    return (
      <div className="bg-[#0d0b1e] border border-[#3A9AFF]/10 rounded p-6 md:p-8 shadow-xl">
        <p className="text-sm text-[#f0f0fa]/40 text-center py-10">
          불러오는 중...
        </p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="bg-[#0d0b1e] border border-[#3A9AFF]/10 rounded p-6 md:p-8 shadow-xl">
        <p className="text-sm text-red-400 text-center py-10">
          선호 게임을 불러오지 못했습니다. 잠시 후 다시 시도해주세요.
        </p>
      </div>
    );
  }

  return (
    <div className="bg-[#0d0b1e] border border-[#3A9AFF]/10 rounded p-6 md:p-8 shadow-xl">
      <div className="mb-6 flex justify-between items-end">
        <div>
          <h2 className="text-lg font-bold text-[#f0f0fa] mb-1 font-['Rajdhani']">
            선호 게임 목록
          </h2>
          <p className="text-xs text-[#f0f0fa]/40">
            {games.length} / 5개 등록됨
            {isMaxed && " (최대 등록 개수에 도달했습니다)"}
          </p>
        </div>
      </div>

      {games.length === 0 && (
        <p className="text-sm text-[#f0f0fa]/40 text-center py-10">
          아직 등록한 선호 게임이 없습니다.
        </p>
      )}

      {games.length > 0 && (
        <div className="grid grid-cols-2 md:grid-cols-3 gap-4">
          {games.map((game) => (
            <div
              key={game.gameId}
              className="group relative h-80 md:h-96 rounded overflow-hidden"
            >
              <img
                src={game.coverUrl || FALLBACK_COVER}
                alt={game.name}
                className="w-full h-full object-cover transition-transform duration-300 group-hover:scale-110"
              />
              <div className="absolute inset-0 bg-gradient-to-t from-[#3A9AFF]/50 via-[#0d0b1e]/10 to-transparent" />

              <div className="absolute inset-0 p-4 flex flex-col justify-between">
                <div className="flex justify-between items-start">
                  {game.genre && (
                    <span className="text-[10px] font-bold px-2.5 py-0.5 rounded bg-[#06040f]/80 text-[#3A9AFF] border border-[#3A9AFF]/20 backdrop-blur-sm">
                      {game.genre}
                    </span>
                  )}
                  {game.isMain && (
                    <span className="text-[10px] font-bold px-2.5 py-0.5 rounded bg-[#F1FF5E] text-[#06040f]">
                      대표
                    </span>
                  )}
                </div>

                <div>
                  <h3 className="font-bold text-sm md:text-base text-[#f0f0fa] tracking-wide mb-3">
                    {game.name}
                  </h3>
                  <div className="flex gap-2">
                    {!game.isMain && (
                      <button
                        disabled={isMutating}
                        onClick={() => handleMakeMain(game.gameId)}
                        className="flex-1 text-[11px] font-bold px-2 py-1.5 rounded bg-white/10 text-white hover:bg-[#F1FF5E]/20 hover:text-[#F1FF5E] transition-all disabled:opacity-40 disabled:cursor-not-allowed"
                      >
                        대표 지정
                      </button>
                    )}
                    <button
                      disabled={isMutating}
                      onClick={() => handleRemove(game.gameId)}
                      className="flex-1 text-[11px] font-bold px-2 py-1.5 rounded bg-white/10 text-white hover:bg-red-500/20 hover:text-red-400 transition-all disabled:opacity-40 disabled:cursor-not-allowed"
                    >
                      삭제
                    </button>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}

      {actionError && (
        <p className="text-xs text-red-400 mt-4">{actionError}</p>
      )}

      <div className="mt-6 pt-6 border-t border-white/5">
        <p className="text-xs text-[#f0f0fa]/30">
          + 새 게임 추가 기능은 전체 게임 목록 API가 준비되는 대로 열릴
          예정입니다.
        </p>
      </div>
    </div>
  );
}

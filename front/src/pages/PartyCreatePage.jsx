import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { api } from "../api/client";

const chemistryOptions = [
  { value: "SYNERGY", label: "상생" },
  { value: "RIVAL", label: "상극" },
  { value: "BALANCED", label: "균형" },
];

export default function PartyCreatePage() {
  const navigate = useNavigate();
  const [games, setGames] = useState([]);
  const [gameId, setGameId] = useState(null);
  const [gamesLoading, setGamesLoading] = useState(true);
  const [title, setTitle] = useState("");
  const [totalSlots, setTotalSlots] = useState(4);
  const [chemistry, setChemistry] = useState(chemistryOptions[0].value);
  const [error, setError] = useState(null);

  useEffect(() => {
    api
      .get("/users/me/games", { params: { gameId } })
      .then((res) => {
        setGames(res.data.data);
        setError(null);
      })
      .catch((err) => {
        console.error(err);
        setError("게임 목록을 불러오지 못했습니다.");
      })
      .finally(() => setGamesLoading(false));
  }, [gameId]);

  const hasNoGames = !gamesLoading && games.length === 0;

  const handleSubmit = (e) => {
    e.preventDefault();
    api
      .post("/party/create", {
        title,
        gameId: Number(gameId),
        maxMemberCount: totalSlots,
        chemistryType: chemistry,
      })
      .then(() => {
        navigate("/party");
      })
      .catch((err) => {
        console.error(err);
        setError("파티 생성에 실패했습니다.");
      });
  };

  let gameField;
  if (gamesLoading) {
    gameField = (
      <p className="text-sm" style={{ color: "#c4c4d6" }}>
        불러오는 중...
      </p>
    );
  } else if (hasNoGames) {
    gameField = (
      <p className="text-sm" style={{ color: "#c4c4d6" }}>
        등록된 선호 게임이 없습니다.
      </p>
    );
  } else {
    gameField = (
      <select
        value={gameId}
        onChange={(e) => setGameId(e.target.value)}
        className="w-full px-4 py-2.5 rounded text-sm outline-none"
        style={{
          background: "rgba(38,28,193,0.14)",
          border: "1px solid rgba(58,154,255,0.35)",
          color: "#e8e8f0",
        }}
      >
        {games.map((g) => (
          <option key={g.gameId} value={g.gameId}>
            {g.name}
          </option>
        ))}
      </select>
    );
  }

  return (
    <div
      style={{
        position: "relative",
        minHeight: "100vh",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        padding: "48px 24px",
        background:
          "radial-gradient(circle at 15% 20%, rgba(38,28,193,0.35), transparent 55%), radial-gradient(circle at 85% 80%, rgba(58,154,255,0.25), transparent 55%), #07070e",
      }}
    >
      <div
        style={{
          position: "relative",
          zIndex: 1,
          width: "100%",
          maxWidth: "560px",
        }}
      >
        <button
          onClick={() => navigate("/party")}
          className="text-sm mb-3"
          style={{ color: "#c4c4d6" }}
        >
          ← 목록으로
        </button>
        <div
          style={{
            background: "rgba(255, 255, 255, 0.20)",
            border: "2px solid rgba(255,255,255,0.08)",
            borderRadius: "20px",
            padding: "40px",
            boxShadow: "0 12px 40px rgba(0,0,0,0.5)",
          }}
        >
          <p
            className="text-xs uppercase tracking-widest mb-2"
            style={{ color: "#3A9AFF" }}
          >
            파티를 만들어 게임을 함께 즐기세요!
          </p>
          <h1
            className="text-4xl font-bold mb-10"
            style={{ fontFamily: "'Rajdhani', sans-serif" }}
          >
            파티 생성
          </h1>

          <form onSubmit={handleSubmit} className="flex flex-col gap-6">
            <div>
              <label
                className="block text-xs mb-2 font-extrabold"
                style={{ color: "#c4c4d6" }}
              >
                게임
              </label>
              {gameField}
            </div>

            <div>
              <label
                className="block text-xs font-extrabold mb-2"
                style={{ color: "#c4c4d6" }}
              >
                파티 제목
              </label>
              <input
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                placeholder="예) 다이아 랭크 듀오 구합니다"
                className="w-full px-4 py-2.5 rounded text-sm outline-none"
                style={{
                  background: "rgba(255, 255, 255, 0.25)",
                  border: "1px solid rgba(255, 255, 255, 0.2)",
                  color: "#e8e8f0",
                }}
              />
            </div>

            <div className="grid grid-cols-2 gap-4">
              <div>
                <label
                  className="block text-xs font-extrabold mb-2"
                  style={{ color: "#c4c4d6" }}
                >
                  총 인원
                </label>
                <input
                  type="number"
                  min={2}
                  max={8}
                  value={totalSlots}
                  onChange={(e) => setTotalSlots(Number(e.target.value))}
                  className="w-full px-4 py-2.5 rounded text-sm outline-none"
                  style={{
                    background: "rgba(255, 255, 255, 0.25)",
                    border: "1px solid rgba(255, 255, 255, 0.2)",
                    color: "#e8e8f0",
                  }}
                />
              </div>
            </div>

            <div>
              <label
                className="block text-xs font-extrabold mb-2"
                style={{ color: "#c4c4d6" }}
              >
                궁합 유형
              </label>
              <div className="flex gap-2">
                {chemistryOptions.map((opt) => (
                  <button
                    key={opt.value}
                    type="button"
                    onClick={() => setChemistry(opt.value)}
                    className="px-4 py-2 rounded text-sm font-medium transition-all"
                    style={
                      chemistry === opt.value
                        ? {
                            background: "rgba(38,28,193,0.35)",
                            color: "#3A9AFF",
                            border: "1px solid #3A9AFF",
                          }
                        : {
                            background: "rgba(255,255,255,0.03)",
                            color: "#8888a0",
                            border: "1px solid rgba(255,255,255,0.08)",
                          }
                    }
                  >
                    {opt.label}
                  </button>
                ))}
              </div>
            </div>

            <button
              type="submit"
              disabled={hasNoGames}
              className="mt-2 px-6 py-3 rounded text-sm font-semibold disabled:opacity-40 disabled:cursor-not-allowed"
              style={{
                background: "linear-gradient(135deg, #261CC1, #1C0770)",
                color: "#fff",
              }}
            >
              완료
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}

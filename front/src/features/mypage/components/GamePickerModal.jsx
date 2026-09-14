import { useEffect, useRef, useState } from "react";
import { Dialog, DialogPanel, DialogTitle } from "@headlessui/react";
import { getGameCatalog } from "../api/gameApi";

export default function GamePickerModal({ games, isMutating, onAdd, onClose }) {
  const [catalog, setCatalog] = useState([]);
  const [query, setQuery] = useState("");
  const [loading, setLoading] = useState(true);
  const [loadError, setLoadError] = useState(false);
  const [attempt, setAttempt] = useState(0);
  const [message, setMessage] = useState("");
  const [addingId, setAddingId] = useState(null);
  const busy = useRef(false);
  const [needsRefresh, setNeedsRefresh] = useState(false);

  useEffect(() => {
    let active = true;
    getGameCatalog().then((data) => {
      if (active) setCatalog(data);
    }).catch(() => {
      if (active) setLoadError(true);
    }).finally(() => {
      if (active) setLoading(false);
    });
    return () => { active = false; };
  }, [attempt]);

  const add = async (game) => {
    if (busy.current || isMutating || games.length >= 5 || needsRefresh) return;
    busy.current = true;
    setAddingId(game.gameId);
    setMessage("");
    try {
      await onAdd(game.gameId);
      setMessage(`${game.name} 게임을 추가했습니다.`);
    } catch (err) {
      const status = err.response?.status;
      if (status === 409) {
        setMessage("이미 등록한 게임이거나 최대 5개에 도달했습니다. 페이지를 새로고침해주세요.");
        setNeedsRefresh(true);
      } else if (status === 404) {
        setMessage("게임을 찾을 수 없습니다. 목록을 다시 열어주세요.");
      } else if (err.message?.startsWith("게임은 등록되었지만")) {
        setMessage(err.message);
        setNeedsRefresh(true);
      } else {
        setMessage("게임 추가에 실패했습니다. 다시 시도해주세요.");
      }
    } finally {
      busy.current = false;
      setAddingId(null);
    }
  };

  const pending = isMutating || addingId !== null;
  const filtered = catalog.filter((game) => game.name.toLocaleLowerCase().includes(query.trim().toLocaleLowerCase()));

  return (
    <Dialog open onClose={() => { if (!busy.current && !pending) onClose(); }} className="relative z-50">
      <div className="fixed inset-0 bg-black/70" aria-hidden="true" />
      <div className="fixed inset-0 flex items-center justify-center p-4">
        <DialogPanel className="flex max-h-[85dvh] w-full max-w-xl flex-col rounded-xl border border-[#3A9AFF]/20 bg-[#0d0b1e] p-5 text-[#f0f0fa] shadow-xl">
          <div className="flex items-center justify-between gap-4">
            <DialogTitle className="text-lg font-bold">선호 게임 추가</DialogTitle>
            <button type="button" onClick={onClose} disabled={pending} aria-label="게임 선택 닫기" className="rounded px-3 py-2 hover:bg-white/10 disabled:opacity-40">닫기</button>
          </div>
          <p className="mt-1 text-sm text-white/60">현재 {games.length} / 5개 등록</p>
          <label className="mt-4 text-sm" htmlFor="favorite-game-search">게임 이름 검색</label>
          <input id="favorite-game-search" type="search" data-autofocus value={query} onChange={(event) => setQuery(event.target.value)} placeholder="게임 이름을 입력하세요" className="mt-2 rounded border border-white/20 bg-white/5 px-3 py-2 outline-none focus:border-[#3A9AFF]" />
          <div className="mt-4 min-h-0 overflow-y-auto" aria-busy={loading}>
            {loading ? <p className="py-8 text-center text-white/60">게임 목록을 불러오는 중...</p> : loadError ? (
              <div className="py-8 text-center">
                <p role="alert">게임 목록을 불러오지 못했습니다.</p>
                <button type="button" className="mt-3 rounded bg-white/10 px-4 py-2" onClick={() => { setLoading(true); setLoadError(false); setAttempt((value) => value + 1); }}>다시 시도</button>
              </div>
            ) : filtered.length === 0 ? <p className="py-8 text-center text-white/60">{catalog.length === 0 ? "등록된 게임이 없습니다." : "검색 결과가 없습니다."}</p> : (
              <ul className="space-y-2">
                {filtered.map((game) => {
                  const registered = games.some((item) => item.gameId === game.gameId);
                  return (
                    <li key={game.gameId} className="flex items-center gap-3 rounded-lg bg-white/5 p-3">
                      {game.coverUrl ? <img src={game.coverUrl} alt="" className="h-14 w-12 shrink-0 rounded object-cover" /> : <div aria-hidden="true" className="flex h-14 w-12 shrink-0 items-center justify-center rounded bg-[#3A9AFF]/10">🎮</div>}
                      <div className="min-w-0 flex-1"><p className="break-words font-medium">{game.name}</p><p className="text-xs text-white/50">{game.genre}</p></div>
                      <button type="button" aria-label={`${game.name} ${registered ? "등록됨" : "추가"}`} disabled={registered || games.length >= 5 || pending || needsRefresh} onClick={() => add(game)} className="shrink-0 rounded bg-[#3A9AFF]/20 px-3 py-2 text-sm text-[#3A9AFF] disabled:cursor-not-allowed disabled:opacity-40">{registered ? "등록됨" : addingId === game.gameId ? "추가 중..." : "추가"}</button>
                    </li>
                  );
                })}
              </ul>
            )}
          </div>
          <div className="mt-4 border-t border-white/10 pt-4">
            {games.length >= 5 && <p className="mb-2 text-sm text-[#F1FF5E]">최대 5개까지 등록할 수 있습니다. 다른 게임을 추가하려면 기존 게임을 삭제해주세요.</p>}
            <p role="status" className="text-sm text-white/80">{message}</p>
            <button type="button" onClick={onClose} disabled={pending} className="mt-3 w-full rounded bg-[#3A9AFF] py-2 font-bold text-[#06040f] disabled:opacity-40">완료</button>
          </div>
        </DialogPanel>
      </div>
    </Dialog>
  );
}

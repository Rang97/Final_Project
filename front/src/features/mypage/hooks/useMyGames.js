import { useCallback, useEffect, useState } from "react";
import {
  getMyGames,
  registerGame,
  setMainGame,
  deleteGame,
} from "../api/gameApi";

const MAX_GAMES = 5;

export function useMyGames() {
  const [games, setGames] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isMutating, setIsMutating] = useState(false);

  const fetchGames = useCallback(async (background = false) => {
    if (!background) setIsLoading(true);
    if (!background) setError(null);
    try {
      const data = await getMyGames();
      setGames(data);
    } catch (err) {
      if (background) throw err;
      setError(err);
    } finally {
      if (!background) setIsLoading(false);
    }
  }, []);

  useEffect(() => {
    let active = true;
    getMyGames().then((data) => {
      if (active) setGames(data);
    }).catch((err) => {
      if (active) setError(err);
    }).finally(() => {
      if (active) setIsLoading(false);
    });
    return () => { active = false; };
  }, []);

  const isMaxed = games.length >= MAX_GAMES;

  const addGame = async (gameId) => {
    if (isMaxed) return;
    setIsMutating(true);
    try {
      await registerGame(gameId);
      try {
        await fetchGames(true);
      } catch {
        throw new Error("게임은 등록되었지만 목록을 갱신하지 못했습니다. 페이지를 새로고침해주세요.");
      }
    } finally {
      setIsMutating(false);
    }
  };

  const removeGame = async (gameId) => {
    setIsMutating(true);
    try {
      await deleteGame(gameId);
      await fetchGames();
    } finally {
      setIsMutating(false);
    }
  };

  const makeMain = async (gameId) => {
    setIsMutating(true);
    try {
      await setMainGame(gameId);
      await fetchGames();
    } finally {
      setIsMutating(false);
    }
  };

  return {
    games,
    isLoading,
    error,
    isMutating,
    isMaxed,
    addGame,
    removeGame,
    makeMain,
    refetch: fetchGames,
  };
}

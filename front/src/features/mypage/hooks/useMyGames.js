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

  const fetchGames = useCallback(async () => {
    setIsLoading(true);
    setError(null);
    try {
      const data = await getMyGames();
      setGames(data);
    } catch (err) {
      setError(err);
    } finally {
      setIsLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchGames();
  }, [fetchGames]);

  const isMaxed = games.length >= MAX_GAMES;

  const addGame = async (gameId) => {
    if (isMaxed) return;
    setIsMutating(true);
    try {
      await registerGame(gameId);
      await fetchGames();
    } catch (err) {
      throw err;
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

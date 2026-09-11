import apiClient from "../../../api/apiClient";

export const getMyGames = async () => {
  const response = await apiClient.get("/users/me/games");
  return response.data.data;
};

export const registerGame = async (gameId) => {
  await apiClient.post("/users/me/games", { gameId });
};

export const setMainGame = async (gameId) => {
  await apiClient.patch(`/users/me/games/${gameId}/main`);
};

export const deleteGame = async (gameId) => {
  await apiClient.delete(`/users/me/games/${gameId}`);
};

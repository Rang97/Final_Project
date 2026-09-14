import apiClient from "../../../api/apiClient";

export const getAllGames = async () => {
  const response = await apiClient.get("/games");
  return response.data.data;
};

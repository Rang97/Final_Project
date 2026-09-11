import apiClient from "../../../api/apiClient";

export const getTodayFortune = async () => {
  const response = await apiClient.post("/users/me/fortunes/today");
  return response.data.data;
};

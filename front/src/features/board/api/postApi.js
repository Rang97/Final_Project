import apiClient from "../../../api/apiClient";

export const getPosts = async ({ page = 0, size = 10 } = {}) => {
  const response = await apiClient.get("/posts", { params: { page, size } });
  return response.data.data;
};

export const getPost = async (postId) => {
  const response = await apiClient.get(`/posts/${postId}`);
  return response.data.data;
};

export const createPost = async ({ title, content }) => {
  const response = await apiClient.post("/posts", { title, content });
  return response.data.data;
};

export const updatePost = (postId, body) => {
  throw new Error("Not implemented yet");
};

export const deletePost = (postId) => {
  throw new Error("Not implemented yet");
};

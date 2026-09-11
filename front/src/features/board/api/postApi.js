import apiClient from "../../../api/apiClient";

export const getPosts = async ({ page = 0, size = 10 } = {}) => {
  const response = await apiClient.get("/posts", { params: { page, size } });
  return response.data.data;
};

export const getPost = async (postId) => {
  const response = await apiClient.get(`/posts/${postId}`);
  return response.data.data;
};

// POST /api/posts -> ApiResponse<Long> (생성된 postId 숫자를 그대로 반환)
export const createPost = async ({ title, content }) => {
  const response = await apiClient.post("/posts", { title, content });
  return response.data.data;
};

// PUT /api/posts/{postId} -> ApiResponse<Void>
export const updatePost = async (postId, { title, content }) => {
  await apiClient.put(`/posts/${postId}`, { title, content });
};

// DELETE /api/posts/{postId} -> ApiResponse<Void>
export const deletePost = async (postId) => {
  await apiClient.delete(`/posts/${postId}`);
};

import apiClient from "../../../api/apiClient";

export const getComments = async (postId) => {
  const response = await apiClient.get(`/posts/${postId}/comments`);
  return response.data.data;
};

// POST -> ApiResponse<Long> (생성된 commentId)
export const createComment = async (postId, { content }) => {
  const response = await apiClient.post(`/posts/${postId}/comments`, {
    content,
  });
  return response.data.data;
};

export const updateComment = async (postId, commentId, { content }) => {
  await apiClient.put(`/posts/${postId}/comments/${commentId}`, { content });
};

export const deleteComment = async (postId, commentId) => {
  await apiClient.delete(`/posts/${postId}/comments/${commentId}`);
};

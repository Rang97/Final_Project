import apiClient from "../../../api/apiClient";

export const getComments = async (postId) => {
  const response = await apiClient.get(`/posts/${postId}/comments`);
  return response.data.data;
};

export const createComment = async (postId, { content }) => {
  const response = await apiClient.post(`/posts/${postId}/comments`, {
    content,
  });
  return response.data.data;
};

export const updateComment = (postId, commentId, body) => {
  throw new Error("Not implemented yet");
};

export const deleteComment = (postId, commentId) => {
  throw new Error("Not implemented yet");
};

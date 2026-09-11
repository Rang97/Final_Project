import { useState } from "react";
import { createPost, updatePost, deletePost } from "../api/postApi";

export function useCreatePost() {
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [error, setError] = useState(null);

  const submitPost = async (body) => {
    setIsSubmitting(true);
    setError(null);
    try {
      const postId = await createPost(body);
      return postId;
    } catch (err) {
      setError(err);
      throw err;
    } finally {
      setIsSubmitting(false);
    }
  };

  return { submitPost, isSubmitting, error };
}

export function useUpdatePost() {
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [error, setError] = useState(null);

  const submitUpdate = async (postId, body) => {
    setIsSubmitting(true);
    setError(null);
    try {
      await updatePost(postId, body);
    } catch (err) {
      setError(err);
      throw err;
    } finally {
      setIsSubmitting(false);
    }
  };

  return { submitUpdate, isSubmitting, error };
}

export function useDeletePost() {
  const [isDeleting, setIsDeleting] = useState(false);
  const [error, setError] = useState(null);

  const removePost = async (postId) => {
    setIsDeleting(true);
    setError(null);
    try {
      await deletePost(postId);
    } catch (err) {
      setError(err);
      throw err;
    } finally {
      setIsDeleting(false);
    }
  };

  return { removePost, isDeleting, error };
}

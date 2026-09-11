import { useState } from "react";
import { createComment, updateComment, deleteComment } from "../api/commentApi";

export function useCommentSubmit(postId, onSuccess) {
  const [content, setContent] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [error, setError] = useState(null);

  const submitComment = async () => {
    const trimmed = content.trim();
    if (!trimmed || isSubmitting) return;

    setIsSubmitting(true);
    setError(null);
    try {
      await createComment(postId, { content: trimmed });
      setContent("");
      onSuccess?.();
    } catch (err) {
      setError(err);
    } finally {
      setIsSubmitting(false);
    }
  };

  return { content, setContent, submitComment, isSubmitting, error };
}

export function useCommentEdit(postId, onSuccess) {
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [error, setError] = useState(null);

  const editComment = async (commentId, content) => {
    const trimmed = content.trim();
    if (!trimmed) return;

    setIsSubmitting(true);
    setError(null);
    try {
      await updateComment(postId, commentId, { content: trimmed });
      onSuccess?.();
    } catch (err) {
      setError(err);
      throw err;
    } finally {
      setIsSubmitting(false);
    }
  };

  return { editComment, isSubmitting, error };
}

export function useCommentDelete(postId, onSuccess) {
  const [isDeleting, setIsDeleting] = useState(false);
  const [error, setError] = useState(null);

  const removeComment = async (commentId) => {
    setIsDeleting(true);
    setError(null);
    try {
      await deleteComment(postId, commentId);
      onSuccess?.();
    } catch (err) {
      setError(err);
      throw err;
    } finally {
      setIsDeleting(false);
    }
  };

  return { removeComment, isDeleting, error };
}

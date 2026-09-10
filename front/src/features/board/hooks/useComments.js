import { useState } from "react";
import { createComment } from "../api/commentApi";

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
      const newComment = await createComment(postId, { content: trimmed });
      setContent("");
      onSuccess?.(newComment);
    } catch (err) {
      setError(err);
    } finally {
      setIsSubmitting(false);
    }
  };

  return { content, setContent, submitComment, isSubmitting, error };
}

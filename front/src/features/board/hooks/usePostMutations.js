import { useState } from "react";
import { createPost } from "../api/postApi";

export function useCreatePost() {
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [error, setError] = useState(null);

  const submitPost = async (body) => {
    setIsSubmitting(true);
    setError(null);
    try {
      const created = await createPost(body);
      return created;
    } catch (err) {
      setError(err);
      throw err;
    } finally {
      setIsSubmitting(false);
    }
  };

  return { submitPost, isSubmitting, error };
}

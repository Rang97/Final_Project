import { useCallback, useEffect, useState } from "react";
import { getPosts } from "../api/postApi";

export const PAGE_SIZE = 10;

export function usePosts() {
  const [page, setPage] = useState(0);
  const [posts, setPosts] = useState([]);
  const [totalPages, setTotalPages] = useState(0);
  const [totalCount, setTotalCount] = useState(0);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchPosts = useCallback(async (targetPage) => {
    setIsLoading(true);
    setError(null);
    try {
      const pageResponse = await getPosts({
        page: targetPage,
        size: PAGE_SIZE,
      });
      setPosts(pageResponse.content);
      setTotalPages(pageResponse.totalPages);
      setTotalCount(pageResponse.totalCount);
    } catch (err) {
      setError(err);
    } finally {
      setIsLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchPosts(page);
  }, [page, fetchPosts]);

  return {
    posts,
    page,
    setPage,
    totalPages,
    totalCount,
    isLoading,
    error,
    refetch: () => fetchPosts(page),
  };
}

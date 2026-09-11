import { useNavigate } from "react-router-dom";
import { FiEye } from "react-icons/fi";
import { usePosts, PAGE_SIZE } from "../features/board/hooks/usePosts";
import { formatRelativeTime } from "../features/board/utils/formatDate";

export default function BoardPage() {
  const navigate = useNavigate();
  const { posts, page, setPage, totalPages, totalCount, isLoading, error } =
    usePosts();

  return (
    <div className="bg-[#06040f] min-h-screen text-white selection:bg-[#F1FF5E] selection:text-[#1C0770]">
      <div className="max-w-4xl mx-auto px-6 md:px-10 py-12">
        <div className="flex items-end justify-between mb-10">
          <div>
            <p className="text-xs uppercase tracking-widest mb-2 font-bold text-[#3A9AFF]">
              게이머 소통 공간
            </p>
            <h1 className="text-4xl font-extrabold tracking-tight font-['Rajdhani'] text-white">
              자유 게시판
            </h1>
          </div>
          <button
            onClick={() => navigate("/board/write")}
            className="px-5 py-2.5 rounded text-sm font-bold bg-[#F1FF5E] text-[#06040f] hover:transition-all hover:brightness-110 hover:cursor-pointer"
          >
            + 글쓰기
          </button>
        </div>

        {isLoading && (
          <div className="py-20 text-center text-sm text-slate-400">
            불러오는 중...
          </div>
        )}

        {!isLoading && error && (
          <div className="py-20 text-center text-sm text-red-400">
            게시글을 불러오지 못했습니다. 잠시 후 다시 시도해주세요.
          </div>
        )}

        {!isLoading && !error && posts.length === 0 && (
          <div className="py-20 text-center text-sm text-slate-400">
            등록된 게시글이 없습니다.
          </div>
        )}

        {!isLoading && !error && posts.length > 0 && (
          <div className="flex flex-col gap-3">
            {posts.map((post, i) => {
              const index = totalCount - page * PAGE_SIZE - i;

              return (
                <div
                  key={post.postId}
                  onClick={() => navigate(`/board/${post.postId}`)}
                  className="group flex items-center justify-between gap-4 px-8 py-6 rounded-sm cursor-pointer transition-all bg-[#261cc1]/15 text-[#a0a0c0] border border-[#0d0b1e] hover:border-[#F1FF5E]/50"
                >
                  <span className="font-['Rajdhani'] text-lg font-bold text-[#3A9AFF] group-hover:text-[#F1FF5E] transition-colors w-6 text-center">
                    {index}
                  </span>

                  <div className="flex-1 min-w-0">
                    <div className="flex items-center gap-2 mb-1.5">
                      <p className="text-base font-bold truncate text-white group-hover:text-[#F1FF5E] transition-colors">
                        {post.title}
                      </p>
                    </div>
                    <p className="text-xs font-medium text-slate-300/60">
                      {post.writerNickname} ·{" "}
                      {formatRelativeTime(post.createdAt)}
                    </p>
                  </div>

                  <div className="hidden md:flex items-center gap-4 text-sm text-slate-300/70 flex-shrink-0">
                    <span className="flex items-center gap-1">
                      <FiEye /> {post.viewCount.toLocaleString()}
                    </span>
                  </div>
                </div>
              );
            })}
          </div>
        )}

        {!isLoading && !error && totalPages > 1 && (
          <div className="flex justify-center gap-2 mt-12">
            {Array.from({ length: totalPages }, (_, idx) => idx).map((p) => (
              <button
                key={p}
                onClick={() => setPage(p)}
                className={`w-9 h-9 rounded-sm text-sm font-bold transition-all border ${
                  p === page
                    ? "bg-[rgba(38,28,193,0.3)] text-[#3A9AFF]"
                    : "bg-[#0d0b1e] text-slate-300/60 border-[#261CC1]/50 hover:border-[#3A9AFF] hover:text-white"
                }`}
              >
                {p + 1}
              </button>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}

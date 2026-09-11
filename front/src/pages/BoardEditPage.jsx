import { useEffect, useState } from "react";
import { FiArrowLeft } from "react-icons/fi";
import { useNavigate, useParams } from "react-router-dom";
import { usePost } from "../features/board/hooks/usePost";
import { useUpdatePost } from "../features/board/hooks/usePostMutations";

export default function BoardEditPage() {
  const { id: postId } = useParams();
  const navigate = useNavigate();

  const { post, isLoading: isLoadingPost, error: loadError } = usePost(postId);
  const { submitUpdate, isSubmitting } = useUpdatePost();

  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [formError, setFormError] = useState(null);

  useEffect(() => {
    if (post) {
      setTitle(post.title);
      setContent(post.content);
    }
  }, [post]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!title.trim() || !content.trim()) {
      setFormError("제목과 내용을 모두 입력해 주세요.");
      return;
    }

    try {
      await submitUpdate(postId, { title, content });
      alert("게시글이 수정되었습니다.");
      navigate(`/board/${postId}`);
    } catch (err) {
      const status = err.response?.status;
      if (status === 403) {
        setFormError("본인이 작성한 글만 수정할 수 있습니다.");
      } else {
        setFormError("게시글 수정에 실패했습니다. 다시 시도해주세요.");
      }
    }
  };

  if (isLoadingPost) {
    return (
      <div className="max-w-3xl mx-auto px-6 md:px-10 py-12 text-center text-sm text-slate-400">
        불러오는 중...
      </div>
    );
  }

  if (loadError || !post) {
    return (
      <div className="max-w-3xl mx-auto px-6 md:px-10 py-12 text-center text-sm text-red-400">
        게시글을 불러오지 못했습니다.
      </div>
    );
  }

  return (
    <div className="max-w-3xl mx-auto px-6 md:px-10 py-12">
      <button
        onClick={() => navigate(`/board/${postId}`)}
        className="flex items-center gap-2 text-sm mb-8 py-2 px-3 rounded-sm
          bg-[#3A9AFF]/15 text-[#3A9AFF] font-bold border-[#3A9AFF]  hover:bg-[#261CC1]/40 transition-colors hover:cursor-pointer"
      >
        <FiArrowLeft />
        게시글로 돌아가기
      </button>

      <div className="bg-[#0d0b1e] border border-[rgba(58,154,255,0.1)] rounded p-6 md:p-8 shadow-xl">
        <div className="mb-8 pb-6 border-b border-[rgba(58,154,255,0.08)]">
          <p className="text-xs uppercase tracking-widest mb-2 font-bold text-[#3A9AFF]">
            게이머 소통 공간
          </p>
          <h1 className="text-2xl md:text-3xl font-bold tracking-tight font-['Rajdhani'] text-[#f0f0fa]">
            게시글 수정
          </h1>
        </div>

        <form onSubmit={handleSubmit} className="flex flex-col gap-6">
          <div>
            <label className="block text-xs font-semibold text-[rgba(240,240,250,0.6)] mb-2 uppercase tracking-wider">
              제목
            </label>
            <input
              type="text"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              className="w-full px-4 py-3 rounded text-sm bg-[rgba(255,255,255,0.04)] border border-[rgba(58,154,255,0.15)] text-[#f0f0fa] placeholder-[rgba(240,240,250,0.28)] focus:outline-none focus:border-[#3A9AFF] transition-colors"
            />
          </div>

          <div>
            <label className="block text-xs font-semibold text-[rgba(240,240,250,0.6)] mb-2 uppercase tracking-wider">
              내용
            </label>
            <textarea
              rows={12}
              value={content}
              onChange={(e) => setContent(e.target.value)}
              className="w-full px-4 py-3 rounded text-sm bg-[rgba(255,255,255,0.04)] border border-[rgba(58,154,255,0.15)] text-[#f0f0fa] placeholder-[rgba(240,240,250,0.28)] focus:outline-none focus:border-[#3A9AFF] transition-colors resize-none leading-relaxed"
            />
          </div>

          {formError && <p className="text-xs text-red-400">{formError}</p>}

          <div className="flex justify-end gap-3 pt-6 border-t border-[rgba(58,154,255,0.08)]">
            <button
              type="button"
              onClick={() => navigate(`/board/${postId}`)}
              className="px-5 py-2.5 rounded text-sm font-semibold border border-[rgba(58,154,255,0.15)] text-[rgba(240,240,250,0.4)] hover:text-white hover:border-[#3A9AFF] transition-all cursor-pointer"
            >
              취소
            </button>
            <button
              type="submit"
              disabled={isSubmitting}
              className="px-6 py-2.5 rounded text-sm font-bold bg-[#F1FF5E] text-[#06040f] hover:brightness-110 transition-all cursor-pointer disabled:opacity-50 disabled:cursor-not-allowed"
            >
              {isSubmitting ? "저장 중..." : "저장"}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

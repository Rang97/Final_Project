import { useParams, useNavigate } from "react-router-dom";
import { useState } from "react";
import { posts } from "./BoardPage";
import { FiArrowLeft, FiEye, FiMessageSquare } from "react-icons/fi";

const sampleComments = [
  {
    id: 1,
    author: "GuildMember01",
    time: "1시간 전",
    body: "정말 유익한 정보네요! 감사합니다.",
  },
  {
    id: 2,
    author: "ProGamer_K",
    time: "45분 전",
    body: "저도 이 방법 써봤는데 효과 있었어요 ㅎㅎ",
  },
  {
    id: 3,
    author: "Newbie2026",
    time: "20분 전",
    body: "질문이 있는데요, 더 자세히 알 수 있을까요?",
  },
];

export default function BoardDetailPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [comment, setComment] = useState("");

  const post = posts.find((p) => p.id === Number(id));

  if (!post) {
    return (
      <div className="bg-[#06040f] min-h-screen text-slate-400 flex items-center justify-center">
        게시글을 찾을 수 없습니다.
      </div>
    );
  }

  return (
    <div className="bg-[#06040f] min-h-screen text-white selection:bg-[#F1FF5E] selection:text-[#1C0770]">
      <div className="max-w-3xl mx-auto px-6 md:px-10 py-12">
        {/* Back Button */}
        <button
          onClick={() => navigate("/board")}
          className="flex items-center gap-2 text-sm mb-8 py-2 px-3 rounded-sm
          bg-[#3A9AFF]/15 text-[#3A9AFF] font-bold border-[#3A9AFF]  hover:bg-[#261CC1]/40 transition-colors hover:cursor-pointer"
        >
          <FiArrowLeft />
          게시판으로 돌아가기
        </button>

        {/* Post Card */}
        <div className="bg-[#0d0b1e] rounded-sm p-6 mb-10 md:p-8 ">
          {/* Title */}
          <h1 className="font-bold mb-4 leading-snug text-2xl md:text-3xl text-white font-['Rajdhani']">
            {post.title}
          </h1>

          {/* Meta Info */}
          <div className="flex items-center justify-between pb-6 mb-6 border-b border-[#3A9AFF]/40 text-xs text-slate-300/70">
            <div className="flex items-center gap-2 font-medium">
              <span className="text-white font-bold">{post.author}</span>
              <span>·</span>
              <span>{post.time}</span>
            </div>
            <div className="flex items-center gap-5 font-semibold">
              <span className="flex items-center gap-2">
                <FiEye /> {post.views.toLocaleString()}
              </span>
              <span className="flex items-center gap-2">
                <FiMessageSquare /> {post.replies}
              </span>
            </div>
          </div>

          {/* Body */}
          <div className="text-sm md:text-base leading-relaxed whitespace-pre-line text-slate-200/90 font-normal">
            {post.body}
          </div>
        </div>

        {/* Comments Section */}
        <div className="bg-[#261CC1]/10 rounded-sm p-6 shadow-sm">
          <h2 className="font-medium mb-6  text-white font-['Rajdhani']">
            댓글 <span className="text-[#F1FF5E]">{post.replies}</span>
          </h2>

          {/* Comment Input */}
          <div className="flex gap-2 mb-8">
            <input
              type="text"
              placeholder="댓글을 입력하세요..."
              value={comment}
              onChange={(e) => setComment(e.target.value)}
              className="flex-1 px-4 py-2.5 rounded-sm text-sm
              bg-[rgba(255,255,255,0.04)] border border-[rgba(58,154,255,0.15)] text-white placeholder-slate-400/60 focus:outline-none focus:border-[#3A9AFF] transition-colors"
            />
            <button
              className="
              px-8 py-2.5 rounded-sm text-sm font-bold bg-[#F1FF5E] text-[#1C0770] hover:transition-all hover:brightness-110 transition-all"
              onClick={() => setComment("")}
            >
              등록
            </button>
          </div>

          {/* Comment List */}
          <div className="flex flex-col gap-4">
            {sampleComments.map((c) => (
              <div key={c.id} className="mb-3">
                <div className="flex items-center justify-between mb-2">
                  <span className="text-xs font-bold text-white">
                    {c.author}
                  </span>
                  <span className="text-[11px] text-slate-400">{c.time}</span>
                </div>
                <p className="text-sm text-slate-200/80">{c.body}</p>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}

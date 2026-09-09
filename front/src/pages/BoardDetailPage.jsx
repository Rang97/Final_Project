import { useParams, useNavigate } from "react-router-dom";
import { useState } from "react";
import { posts } from "./BoardPage";

const categoryColors = {
  "공략/팁": { bg: "rgba(124,58,237,0.15)", color: "#a78bfa" },
  자유: { bg: "rgba(6,214,160,0.12)", color: "#06d6a0" },
  질문: { bg: "rgba(245,158,11,0.12)", color: "#fbbf24" },
  거래: { bg: "rgba(239,68,68,0.12)", color: "#f87171" },
  이벤트: { bg: "rgba(236,72,153,0.12)", color: "#f472b6" },
};

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
  const [liked, setLiked] = useState(false);
  const [comment, setComment] = useState("");

  const post = posts.find((p) => p.id === Number(id));

  if (!post) {
    return (
      <div
        className="flex items-center justify-center min-h-[60vh]"
        style={{ color: "#4a4a70" }}
      >
        게시글을 찾을 수 없습니다.
      </div>
    );
  }

  const cat = categoryColors[post.category] ?? {
    bg: "rgba(255,255,255,0.07)",
    color: "#a0a0c0",
  };

  return (
    <div className="max-w-3xl mx-auto px-6 md:px-10 py-12">
      {/* Back */}
      <button
        onClick={() => navigate("/board")}
        className="flex items-center gap-2 text-sm mb-8 transition-colors hover:text-white"
        style={{ color: "#4a4a70" }}
      >
        ← 게시판으로
      </button>

      {/* Post card */}
      <div
        className="rounded p-8 mb-6"
        style={{
          background: "#0f0f1a",
          border: "1px solid rgba(255,255,255,0.07)",
        }}
      >
        {/* Meta */}
        <div className="flex items-center gap-3 mb-5 flex-wrap">
          <span
            className="text-xs font-semibold px-2.5 py-1 rounded"
            style={{ background: cat.bg, color: cat.color }}
          >
            {post.category}
          </span>
          <span className="text-xs" style={{ color: "#4a4a70" }}>
            {post.game}
          </span>
        </div>

        {/* Title */}
        <h1
          className="font-bold mb-6 leading-snug"
          style={{
            fontFamily: "'Rajdhani', sans-serif",
            fontSize: "clamp(1.5rem, 3vw, 2rem)",
            color: "#f0f0fa",
          }}
        >
          {post.title}
        </h1>

        {/* Author row */}
        <div
          className="flex items-center gap-3 pb-6 mb-6"
          style={{ borderBottom: "1px solid rgba(255,255,255,0.06)" }}
        >
          <div
            className="w-8 h-8 rounded flex items-center justify-center text-sm font-bold flex-shrink-0"
            style={{
              background: "linear-gradient(135deg, #7c3aed, #06d6a0)",
              color: "#fff",
            }}
          >
            {post.author[0]}
          </div>
          <div>
            <p className="text-sm font-medium" style={{ color: "#c0c0e0" }}>
              {post.author}
            </p>
            <p className="text-xs" style={{ color: "#4a4a70" }}>
              {post.time}
            </p>
          </div>
          <div
            className="ml-auto flex items-center gap-4 text-xs"
            style={{ color: "#4a4a70" }}
          >
            <span>👁 {post.views.toLocaleString()}</span>
            <span>💬 {post.replies}</span>
          </div>
        </div>

        {/* Body */}
        <div
          className="text-sm leading-relaxed whitespace-pre-line"
          style={{ color: "#a0a0c0" }}
        >
          {post.body}
        </div>

        {/* Like button */}
        <div className="mt-8 flex justify-center">
          <button
            onClick={() => setLiked(!liked)}
            className="flex items-center gap-2 px-6 py-2.5 rounded font-semibold text-sm transition-all"
            style={
              liked
                ? {
                    background: "rgba(124,58,237,0.2)",
                    color: "#a78bfa",
                    border: "1px solid rgba(124,58,237,0.4)",
                  }
                : {
                    background: "rgba(255,255,255,0.05)",
                    color: "#6060a0",
                    border: "1px solid rgba(255,255,255,0.08)",
                  }
            }
          >
            👍 {liked ? post.likes + 1 : post.likes}
          </button>
        </div>
      </div>

      {/* Comments section */}
      <div
        className="rounded p-6"
        style={{
          background: "#0f0f1a",
          border: "1px solid rgba(255,255,255,0.07)",
        }}
      >
        <h2
          className="font-bold mb-6 text-lg"
          style={{ fontFamily: "'Rajdhani', sans-serif" }}
        >
          댓글 {post.replies}
        </h2>

        {/* Comment input */}
        <div className="flex gap-3 mb-8">
          <div
            className="w-8 h-8 rounded flex-shrink-0 flex items-center justify-center text-sm font-bold"
            style={{
              background: "linear-gradient(135deg, #7c3aed, #5b21b6)",
              color: "#fff",
            }}
          >
            나
          </div>
          <div className="flex-1 flex gap-2">
            <input
              type="text"
              placeholder="댓글을 입력하세요..."
              value={comment}
              onChange={(e) => setComment(e.target.value)}
              className="flex-1 px-4 py-2.5 rounded text-sm outline-none"
              style={{
                background: "rgba(255,255,255,0.04)",
                border: "1px solid rgba(255,255,255,0.08)",
                color: "#e8e8f0",
              }}
            />
            <button
              className="px-4 py-2.5 rounded text-sm font-semibold flex-shrink-0"
              style={{
                background: "linear-gradient(135deg, #7c3aed, #5b21b6)",
                color: "#fff",
              }}
              onClick={() => setComment("")}
            >
              등록
            </button>
          </div>
        </div>

        {/* Comment list */}
        <div className="flex flex-col gap-5">
          {sampleComments.map((c) => (
            <div key={c.id} className="flex gap-3">
              <div
                className="w-8 h-8 rounded flex-shrink-0 flex items-center justify-center text-xs font-bold"
                style={{ background: "rgba(124,58,237,0.2)", color: "#a78bfa" }}
              >
                {c.author[0]}
              </div>
              <div className="flex-1">
                <div className="flex items-center gap-2 mb-1">
                  <span
                    className="text-sm font-medium"
                    style={{ color: "#c0c0e0" }}
                  >
                    {c.author}
                  </span>
                  <span className="text-xs" style={{ color: "#4a4a70" }}>
                    {c.time}
                  </span>
                </div>
                <p className="text-sm" style={{ color: "#a0a0c0" }}>
                  {c.body}
                </p>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

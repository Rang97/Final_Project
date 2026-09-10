import { FiEye, FiMessageSquare } from "react-icons/fi";
import { useNavigate } from "react-router-dom";

export const posts = [
  {
    id: 1,
    title: "시즌14 정글 티어리스트 총정리 (패치 14.12 기준)",
    author: "JungleMaster",
    time: "2시간 전",
    views: 4820,
    likes: 312,
    replies: 48,
    pinned: true,
    body: "안녕하세요! 오늘은 현 패치 기준 정글 티어리스트를 총정리해 보겠습니다.\n\n**S+ 티어**: 비에고, 브랜드, 아이번\n**S 티어**: 그레이브즈, 헤카림, 리 신\n**A 티어**: 카직스, 렉사이, 니달리\n\n정글은 현재 초반 갱킹 중심의 메타가 이어지고 있습니다. 라인 주도권을 빠르게 가져가는 챔피언들이 강세를 보이고 있으며, 특히 비에고는 초반 클리어 속도와 후반 스케일링 모두 뛰어나 픽률과 승률이 최상위권입니다.\n\n초반 갱킹을 통해 상대 정글러보다 먼저 성장 우위를 점하는 것이 핵심입니다.",
  },
  {
    id: 2,
    title: "칼바람 나락 성능 좋은 챔피언 추천 부탁드립니다",
    author: "Gamer123",
    time: "4시간 전",
    views: 1240,
    likes: 45,
    replies: 12,
    pinned: false,
    body: "요즘 칼바람만 주로 하고 있는데 승률 좋은 챔피언 조합이나 템트리 추천해주세요!",
  },
];

export default function BoardPage() {
  const navigate = useNavigate();

  return (
    <div className="bg-[#06040f] min-h-screen text-white selection:bg-[#F1FF5E] selection:text-[#1C0770]">
      <div className="max-w-4xl mx-auto px-6 md:px-10 py-12">
        {/* Header */}
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

        {/* Post list */}
        <div className="flex flex-col gap-3">
          {posts.map((post, index) => (
            <div
              key={post.id}
              onClick={() => navigate(`/board/${post.id}`)}
              className="group flex items-center justify-between gap-4 px-8 py-6 rounded-sm cursor-pointer transition-all bg-[#261cc1]/15 text-[#a0a0c0] border border-[#0d0b1e] hover:border-[#F1FF5E]/50"
            >
              <span className="font-['Rajdhani'] text-lg font-bold text-[#3A9AFF] group-hover:text-[#F1FF5E] transition-colors w-6 text-center">
                {index + 1}
              </span>

              {/* Content */}
              <div className="flex-1 min-w-0">
                <div className="flex items-center gap-2 mb-1.5">
                  <p className="text-base font-bold truncate text-white group-hover:text-[#F1FF5E] transition-colors">
                    {post.title}
                  </p>
                </div>
                <p className="text-xs font-medium text-slate-300/60">
                  {post.author} · {post.time}
                </p>
              </div>

              {/* Stats */}
              <div className="hidden md:flex items-center gap-4 text-sm text-slate-300/70 flex-shrink-0">
                <span className="flex items-center gap-1">
                  <FiEye /> {post.views.toLocaleString()}
                </span>
                <span className="flex items-center gap-1">
                  <FiMessageSquare /> {post.replies}
                </span>
              </div>
            </div>
          ))}
        </div>

        {/* Pagination */}
        <div className="flex justify-center gap-2 mt-12">
          {[1, 2, 3, 4, 5].map((p) => (
            <button
              key={p}
              className={`w-9 h-9 rounded-sm text-sm font-bold transition-all border ${
                p === 1
                  ? "bg-[rgba(38,28,193,0.3)] text-[#3A9AFF] "
                  : "bg-[#0d0b1e] text-slate-300/60 border-[#261CC1]/50 hover:border-[#3A9AFF] hover:text-white"
              }`}
            >
              {p}
            </button>
          ))}
        </div>
      </div>
    </div>
  );
}

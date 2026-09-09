import { useNavigate } from "react-router-dom";

export const posts = [
  {
    id: 1,
    category: "공략/팁",
    game: "리그 오브 레전드",
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
    category: "공략/팁",
    game: "발로란트",
    title: "레디언트가 알려주는 에임 훈련 루틴 — 하루 30분으로 충분",
    author: "AimGod",
    time: "4시간 전",
    views: 3200,
    likes: 201,
    replies: 35,
    pinned: true,
    body: "에임 향상을 위해 하루 30분 루틴을 소개합니다.\n\n**1. 워밍업 (10분)**: 레인지 봇 — 고정 타깃 헤드샷 x100회\n**2. 트래킹 (10분)**: 움직이는 봇 추적 훈련\n**3. 플릭 (10분)**: 랜덤 방향 플릭 반복\n\n꾸준히 3주만 해도 체감 차이가 납니다. 중요한 건 매일 빠짐없이 하는 것!",
  },
  {
    id: 3,
    category: "자유",
    game: "배틀그라운드",
    title: "닭저녁 처음 먹었는데 기분 설명 불가ㅋㅋㅋ",
    author: "ChickenLover",
    time: "1시간 전",
    views: 980,
    likes: 87,
    replies: 22,
    pinned: false,
    body: "진짜 1000시간 넘게 했는데 드디어 처음으로 닭저녁 먹었습니다ㅋㅋㅋ\n\n근데 진짜 손 떨리는 거 실화예요? 마지막 1:1 상황에서 심장이 멎는 줄 알았어요.\n\n같이 파티 하실 분 언제든 환영합니다 ㅎㅎ",
  },
  {
    id: 4,
    category: "질문",
    game: "오버워치 2",
    title: "탱커 메인인데 실버 탈출 방법 좀 알려주세요ㅠ",
    author: "SilverTank",
    time: "30분 전",
    views: 432,
    likes: 12,
    replies: 14,
    pinned: false,
    body: "라인홀트 메인인데 시즌 내내 실버에 갇혀있습니다ㅠㅠ\n\n포지션 문제인지, 궁 타이밍 문제인지 잘 모르겠어요. 혹시 탱커 메인 고수분들 조언 부탁드려요!\n\n현재 승률은 49%고 평균 데미지 흡수량은 8000 정도입니다.",
  },
  {
    id: 5,
    category: "자유",
    game: "메이플스토리",
    title: "드디어 아케인포스 600 달성했습니다!!",
    author: "MapleVet",
    time: "3시간 전",
    views: 2100,
    likes: 198,
    replies: 61,
    pinned: false,
    body: "2년 걸렸습니다... 진짜 눈물이 나네요.\n\n아케인 리버 6개 섬을 다 완성하고 포스 600을 달성했습니다. 보스방 레이드 다 풀파티로 돌 수 있게 됐어요.\n\n같이 레이드 하실 분 댓글 달아주세요!",
  },
  {
    id: 6,
    category: "거래",
    game: "리그 오브 레전드",
    title: "챌린저 계정 대리 NO — 정상적인 계정 거래 주의사항",
    author: "SafeTrade",
    time: "5시간 전",
    views: 1540,
    likes: 95,
    replies: 19,
    pinned: false,
    body: "요즘 사기 거래가 많아서 주의사항 공유드립니다.\n\n1. 에스크로 없이 선입금 절대 금지\n2. 계정 판매자 랭크 인증 확인\n3. 라이엇 계정 이메일 인증 변경 확인 후 대금 지급\n\n안전하게 거래하세요!",
  },
  {
    id: 7,
    category: "공략/팁",
    game: "발로란트",
    title: "어센던트 승격 직전에 항상 실패하는 분들께",
    author: "MindsetCoach",
    time: "7시간 전",
    views: 2880,
    likes: 241,
    replies: 53,
    pinned: false,
    body: "승격전에서 실패하는 이유 1위는 멘탈 관리 실패입니다.\n\n저도 다이아→어센던트 승격전을 12번 실패했고, 그 원인을 분석해봤어요.\n\n핵심은 '이겨야 한다'는 압박감을 버리고 '내 플레이에 집중'하는 것. 팀원 탓 그만하고 내가 통제할 수 있는 것에만 집중하세요.",
  },
  {
    id: 8,
    category: "이벤트",
    game: "공통",
    title: "GuildHub 여름 토너먼트 참가자 모집 (~7/31)",
    author: "GuildHub",
    time: "1일 전",
    views: 5600,
    likes: 430,
    replies: 88,
    pinned: false,
    body: "GuildHub 공식 여름 토너먼트를 개최합니다!\n\n**참가 종목**: 리그 오브 레전드 5v5, 발로란트 5v5\n**상금**: 1위 50만원, 2위 20만원, 3위 10만원\n**신청 기간**: ~7월 31일\n\n팀 단위 신청 필수. 자세한 규정은 공지사항 확인해주세요.",
  },
  {
    id: 9,
    category: "질문",
    game: "배틀그라운드",
    title: "마우스 감도 세팅 공유해주실 분?",
    author: "NewbieGamer",
    time: "2시간 전",
    views: 670,
    likes: 34,
    replies: 27,
    pinned: false,
    body: "배그 시작한 지 2주 된 뉴비인데요, 마우스 감도 세팅을 어떻게 해야 할지 모르겠어요.\n\n현재 DPI 800에 인게임 감도 0.25 쓰고 있는데 에임이 너무 흔들려요. 고수분들 세팅 공유 부탁드립니다!",
  },
  {
    id: 10,
    category: "자유",
    game: "오버워치 2",
    title: "시즌 컷 때문에 심장이 멎는 줄ㅋㅋ 결국 다이아 유지",
    author: "HeartAttack",
    time: "4시간 전",
    views: 1200,
    likes: 110,
    replies: 31,
    pinned: false,
    body: "시즌 마지막 날 다이아 경계선에서 3연패 하다가 극적으로 2연승해서 유지했습니다 ㅋㅋㅋ\n\n다음 시즌엔 마스터 도전할 건데 같이 파티 하실 분 구합니다. 솔큐 트라우마 생겨버렸어요ㅠㅠ",
  },
];

const categoryColors = {
  "공략/팁": { bg: "rgba(124,58,237,0.15)", color: "#a78bfa" },
  자유: { bg: "rgba(6,214,160,0.12)", color: "#06d6a0" },
  질문: { bg: "rgba(245,158,11,0.12)", color: "#fbbf24" },
  거래: { bg: "rgba(239,68,68,0.12)", color: "#f87171" },
  이벤트: { bg: "rgba(236,72,153,0.12)", color: "#f472b6" },
};

export default function BoardPage() {
  const navigate = useNavigate();

  return (
    <div className="max-w-4xl mx-auto px-6 md:px-10 py-12">
      {/* Header */}
      <div className="flex items-end justify-between mb-10">
        <div>
          <p
            className="text-xs uppercase tracking-widest mb-2"
            style={{ color: "#7c3aed" }}
          >
            커뮤니티
          </p>
          <h1
            className="text-4xl font-bold"
            style={{ fontFamily: "'Rajdhani', sans-serif" }}
          >
            게시판
          </h1>
        </div>
        <button
          className="px-5 py-2 rounded text-sm font-semibold"
          style={{
            background: "linear-gradient(135deg, #7c3aed, #5b21b6)",
            color: "#fff",
          }}
        >
          + 글쓰기
        </button>
      </div>

      {/* Post list */}
      <div className="flex flex-col gap-2">
        {posts.map((post) => {
          const cat = categoryColors[post.category] ?? {
            bg: "rgba(255,255,255,0.07)",
            color: "#a0a0c0",
          };
          return (
            <div
              key={post.id}
              className="group flex items-start gap-4 px-5 py-4 rounded cursor-pointer transition-all"
              style={{
                background: post.pinned ? "rgba(124,58,237,0.06)" : "#0f0f1a",
                border: post.pinned
                  ? "1px solid rgba(124,58,237,0.18)"
                  : "1px solid rgba(255,255,255,0.05)",
              }}
              onClick={() => navigate(`/board/${post.id}`)}
            >
              {/* Category badge */}
              <span
                className="text-xs font-semibold px-2 py-0.5 rounded flex-shrink-0 mt-0.5"
                style={{ background: cat.bg, color: cat.color }}
              >
                {post.category}
              </span>

              {/* Content */}
              <div className="flex-1 min-w-0">
                <p
                  className="text-sm font-semibold truncate mb-1 group-hover:text-[#a78bfa] transition-colors"
                  style={{ color: "#e8e8f0" }}
                >
                  {post.pinned && (
                    <span
                      className="mr-2 text-xs font-bold"
                      style={{ color: "#06d6a0" }}
                    >
                      [공지]
                    </span>
                  )}
                  {post.title}
                </p>
                <p className="text-xs" style={{ color: "#4a4a70" }}>
                  {post.game} · {post.author} · {post.time}
                </p>
              </div>

              {/* Stats */}
              <div
                className="hidden md:flex items-center gap-4 text-xs flex-shrink-0"
                style={{ color: "#4a4a70" }}
              >
                <span>👁 {post.views.toLocaleString()}</span>
                <span>👍 {post.likes}</span>
                <span>💬 {post.replies}</span>
              </div>
            </div>
          );
        })}
      </div>

      {/* Pagination */}
      <div className="flex justify-center gap-2 mt-10">
        {[1, 2, 3, 4, 5].map((p) => (
          <button
            key={p}
            className="w-8 h-8 rounded text-sm transition-all"
            style={
              p === 1
                ? {
                    background: "rgba(124,58,237,0.2)",
                    color: "#a78bfa",
                    border: "1px solid rgba(124,58,237,0.4)",
                  }
                : {
                    background: "#0f0f1a",
                    color: "#4a4a70",
                    border: "1px solid rgba(255,255,255,0.06)",
                  }
            }
          >
            {p}
          </button>
        ))}
      </div>
    </div>
  );
}

import { useState } from "react";
import { useAuthStore } from "../store/authStore";
import { useMyGames } from "../features/mypage/hooks/useMyGames";
import GameSection from "../features/mypage/components/GameSection";
import FortuneSection from "../features/mypage/components/FortuneSection";

const tabs = ["선호 게임", "오행", "내 파티", "작성 글", "설정"];

export default function MyPage() {
  const [activeTab, setActiveTab] = useState("선호 게임");
  const user = useAuthStore((state) => state.user);

  const { games } = useMyGames();

  return (
    <div className="bg-[#06040f] min-h-screen text-white selection:bg-[#F1FF5E] selection:text-[#1C0770]">
      <div className="max-w-4xl mx-auto px-6 md:px-10 py-10">
        <div className="mb-8 border-b border-[#3A9AFF]/10 pb-6">
          <p className="text-xs uppercase tracking-widest mb-1 font-bold text-[#3A9AFF]">
            마이페이지
          </p>
          <h1 className="text-3xl md:text-4xl font-extrabold font-['Rajdhani'] text-[#f0f0fa]">
            {user?.nickname ?? "Hello"}
          </h1>
        </div>

        <FortuneSection games={games} />

        <div className="w-full flex gap-2 mb-8 border-b border-[#3A9AFF]/15 pb-3 overflow-x-auto">
          {tabs.map((tab) => (
            <button
              key={tab}
              onClick={() => setActiveTab(tab)}
              className={`px-5 py-2 rounded text-sm font-bold transition-all cursor-pointer whitespace-nowrap ${
                activeTab === tab
                  ? "bg-[#261CC1]/40 text-[#F1FF5E] border border-[#F1FF5E]/30"
                  : "text-[#f0f0fa]/50 hover:text-white"
              }`}
            >
              {tab}
            </button>
          ))}
        </div>

        {activeTab === "선호 게임" && <GameSection />}
        {activeTab === "오행" && <SajuSection />}

        {activeTab === "내 파티" && (
          <div className="bg-[#0d0b1e] border border-[#3A9AFF]/10 rounded p-6 md:p-8 shadow-xl text-center">
            <p className="text-sm text-[#f0f0fa]/40">
              내 파티 목록 기능은 준비 중입니다. (백엔드 API 추가 필요)
            </p>
          </div>
        )}

        {activeTab === "작성 글" && (
          <div className="bg-[#0d0b1e] border border-[#3A9AFF]/10 rounded p-6 md:p-8 shadow-xl text-center">
            <p className="text-sm text-[#f0f0fa]/40">
              작성 글 목록 기능은 준비 중입니다. (백엔드 API 추가 필요)
            </p>
          </div>
        )}

        {activeTab === "설정" && (
          <div className="bg-[#0d0b1e] border border-[#3A9AFF]/10 rounded p-6 md:p-8 shadow-xl text-center">
            <p className="text-sm text-[#f0f0fa]/40">
              계정 정보 수정 기능은 준비 중입니다. (백엔드 API 추가 필요)
            </p>
          </div>
        )}
      </div>
    </div>
  );
}

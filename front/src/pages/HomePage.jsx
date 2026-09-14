import { useState, useRef, useEffect } from "react";
import { FiArrowLeft, FiArrowRight } from "react-icons/fi";
import { useNavigate } from "react-router-dom";

const gameCards = [
  {
    id: 1,
    title: "리그 오브 레전드",
    sub: "MOBA · 5v5",
    tag: "인기",
    img: "https://images.unsplash.com/photo-1542751371-adc38448a05e?w=500&h=700&fit=crop&auto=format",
  },
  {
    id: 2,
    title: "발로란트",
    sub: "FPS · 5v5",
    tag: "HOT",
    img: "https://images.unsplash.com/photo-1511512578047-dfb367046420?w=500&h=700&fit=crop&auto=format",
  },
  {
    id: 3,
    title: "배틀그라운드",
    sub: "Battle Royale",
    tag: "추천",
    img: "https://images.unsplash.com/photo-1550745165-9bc0b252726f?w=500&h=700&fit=crop&auto=format",
  },
  {
    id: 4,
    title: "오버워치 2",
    sub: "팀 슈터",
    tag: "NEW",
    img: "https://images.unsplash.com/photo-1493711662062-fa541adb3fc8?w=500&h=700&fit=crop&auto=format",
  },
  {
    id: 5,
    title: "메이플스토리",
    sub: "MMORPG",
    img: "https://images.unsplash.com/photo-1519326844852-704caea5679e?w=500&h=700&fit=crop&auto=format",
  },
  {
    id: 6,
    title: "로스트아크",
    sub: "MMORPG",
    img: "https://images.unsplash.com/photo-1538481199705-c710c4e965fc?w=500&h=700&fit=crop&auto=format",
  },
  {
    id: 7,
    title: "FC 온라인",
    sub: "스포츠 · 축구",
    img: "https://images.unsplash.com/photo-1508098682722-e99c43a406b2?w=500&h=700&fit=crop&auto=format",
  },
  {
    id: 8,
    title: "Apex 레전드",
    sub: "Battle Royale",
    img: "https://images.unsplash.com/photo-1560253023-3ec5d502959f?w=500&h=700&fit=crop&auto=format",
  },
  {
    id: 9,
    title: "마인크래프트",
    sub: "샌드박스",
    img: "https://images.unsplash.com/photo-1607604276583-eef5d076aa5f?w=500&h=700&fit=crop&auto=format",
  },
  {
    id: 10,
    title: "원신",
    sub: "오픈월드 RPG",
    img: "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=500&h=700&fit=crop&auto=format",
  },
  {
    id: 11,
    title: "사이퍼즈",
    sub: "AOS · 액션",
    img: "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=500&h=700&fit=crop&auto=format",
  },
  {
    id: 12,
    title: "던전앤파이터",
    sub: "벨트스크롤 액션",
    img: "https://images.unsplash.com/photo-1542751110-97427bbecf20?w=500&h=700&fit=crop&auto=format",
  },
  {
    id: 13,
    title: "스타크래프트",
    sub: "RTS",
    img: "https://images.unsplash.com/photo-1509198397868-475647b2a1e5?w=500&h=700&fit=crop&auto=format",
  },
  {
    id: 14,
    title: "몬스터헌터",
    sub: "액션 RPG",
    img: "https://images.unsplash.com/photo-1563089145-599997674d42?w=500&h=700&fit=crop&auto=format",
  },
  {
    id: 15,
    title: "디아블로 IV",
    sub: "핵앤슬래시",
    img: "https://images.unsplash.com/photo-1579373903781-fd5c0c30c4cd?w=500&h=700&fit=crop&auto=format",
  },
  {
    id: 16,
    title: "카운터 스트라이크 2",
    sub: "FPS",
    img: "https://images.unsplash.com/photo-1552824728-06768556728e?w=500&h=700&fit=crop&auto=format",
  },
  {
    id: 17,
    title: "서든어택",
    sub: "FPS",
    img: "https://images.unsplash.com/photo-1511512578047-dfb367046420?w=500&h=700&fit=crop&auto=format",
  },
  {
    id: 18,
    title: "폴가이즈",
    sub: "파티 · 파티게임",
    img: "https://images.unsplash.com/photo-1551103782-8ab07afd45c1?w=500&h=700&fit=crop&auto=format",
  },
  {
    id: 19,
    title: "어몽어스",
    sub: "마피아 · 추리",
    img: "https://images.unsplash.com/photo-1612287230202-1ff1d85d1bdf?w=500&h=700&fit=crop&auto=format",
  },
  {
    id: 20,
    title: "붕괴: 스타레일",
    sub: "턴제 RPG",
    img: "https://images.unsplash.com/photo-1534423861386-85a16f5d13fd?w=500&h=700&fit=crop&auto=format",
  },
];

export default function HomePage() {
  const navigate = useNavigate();
  const sliderRef = useRef(null);

  const [isDragging, setIsDragging] = useState(false);
  const [startX, setStartX] = useState(0);
  const [scrollLeft, setScrollLeft] = useState(0);
  const [hasDragged, setHasDragged] = useState(false);
  const [scrollPos, setScrollPos] = useState(0);

  const handleScroll = () => {
    if (sliderRef.current) {
      setScrollPos(sliderRef.current.scrollLeft);
    }
  };

  // 마우스 휠 동작 시 독립 스크롤바 없이 트랙을 좌우로 스크롤하도록 함
  const handleWheel = (e) => {
    if (sliderRef.current) {
      sliderRef.current.scrollLeft += e.deltaY;
    }
  };

  const handleMouseDown = (e) => {
    if (!sliderRef.current) return;
    setIsDragging(true);
    setHasDragged(false);
    setStartX(e.pageX - sliderRef.current.offsetLeft);
    setScrollLeft(sliderRef.current.scrollLeft);
  };

  const handleMouseMove = (e) => {
    if (!isDragging || !sliderRef.current) return;
    e.preventDefault();
    const x = e.pageX - sliderRef.current.offsetLeft;
    const walk = (x - startX) * 1.5;

    if (Math.abs(walk) > 5) {
      setHasDragged(true);
    }
    sliderRef.current.scrollLeft = scrollLeft - walk;
  };

  const handleMouseUpOrLeave = () => {
    setIsDragging(false);
  };

  const handleCardClick = () => {
    if (hasDragged) return;
    navigate("/party");
  };

  useEffect(() => {
    const centerSlider = () => {
      if (sliderRef.current) {
        const container = sliderRef.current;
        const centerPos = (container.scrollWidth - container.clientWidth) / 2;
        container.scrollLeft = centerPos;
        setScrollPos(centerPos);
      }
    };

    centerSlider();
    window.addEventListener("resize", centerSlider);
    return () => window.removeEventListener("resize", centerSlider);
  }, []);

  return (
    <div className="bg-[#06040f] min-h-screen text-white overflow-x-hidden">
      <section className="relative min-h-screen flex flex-col items-center justify-between pt-10 pb-10 overflow-hidden">
        {/* Grid texture */}
        <div
          className="absolute inset-0 pointer-events-none"
          style={{
            backgroundImage:
              "linear-gradient(rgba(58,154,255,0.04) 1px, transparent 1px), linear-gradient(90deg, rgba(58,154,255,0.04) 1px, transparent 1px)",
            backgroundSize: "52px 52px",
          }}
        />

        {/* Blue radial glow */}
        <div
          className="absolute bottom-0 left-1/2 -translate-x-1/2 pointer-events-none"
          style={{
            width: 800,
            height: 500,
            background:
              "radial-gradient(ellipse, rgba(38,28,193,0.28) 0%, rgba(58,154,255,0.08) 50%, transparent 70%)",
            filter: "blur(40px)",
          }}
        />

        {/* Floating deco */}
        <img
          src="https://images.unsplash.com/photo-1550745165-9bc0b252726f?w=80&h=80&fit=crop&auto=format"
          alt=""
          className="absolute hidden md:block rounded opacity-60 pointer-events-none"
          style={{
            width: 68,
            height: 68,
            top: "18%",
            left: "6%",
            transform: "rotate(-12deg)",
            filter: "drop-shadow(0 8px 24px rgba(58,154,255,0.4))",
          }}
        />
        <img
          src="https://images.unsplash.com/photo-1493711662062-fa541adb3fc8?w=80&h=80&fit=crop&auto=format"
          alt=""
          className="absolute hidden md:block rounded opacity-50 pointer-events-none"
          style={{
            width: 58,
            height: 58,
            top: "28%",
            right: "7%",
            transform: "rotate(10deg)",
            filter: "drop-shadow(0 8px 24px rgba(241,255,94,0.3))",
          }}
        />

        {/* Hero Header */}
        <div className="flex flex-col items-center z-10 max-w-4xl px-4 text-center mt-2">
          {/* Badge */}
          <div className="mt-10 mb-6">
            <span
              className="inline-flex items-center gap-2 text-xs font-semibold tracking-widest uppercase px-4 py-1.5 rounded"
              style={{
                background: "rgba(58,154,255,0.1)",
                color: "#3A9AFF",
                border: "1px solid rgba(58,154,255,0.25)",
              }}
            >
              <span
                className="w-1.5 h-1.5 rounded-full"
                style={{ background: "#3A9AFF", boxShadow: "0 0 6px #3A9AFF" }}
              />
              게이머를 위한 커뮤니티
            </span>
          </div>

          <h1 className="font-['Rajdhani'] font-extrabold text-5xl sm:text-6xl md:text-7xl tracking-tight leading-none mb-5">
            함께 플레이하고 <br />
            <span className="bg-gradient-to-r from-[#3A9AFF] via-white to-[#F1FF5E] bg-clip-text text-transparent drop-shadow-[0_0_35px_rgba(58,154,255,0.4)]">
              함께 승리하세요
            </span>
          </h1>

          <p className="max-w-md text-slate-300/80 text-sm md:text-base leading-relaxed">
            당신의 사주와 찰떡인 파티를 찾아보세요
          </p>
        </div>

        {/* Card Slider Section */}
        <div className="w-full relative z-10 my-auto m0">
          {/* Side Fading Masks */}
          <div className="absolute left-0 top-0 bottom-0 w-24 md:w-48 z-40 pointer-events-none" />
          <div className="absolute right-0 top-0 bottom-0 w-24 md:w-48 z-40 pointer-events-none" />

          <div
            ref={sliderRef}
            onScroll={handleScroll}
            onMouseDown={handleMouseDown}
            onMouseMove={handleMouseMove}
            onMouseUp={handleMouseUpOrLeave}
            onMouseLeave={handleMouseUpOrLeave}
            style={{
              paddingLeft: "calc(50vw - 150px)",
              paddingRight: "calc(50vw - 150px)",
            }}
            className={`flex items-center py-16 sm:pt-18 sm:pb-50 overflow-hidden select-none ${
              isDragging ? "cursor-grabbing" : "cursor-grab"
            }`}
          >
            {gameCards.map((card, idx) => {
              const isMobile =
                typeof window !== "undefined" && window.innerWidth < 640;
              const cardWidth = isMobile ? 240 : 300;

              const overlap = isMobile ? 48 : 80;
              const effectiveWidth = cardWidth - overlap;

              const cardOffset = idx * effectiveWidth;
              const containerWidth = sliderRef.current
                ? sliderRef.current.clientWidth
                : 0;
              const currentCenter = scrollPos + containerWidth / 2;

              const paddingOffset = containerWidth / 2 - cardWidth / 2;
              const cardCenter = cardOffset + paddingOffset + cardWidth / 2;

              const distanceFromCenter = cardCenter - currentCenter;
              const absDistance = Math.abs(distanceFromCenter);

              // 1. 회전각 연산
              const rotateZ = Math.max(
                -20,
                Math.min(20, distanceFromCenter / 35),
              );

              // 2. Y축 낙하 곡선 연산
              const translateY = Math.pow(absDistance / 60, 1.8) * 3;

              // 3. Z-Index 연산
              const zIndex = Math.max(1, 100 - Math.round(absDistance / 10));

              return (
                <div
                  key={card.id}
                  onClick={handleCardClick}
                  style={{
                    transform: `translateY(${translateY}px) rotate(${rotateZ}deg)`,
                    transformOrigin: "bottom center",
                    zIndex: zIndex,
                  }}
                  className="-mx-6 sm:-mx-10 relative flex-shrink-0 w-[240px] sm:w-[300px] h-[380px] sm:h-[490px] rounded-3xl overflow-hidden border border-[#261CC1]/60 bg-[#06040f] shadow-[0_20px_50px_rgba(0,0,0,0.8)] transition-transform duration-75 ease-out hover:scale-105 hover:border-[#F1FF5E] hover:shadow-[0_25px_60px_rgba(241,255,94,0.3)] group"
                >
                  {/* Image */}
                  <img
                    src={card.img}
                    alt={card.title}
                    className="absolute inset-0 w-full h-full object-cover transition-transform duration-500 group-hover:scale-110 pointer-events-none"
                  />

                  {/* Dark Overlay */}
                  <div className="absolute inset-0 bg-gradient-to-t from-[#261CC1] via-[#3A9AFF]/40 to-transparent pointer-events-none" />

                  {/* Content */}
                  <div className="absolute bottom-0 left-0 right-0 p-6 text-left pointer-events-none">
                    <p className="font-['Rajdhani'] font-bold text-2xl sm:text-3xl text-white">
                      {card.title}
                    </p>
                    <p className="text-xs sm:text-sm font-medium text-slate-300/80 mt-1">
                      {card.sub}
                    </p>
                  </div>

                  {/* Tag Badge */}
                  {card.tag && (
                    <div className="absolute top-5 right-5 flex items-center gap-1.5 px-3 py-1 rounded-full text-xs font-bold backdrop-blur-md text-[#F1FF5E] border border-[#F1FF5E]/40 shadow-lg pointer-events-none">
                      <span className="w-1.5 h-1.5 rounded-full bg-[#F1FF5E] animate-pulse" />
                      {card.tag}
                    </div>
                  )}
                </div>
              );
            })}
          </div>

          {/* Bottom Guide */}
          <div className="absolute left-1/2 -translate-x-1/2 bottom-23 gap-2 text-xs font-semibold text-slate-400/80 z-10">
            <span>DRAG TO EXPLORE</span>
          </div>
        </div>
      </section>
    </div>
  );
}

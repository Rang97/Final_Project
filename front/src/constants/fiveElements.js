// 서버-프론트 오행 데이터 주고받을 때 쓰는 매핑 테이블

// 오행 한글 라벨
export const ELEMENT_LABELS = {
  WOOD: "목",
  FIRE: "화",
  EARTH: "토",
  METAL: "금",
  WATER: "수",
};

// 오행별 표시 색상
export const ELEMENT_COLORS = {
  WOOD: "#06d6a0",
  FIRE: "#ff4d4d",
  EARTH: "#f1ff5e",
  METAL: "#c0c0e0",
  WATER: "#3a9aff",
};

// GroupElementSummary 응답 필드명 매핑
export const ELEMENT_TOTAL_KEYS = {
  WOOD: "totalWood",
  FIRE: "totalFire",
  EARTH: "totalEarth",
  METAL: "totalMetal",
  WATER: "totalWater",
};

// 사주 동물별 아이콘 (닉네임 뒤쪽 동물 이름으로 매칭)
export const ANIMAL_ICONS = {
  쥐: "🐭",
  소: "🐮",
  호랑이: "🐯",
  토끼: "🐰",
  용: "🐲",
  뱀: "🐍",
  말: "🐴",
  양: "🐑",
  원숭이: "🐵",
  닭: "🐔",
  개: "🐶",
  돼지: "🐷",
};

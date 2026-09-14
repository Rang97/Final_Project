// 서버-프론트 오행 데이터 주고받을 때 쓷는 매핑 테이블

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

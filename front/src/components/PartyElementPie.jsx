import { ELEMENT_COLORS, ELEMENT_TOTAL_KEYS } from "../constants/fiveElements";

// 오행 비율 도넛 차트

// 도넛 그래프 크기/두께 설정
const SIZE = 100;
const CENTER = SIZE / 2;
const RADIUS = 40;
const STROKE = 14;
const HOVER_STROKE = 18;
const GAP = 6; // 세그먼트 사이 간격
const CIRCUMFERENCE = 2 * Math.PI * RADIUS;

export default function PartyElementPie({
  chemistry,
  hoveredElement,
  onHover,
}) {
  // 오행별 수치 배열로 변환
  const totals = Object.keys(ELEMENT_TOTAL_KEYS).map((key) => ({
    key,
    value: chemistry[ELEMENT_TOTAL_KEYS[key]] ?? 0,
  }));
  const sum = totals.reduce((acc, cur) => acc + cur.value, 0); // 전체 합계

  let offset = 0; // 세그먼트 누적 위치

  return (
    <svg viewBox={`0 0 ${SIZE} ${SIZE}`} className="w-24 h-24 mx-auto">
      {/* 12시 방향부터 시작하도록 회전 */}
      <g transform={`rotate(-90 ${CENTER} ${CENTER})`}>
        {sum === 0 ? (
          // 데이터 없을 때 빈 회색 링
          <circle
            cx={CENTER}
            cy={CENTER}
            r={RADIUS}
            fill="none"
            stroke="rgba(255,255,255,0.1)"
            strokeWidth={STROKE}
          />
        ) : (
          // 오행별 도넛 세그먼트
          totals.map(({ key, value }) => {
            const ratio = value / sum;
            const arcLength = CIRCUMFERENCE * ratio;
            const dashLength = Math.max(arcLength - GAP, 0); // 간격만큼 짧게 그림
            const circle = (
              <circle
                key={key}
                cx={CENTER}
                cy={CENTER}
                r={RADIUS}
                fill="none"
                stroke={ELEMENT_COLORS[key]}
                strokeWidth={hoveredElement === key ? HOVER_STROKE : STROKE} // hover 시 굵게
                strokeLinecap="round"
                strokeDasharray={`${dashLength} ${CIRCUMFERENCE - dashLength}`}
                strokeDashoffset={-offset} // 앞 세그먼트만큼 밀기
                onMouseEnter={() => onHover(key)}
                onMouseLeave={() => onHover(null)}
                style={{ transition: "stroke-width 150ms", cursor: "pointer" }}
              />
            );
            offset += arcLength; // 다음 세그먼트 시작 위치 누적
            return circle;
          })
        )}
      </g>
    </svg>
  );
}

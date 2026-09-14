import { useEffect, useRef, useState } from "react";
import { Client } from "@stomp/stompjs";
import { api } from "../api/client";
import PartyElementPie from "./PartyElementPie";
import {
  ELEMENT_COLORS,
  ELEMENT_LABELS,
  ELEMENT_TOTAL_KEYS,
} from "../constants/fiveElements";

export default function PartyChatModal({ party, onClose }) {
  const [messages, setMessages] = useState([]); // 메시지 배열
  const [input, setInput] = useState(""); // 입력창 값
  const [myAnimalName, setMyAnimalName] = useState(null); // 사주 닉
  const stompClientRef = useRef(null);
  const [showDetail, setShowDetail] = useState(false); // 오행 패널
  const [chemistry, setChemistry] = useState(null); // 오행 데이터
  const [visible, setVisible] = useState(false); // 모달 애니메이션
  const [hoveredElement, setHoveredElement] = useState(null); // 오행 중 마우스 올린 항목
  const [error, setError] = useState(null);

  // 내 닉네임 조회
  useEffect(() => {
    api
      .get("/mypage/saju")
      .then((res) => setMyAnimalName(res.data.data.saju.sajuAnimalName))
      .catch((err) => console.error(err));
  }, []);

  // STOMP 연결
  useEffect(() => {
    const client = new Client({
      brokerURL: "ws://localhost:8080/ws",
      connectHeaders: {
        Authorization: "Bearer " + localStorage.getItem("accessToken"),
      },
      onConnect: () => {
        client.subscribe("/sub/party/" + party.partyId, (message) => {
          const data = JSON.parse(message.body);
          setMessages((prev) => [...prev, data]);
        });
      },
      onStompError: (frame) => {
        console.error(frame);
        setError(frame.headers["message"] ?? "채팅 연결에 실패했습니다.")
      },
      onWebSocketError: () => {
        setError("채팅 서버에 연결할 수 없습니다.")
      }
    });

    client.activate();
    stompClientRef.current = client;
    

    return () => {
      client.deactivate();
    };
  }, [party.partyId]);

  // 
  useEffect(() => {
    const id = requestAnimationFrame(() => setVisible(true));
    return () => cancelAnimationFrame(id);
  }, []);

  // 상세 패널
  const handleToggleDetail = (e) => {
    e.stopPropagation();
    // 패널 열 때만 API 호출
    if (!showDetail && !chemistry) {
      api
      // 파티원 전체 오행 합계 조회
        .get(`/party/${party.partyId}/chemistry`)
        .then((res) => setChemistry(res.data))
        .catch((err) => console.error(err));
    }
    setShowDetail((prev) => !prev);
  };

  const handleClose = () => {
    setVisible(false);
    setTimeout(onClose, 300);
  };

  // 전송
  const handleSend = (e) => {
    e.preventDefault();
    // 빈 메시지 전송 방지
    if (!input.trim()) return;
    // 서버로 메시지 발행
    stompClientRef.current.publish({
      destination: "/pub/party/" + party.partyId + "/chat",
      body: JSON.stringify({ content: input.trim() }),
    });
    // 전송 후 입력창 비움
    setInput("");
  };

  return (
    <div
      className="fixed inset-0 z-50"
      style={{ background: "transparent" }}
      onClick={handleClose}
    >
      <div
        className="absolute bottom-6 right-6 flex items-stretch transition-all duration-300"
        style={{
          transform: visible ? "translateY(0)" : "translateY(24px)",
          opacity: visible ? 0.9 : 0,
          border: "1px solid rgba(255,255,255,0.1)",
        }}
        onClick={(e) => e.stopPropagation()}
      >
        {/* 오행 상세 패널 — 카드 밖, 왼쪽에 붙음 */}
        <div
          className="overflow-hidden transition-all duration-300 rounded-xl"
          style={{
            width: showDetail ? "260px" : "0px",
            background: "rgba(10,10,20,0.95)",
            border: showDetail ? "1px solid rgba(255,255,255,0.1)" : "none",
            borderRight: "none",
          }}
        >
          <div className="w-65 shrink-0 h-full flex flex-col px-5 py-4 gap-4">
            {!chemistry ? (
              <p className="text-xs" style={{ color: "rgba(255,255,255,0.5)" }}>
                불러오는 중...
              </p>
            ) : (
              <>
                <p
                  className="text-xs font-semibold text-center"
                  style={{ color: "#e8e8f0" }}
                >
                  오행 현황
                </p>
                <PartyElementPie
                  chemistry={chemistry}
                  hoveredElement={hoveredElement}
                  onHover={setHoveredElement}
                />
                <p
                  className="text-[13px] text-center"
                  style={{ color: "#e8e8f0" }}
                >
                  {/* 많은 오행, 적은 오행 다른 문구 표시 */}
                  {chemistry.maxElements?.[0] === chemistry.minElements?.[0]
                    ? "오행이 고르게 분포돼 있어요"
                    : `${ELEMENT_LABELS[chemistry.maxElements?.[0]]} 기운이 강하고, ${ELEMENT_LABELS[chemistry.minElements?.[0]]} 기운이 부족해요`}
                </p>

                <div className="flex gap-2">
                  {/* 각 오행별 합계 숫자 표시 */}
                  {Object.keys(ELEMENT_LABELS).map((key) => {
                    const total = chemistry[ELEMENT_TOTAL_KEYS[key]];
                    const isLacking = chemistry.minElements?.includes(key);
                    const isHovered = hoveredElement === key;
                    const activeColor = isHovered
                      ? ELEMENT_COLORS[key]
                      : isLacking
                        ? ELEMENT_COLORS[key]
                        : "rgba(255,255,255,0.6)";
                    return (
                      <div
                        key={key}
                        className="flex-1 rounded-xl border flex flex-col items-center gap-1 px-2 py-3 transition-colors"
                        style={{
                          borderColor:
                            isHovered || isLacking
                              ? activeColor
                              : "rgba(255,255,255,0.2)",
                          color: activeColor,
                        }}
                        onMouseEnter={() => setHoveredElement(key)}
                        onMouseLeave={() => setHoveredElement(null)}
                      >
                        <span className="text-xs font-semibold">
                          {ELEMENT_LABELS[key]}
                        </span>
                        <span className="text-xs">{total}</span>
                      </div>
                    );
                  })}
                </div>
                <p
                  className="text-[10px] text-center"
                  style={{ color: "#e8e8f0" }}
                >
                  현재 {party.nowMemberCount}명 기준
                </p>
              </>
            )}
          </div>
        </div>

        {/* 채팅 카드 */}
        <div
          className="w-full max-w-md rounded flex flex-col overflow-hidden"
          style={{
            background: "rgba(255, 255, 255, 0.2)",
            border: "1px solid rgba(255,255,255,0.1)",
            height: "60vh",
            width: "45vh",
            backdropFilter: "blur(12px)",
          }}
          onClick={() => setShowDetail(false)}
        >
          <div
            className="flex items-center justify-between px-5 py-4"
            style={{ borderBottom: "1px solid rgba(255,255,255,0.07)" }}
          >
            {/* 타이틀 */}
            <p
              className="text-sm font-semibold truncate"
              style={{ color: "#e8e8f0" }}
            >
              {party.title}
            </p>

            {/* 상세 버튼 */}
            <button
              onClick={handleToggleDetail}
              className="flex justify-items-start text-xs px-3 py-1 rounded-full border transition-colors"
              style={{
                borderColor: showDetail ? "#3A9AFF" : "rgba(255,255,255,0.15)",
                color: showDetail ? "#3A9AFF" : "rgba(255,255,255,0.6)",
              }}
              onMouseEnter={(e) => {
                e.currentTarget.style.borderColor = "#F1FF5E";
                e.currentTarget.style.color = "#F1FF5E";
              }}
              onMouseLeave={(e) => {
                if (!showDetail) {
                  e.currentTarget.style.borderColor = "rgba(255,255,255,0.15)";
                  e.currentTarget.style.color = "rgba(255,255,255,0.6)";
                }
              }}
            >
              상세
            </button>

            {/* 인원수 사각 */}
            <div className="flex items-center gap-3">
              <div className="flex items-center gap-1 justify-end">
                {Array.from({ length: party.maxMemberCount }).map((_, i) => (
                  <div
                    key={i}
                    className="w-2 h-2"
                    style={{
                      background:
                        i < party.nowMemberCount
                          ? "#3A9AFF"
                          : "rgba(255,255,255,0.2)",
                    }}
                  />
                ))}
              </div>

              <button
                onClick={onClose}
                className="text-sm px-2"
                style={{ color: "#F1FF5E" }}
              >
                ✕
              </button>
            </div>
          </div>

          <div className="flex-1 min-h-0 overflow-y-auto px-5 py-4 flex flex-col gap-4">
            {messages.map((msg, i) => {
              // 내가 보낸 메시지인지 판별
              const isMine = msg.sender === myAnimalName;
              // 연속 메시지는 발신자/시간 표시 생략 (앞뒤 참조)
              const prev = messages[i - 1];
              const next = messages[i + 1];
              const formatTime = (t) =>
                new Date(t).toLocaleTimeString("ko-KR", {
                  hour: "2-digit",
                  minute: "2-digit",
                });

              // 메시지 발신자 다르면 이름 표시
              const showSender = !prev || prev.sender !== msg.sender;
              // 메시지 발신자 다르면 시간 표시
              const showTimestamp =
                !next ||
                next.sender !== msg.sender ||
                formatTime(next.timestamp) !== formatTime(msg.timestamp);

              return (
                <div
                  key={i}
                  className={`chat-message-row ${isMine ? "chat-row-personal" : ""}`}
                >
                  {!isMine && (
                    <div className="chat-avatar">{msg.sender?.[0]}</div>
                  )}
                  <div className="min-w-0">
                    {showSender && <p className="chat-sender">{msg.sender}</p>}
                    <div
                      className={`chat-bubble ${isMine ? "chat-bubble-personal" : ""}`}
                    >
                      {msg.content}
                    </div>
                    {showTimestamp && (
                      <p className="chat-timestamp">
                        {formatTime(msg.timestamp)}
                      </p>
                    )}
                  </div>
                </div>
              );
            })}
          </div>

          <form
            onSubmit={handleSend}
            className="flex gap-2 px-4 py-3"
            style={{ borderTop: "1px solid rgba(255,255,255,0.07)" }}
          >
            <input
              value={input}
              onChange={(e) => setInput(e.target.value)}
              placeholder="메시지를 입력하세요"
              className="flex-1 px-4 py-2 rounded text-sm outline-none"
              style={{
                background: "rgba(255,255,255,0.04)",
                border: "1px solid rgba(255,255,255,0.1)",
                color: "#e8e8f0",
              }}
            />
            <button
              type="submit"
              className="brand-gradient-btn px-4 py-2 rounded text-sm font-semibold text-white"
            >
              전송
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}

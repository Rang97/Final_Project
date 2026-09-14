import { useState } from "react";
import { BiSolidErrorAlt, BiX } from "react-icons/bi";

export default function ErrorToast({ message, onClose }) {
  const [closing, setClosing] = useState(false);

  if (!message) return null;

  const handleClose = () => {
    setClosing(true);
    setTimeout(() => {
      onClose();
      setClosing(false);
    }, 300);
  };

  return (
    <div
      className="flex items-start gap-3 px-4 py-3"
      style={{
        position: "fixed",
        left: "1.5rem",
        bottom: "1.5rem",
        zIndex: 1000,
        maxWidth: "320px",
        background: "rgba(255,77,79,0.08)",
        borderLeft: "3px solid #ff4d4f",
        animation: closing
          ? "toastOut 0.3s ease forwards"
          : "toastIn 0.3s ease-out",
      }}
    >
      <BiSolidErrorAlt
        size={20}
        color="#ff4d4f"
        style={{ flexShrink: 0, marginTop: "2px" }}
      />
      <div className="flex-1">
        <p
          style={{
            fontSize: "0.65rem",
            letterSpacing: "0.15em",
            textTransform: "uppercase",
            fontWeight: 700,
            color: "#ff4d4f",
            fontFamily: "'Rajdhani', sans-serif",
          }}
        >
          ERROR
        </p>
        <p
          style={{
            fontSize: "0.875rem",
            color: "#e8e8f0",
            marginTop: "2px",
          }}
        >
          {message}
        </p>
      </div>
      <button
        onClick={handleClose}
        style={{
          flexShrink: 0,
          color: "#a0a0c0",
          background: "transparent",
          border: "none",
          cursor: "pointer",
        }}
      >
        <BiX size={18} />
      </button>
    </div>
  );
}

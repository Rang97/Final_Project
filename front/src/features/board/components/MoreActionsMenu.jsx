import { useEffect, useRef, useState } from "react";
import { FiMoreVertical } from "react-icons/fi";

// 게시글/댓글 우측의 "..." 액션 메뉴
// props.actions: [{ label: string, onClick: () => void, danger?: boolean }]
export default function MoreActionsMenu({ actions }) {
  const [isOpen, setIsOpen] = useState(false);
  const menuRef = useRef(null);

  // 메뉴 바깥을 클릭하면 자동으로 닫히게 함
  useEffect(() => {
    if (!isOpen) return;

    const handleClickOutside = (e) => {
      if (menuRef.current && !menuRef.current.contains(e.target)) {
        setIsOpen(false);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, [isOpen]);

  return (
    <div className="relative" ref={menuRef}>
      <button
        onClick={() => setIsOpen((prev) => !prev)}
        className="p-2 rounded-sm hover:bg-white/10 text-slate-300 hover:text-white transition-colors cursor-pointer"
      >
        <FiMoreVertical className="h-5 w-5" />
      </button>

      {isOpen && (
        <div className="absolute right-0 mt-1 w-30 bg-[#0d0b1e] border border-[#3A9AFF]/20 rounded-sm shadow-lg z-10 overflow-hidden">
          {actions.map((action) => (
            <button
              key={action.label}
              onClick={() => {
                setIsOpen(false);
                action.onClick();
              }}
              className={`w-full text-left px-3 py-2 text-sm font-medium transition-colors cursor-pointer ${
                action.danger
                  ? "text-red-400 hover:bg-red-500/10"
                  : "text-slate-200 hover:bg-white/5 border-b border-slate-800"
              }`}
            >
              {action.label}
            </button>
          ))}
        </div>
      )}
    </div>
  );
}

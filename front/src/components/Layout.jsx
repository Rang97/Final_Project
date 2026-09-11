import { Outlet } from "react-router-dom";
import Navbar from "./Navbar";

export default function Layout() {
  return (
    <div className="min-h-full flex flex-col" style={{ background: "#08080f" }}>
      <Navbar />
      <main className="flex-1">
        <Outlet />
      </main>
      <footer
        className="border-t py-8 text-center text-sm"
        style={{ borderColor: "rgba(255,255,255,0.06)", color: "#6060a0" }}
      ></footer>
    </div>
  );
}

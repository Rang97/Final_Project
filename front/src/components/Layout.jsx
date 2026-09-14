import { Outlet } from "react-router-dom";
import Navbar from "./Navbar";

export default function Layout() {
  return (
    <div className="min-h-full flex flex-col" style={{ background: "#06040f" }}>
      <Navbar />
      <main className="flex-1">
        <Outlet />
      </main>
      <footer
        className="border-t py-8 text-center text-xs"
        style={{
          borderColor: "rgba(58,154,255,0.08)",
          color: "rgba(240,240,250,0.2)",
        }}
      >
        © 2026 GuildHub. All rights reserved.
      </footer>
    </div>
  );
}

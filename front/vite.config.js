import react from "@vitejs/plugin-react";
import { defineConfig } from "vite";
import tailwindcss from "@tailwindcss/vite";

export default defineConfig({
  plugins: [react(), tailwindcss()],
  server: {
    // 서버 요청 전부 8080 포트로 전달
    proxy: {
      "/api": "http://localhost:8080",
    },
  },
});

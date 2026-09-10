import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import tailwindcss from "@tailwindcss/vite";

// https://vite.dev/config/
export default defineConfig({
  plugins: [react(), tailwindcss()],

  server: {
    // 같은 와이파이의 휴대폰에서도 접속 가능
    host: "0.0.0.0",
    port: 5173,

    // React의 /api 요청을 Spring Boot로 전달
    proxy: {
      "/api": {
        target: "http://localhost:8080", // 백엔드로 전달
        changeOrigin: true,
      },
    },
  },
});

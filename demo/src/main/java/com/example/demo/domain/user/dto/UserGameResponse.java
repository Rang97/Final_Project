package com.example.demo.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자가 등록한 선호 게임")
public record UserGameResponse(
        @Schema(description = "게임 ID", example = "1") Long gameId,
        @Schema(description = "게임 이름", example = "리그 오브 레전드") String name,
        @Schema(description = "게임 이미지 URL", example = "https://example.com/game-cover.png", nullable = true) String coverUrl,
        @Schema(description = "게임 장르", example = "MOBA", nullable = true) String genre,
        @Schema(description = "대표 게임 여부", example = "true") boolean isMain) {
}

package com.example.demo.domain.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserGameCreateRequest {

    @NotNull(message = "게임 ID는 필수입니다.")
    @Positive(message = "게임 ID는 양수여야 합니다.")
    @Schema(description = "game 테이블에 존재하는 게임 ID", example = "1", minimum = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long gameId;
}

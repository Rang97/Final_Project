package com.example.demo.domain.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserGameCreateRequest {

    @NotNull(message = "게임 ID는 필수입니다.")
    @Positive(message = "게임 ID는 양수여야 합니다.")
    private Long gameId;
}

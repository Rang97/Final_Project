package com.example.demo.domain.user.dto;

import com.example.demo.domain.saju.dto.SajuInputRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @NotBlank(message = "아이디는 필수입니다.")
        @Size(min = 4, max = 30, message = "아이디는 4자 이상 30 이하여야 합니다.")
        @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "아이디는 영문, 숫자, 밑줄만 사용할 수 있습니다.")
        String loginId,

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 4, max = 30, message = "비밀번호는 4자 이상 30 이하여야 합니다.")
        String password,

        @NotBlank(message = "닉네임은 필수입니다.")
        @Size(max = 15, message = "닉네임은 15 이하여야 합니다.")
        String nickname,

        @Valid
        SajuInputRequest sajuInput
) {
}

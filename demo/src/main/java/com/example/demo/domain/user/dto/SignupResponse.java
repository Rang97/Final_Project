package com.example.demo.domain.user.dto;

import com.example.demo.domain.user.entity.User;

public record SignupResponse(
        Long userId,
        String loginId,
        String nickname,
        String role,
        SajuRegistrationStatus sajuStatus,
        String sajuMessage
) {

    public static SignupResponse from(User user) {
        return new SignupResponse(
                user.getUserId(),
                user.getLoginId(),
                user.getNickname(),
                user.getRole(),
                SajuRegistrationStatus.NOT_PROVIDED,
                "사주 정보가 입력되지 않았습니다."
        );
    }

    public SignupResponse withSajuStatus(SajuRegistrationStatus status, String message) {
        return new SignupResponse(userId, loginId, nickname, role, status, message);
    }
}

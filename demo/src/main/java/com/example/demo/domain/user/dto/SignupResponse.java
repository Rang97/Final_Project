package com.example.demo.domain.user.dto;

import com.example.demo.domain.user.entity.User;

public record SignupResponse(
        Long userId,
        String loginId,
        String nickname,
        String role
) {

    public static SignupResponse from(User user) {
        return new SignupResponse(
                user.getUserId(),
                user.getLoginId(),
                user.getNickname(),
                user.getRole()
        );
    }
}

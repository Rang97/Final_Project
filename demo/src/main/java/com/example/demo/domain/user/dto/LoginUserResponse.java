package com.example.demo.domain.user.dto;

import com.example.demo.domain.user.entity.User;

public record LoginUserResponse(
        Long userId,
        String loginId,
        String nickname,
        String role
) {
    public static LoginUserResponse from(User user) {
        return new LoginUserResponse(
                user.getUserId(),
                user.getLoginId(),
                user.getNickname(),
                user.getRole()
        );
    }

}

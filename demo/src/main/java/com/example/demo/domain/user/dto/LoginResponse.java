package com.example.demo.domain.user.dto;

public record LoginResponse(
        String tokenType,
        String accessToken,
        long expiresIn,
        LoginUserResponse user
) {
    public static LoginResponse bearer(
            String accessToken,
            long expiresIn,
            LoginUserResponse user
    ) {
        return new LoginResponse("Bearer", accessToken, expiresIn, user);
    }
}

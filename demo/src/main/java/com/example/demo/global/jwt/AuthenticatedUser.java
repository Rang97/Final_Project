package com.example.demo.global.jwt;

public record AuthenticatedUser(
        Long userId,
        String loginId,
        String role
) {
}

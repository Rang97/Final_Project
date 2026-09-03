package com.example.demo.auth.dto;

public record SignupResponse(Long userId, String loginId, String nickname, String role) {
}

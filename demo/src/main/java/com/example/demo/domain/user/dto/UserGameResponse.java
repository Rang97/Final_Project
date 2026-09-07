package com.example.demo.domain.user.dto;

public record UserGameResponse(Long gameId, String name, String coverUrl, String genre, boolean isMain) {
}

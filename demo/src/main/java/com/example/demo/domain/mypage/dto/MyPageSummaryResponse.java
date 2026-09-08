package com.example.demo.domain.mypage.dto;

import com.example.demo.domain.user.dto.UserGameResponse;

import java.util.List;

public record MyPageSummaryResponse(
        MyPageSajuResponse saju,
        List<UserGameResponse> games
) {
}
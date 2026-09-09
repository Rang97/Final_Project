package com.example.demo.domain.mypage.dto;

import com.example.demo.domain.saju.dto.SajuResponse;

import java.util.List;
import java.util.Map;

public record MyPageSajuResponse(
        SajuResponse saju,
        Map<String, Double> elementPercentages,
        List<String> dominantElements   // 최대 2개까지만
) {
}
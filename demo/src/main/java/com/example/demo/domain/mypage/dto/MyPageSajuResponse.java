package com.example.demo.domain.mypage.dto;

import com.example.demo.domain.saju.dto.SajuResponse;

import java.util.Map;

public record MyPageSajuResponse(
        SajuResponse saju,
        Map<String, Double> elementPercentages,  // "wood": 23.5 등
        String dominantElement                    // "WOOD", "FIRE" 등 - 문구는 프론트가 매핑
) {
}
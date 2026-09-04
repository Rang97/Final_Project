package com.example.demo.domain.mypage.dto;

import com.example.demo.domain.saju.dto.SajuResponse;

import java.util.Map;

public record MyPageSajuResponse(
        SajuResponse saju,
        Map<String, Double> elementPercentages,  // "wood": 23.5 등
        String dominantElement,                   // 가장 높은 오행 ("WOOD" 등)
        String styleDescription                    // 오행 기반 게임 스타일 설명
) {
}
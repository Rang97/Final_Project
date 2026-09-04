// domain/mypage/service/MyPageService.java
package com.example.demo.domain.mypage.service;

import com.example.demo.domain.mypage.dto.MyPageSajuResponse;
import com.example.demo.domain.mypage.util.ElementStyleMapper;
import com.example.demo.domain.saju.dto.SajuInputRequest;
import com.example.demo.domain.saju.dto.SajuResponse;
import com.example.demo.domain.saju.repository.SajuMapper;
import com.example.demo.domain.saju.service.SajuCalculationService;
import com.example.demo.domain.saju.service.SajuInputService;
import com.example.demo.domain.saju.util.FiveElement;
import com.example.demo.global.util.CurrentUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final SajuInputService sajuInputService;
    private final SajuCalculationService sajuCalculationService;
    private final SajuMapper sajuMapper;
    private final CurrentUserProvider currentUserProvider;

    @Transactional
    public MyPageSajuResponse updateBirthInfo(SajuInputRequest request) {
        Long userId = currentUserProvider.getCurrentUserId();

        sajuInputService.saveOrUpdate(userId, request);
        SajuResponse sajuResponse = sajuCalculationService.calculate(userId);

        return buildResponse(sajuResponse);
    }

    public MyPageSajuResponse getMySaju() {
        Long userId = currentUserProvider.getCurrentUserId();

        var saju = sajuMapper.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("등록된 사주 정보가 없습니다. 생년월일시를 먼저 입력해주세요."));

        return buildResponse(SajuResponse.from(saju));
    }

    private MyPageSajuResponse buildResponse(SajuResponse saju) {
        double total = saju.woodCount() + saju.fireCount() + saju.earthCount()
                + saju.metalCount() + saju.waterCount();

        Map<String, Double> percentages = new LinkedHashMap<>();
        percentages.put("wood", percent(saju.woodCount(), total));
        percentages.put("fire", percent(saju.fireCount(), total));
        percentages.put("earth", percent(saju.earthCount(), total));
        percentages.put("metal", percent(saju.metalCount(), total));
        percentages.put("water", percent(saju.waterCount(), total));

        FiveElement dominant = findDominant(saju);

        return new MyPageSajuResponse(
                saju,
                percentages,
                dominant.name(),
                ElementStyleMapper.describe(dominant)
        );
    }

    private double percent(double value, double total) {
        if (total == 0) return 0.0;
        return Math.round((value / total) * 1000) / 10.0;  // 소수점 1자리
    }

    private FiveElement findDominant(SajuResponse saju) {
        double max = Math.max(saju.woodCount(),
                Math.max(saju.fireCount(),
                        Math.max(saju.earthCount(),
                                Math.max(saju.metalCount(), saju.waterCount()))));

        if (max == saju.woodCount()) return FiveElement.WOOD;
        if (max == saju.fireCount()) return FiveElement.FIRE;
        if (max == saju.earthCount()) return FiveElement.EARTH;
        if (max == saju.metalCount()) return FiveElement.METAL;
        return FiveElement.WATER;
    }
}
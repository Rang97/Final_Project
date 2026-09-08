package com.example.demo.domain.mypage.service;

import com.example.demo.domain.mypage.dto.MyPageSajuResponse;
import com.example.demo.domain.mypage.dto.MyPageSummaryResponse;
import com.example.demo.domain.saju.dto.SajuInputRequest;
import com.example.demo.domain.saju.dto.SajuInputResponse;
import com.example.demo.domain.saju.dto.SajuResponse;
import com.example.demo.domain.saju.repository.SajuMapper;
import com.example.demo.domain.saju.service.SajuCalculationService;
import com.example.demo.domain.saju.service.SajuInputService;
import com.example.demo.domain.saju.util.FiveElement;
import com.example.demo.domain.user.service.UserGameService;
import com.example.demo.global.util.CurrentUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MyPageService {

    //최대 오행 갯수
    private static final int MAX_DOMINANT_ELEMENTS = 2;

    private final SajuInputService sajuInputService;
    private final SajuCalculationService sajuCalculationService;
    private final SajuMapper sajuMapper;
    private final UserGameService userGameService;
    private final CurrentUserProvider currentUserProvider;

    @Transactional
    public SajuInputResponse updateBirthInfo(SajuInputRequest request) {
        Long userId = currentUserProvider.getCurrentUserId();
        return sajuInputService.saveOrUpdate(userId, request);
    }

    @Transactional
    public MyPageSajuResponse calculateSaju() {
        Long userId = currentUserProvider.getCurrentUserId();
        SajuResponse sajuResponse = sajuCalculationService.calculate(userId);
        return buildResponse(sajuResponse);
    }

    public MyPageSajuResponse getMySaju() {
        Long userId = currentUserProvider.getCurrentUserId();

        var saju = sajuMapper.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "등록된 사주 정보가 없습니다. 생년월일시를 먼저 입력해주세요."));

        return buildResponse(SajuResponse.from(saju));
    }

    public MyPageSummaryResponse getMyPageSummary() {
        MyPageSajuResponse saju = null;
        try {
            saju = getMySaju();
        } catch (IllegalArgumentException e) {
            // 사주 정보 없음 - saju는 null로 유지
        }
        var games = userGameService.getMyGames();
        return new MyPageSummaryResponse(saju, games);
    }

    private MyPageSajuResponse buildResponse(SajuResponse saju) {
        double total = saju.woodCount() + saju.fireCount() + saju.earthCount()
                + saju.metalCount() + saju.waterCount();

        //오행 퍼센트 계산
        Map<String, Double> percentages = new LinkedHashMap<>();
        percentages.put("wood", percent(saju.woodCount(), total));
        percentages.put("fire", percent(saju.fireCount(), total));
        percentages.put("earth", percent(saju.earthCount(), total));
        percentages.put("metal", percent(saju.metalCount(), total));
        percentages.put("water", percent(saju.waterCount(), total));

        List<String> dominants = findDominants(saju).stream()
                .map(Enum::name)
                .limit(MAX_DOMINANT_ELEMENTS)   // 3개 이상이면 앞 2개만
                .toList();

        return new MyPageSajuResponse(saju, percentages, dominants);
    }

    private double percent(double value, double total) {
        if (total == 0) return 0.0;
        return Math.round((value / total) * 1000) / 10.0;
    }

    private List<FiveElement> findDominants(SajuResponse saju) {
        double max = Math.max(saju.woodCount(),
                Math.max(saju.fireCount(),
                        Math.max(saju.earthCount(),
                                Math.max(saju.metalCount(), saju.waterCount()))));


        //오행 퍼센트 비교
        List<FiveElement> result = new ArrayList<>();
        if (saju.woodCount() == max) result.add(FiveElement.WOOD);
        if (saju.fireCount() == max) result.add(FiveElement.FIRE);
        if (saju.earthCount() == max) result.add(FiveElement.EARTH);
        if (saju.metalCount() == max) result.add(FiveElement.METAL);
        if (saju.waterCount() == max) result.add(FiveElement.WATER);

        return result;
    }
}
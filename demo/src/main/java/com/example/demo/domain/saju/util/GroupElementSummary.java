package com.example.demo.domain.saju.util;

import java.util.List;

// 파티 전체 합계 결과
public record GroupElementSummary(
        double totalWood,
        double totalFire,
        double totalEarth,
        double totalMetal,
        double totalWater,
        // 최대/최소 오행 동점일 수 있어서 List
        List<FiveElement> maxElements, // 최대
        List<FiveElement> minElements // 최소
) {
}

package com.example.demo.domain.saju.service;

import com.example.demo.domain.saju.util.FiveElement;
import com.example.demo.domain.saju.util.FiveElementProfile;
import com.example.demo.domain.saju.util.GroupElementSummary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChemistryService {

    // (1) 파티 오행 합계 -> 최고/최저 오행 도출
    public GroupElementSummary summarizeGroup(List<FiveElementProfile> members) {
        double totalWood = 0;
        double totalFire = 0;
        double totalEarth = 0;
        double totalMetal = 0;
        double totalWater = 0;

        // 각 유저 오행 더하기
        for (FiveElementProfile member : members) {
            totalWood += member.wood();
            totalFire += member.fire();
            totalEarth += member.earth();
            totalMetal += member.metal();
            totalWater += member.water();
        }

        FiveElement[] elements = {FiveElement.WOOD, FiveElement.FIRE, FiveElement.EARTH, FiveElement.METAL, FiveElement.WATER};
        double[] totals = {totalWood, totalFire, totalEarth, totalMetal, totalWater};

        // 임시로 [0]을 최대/최소로 정의, 각 value 비교로 최대/최소 갱신
        double max = totals[0];
        double min = totals[0];
        for (double value : totals) {
            if (value > max) {
                max = value;
            } else if (value < min) {
                min = value;
            }
        }

        List<FiveElement> maxElements = new ArrayList<>();
        List<FiveElement> minElements = new ArrayList<>();
        // 각 오행 합계가 max/min이랑 같으면 리스트 항목 추가
        for (int i = 0; i < elements.length; i++) {
            if (totals[i] == max) {
                maxElements.add(elements[i]);
            }
            if (totals[i] == min) {
                minElements.add(elements[i]);
            }
        }

        return new GroupElementSummary(totalWood, totalFire, totalEarth, totalMetal, totalWater, maxElements, minElements);
    }

}

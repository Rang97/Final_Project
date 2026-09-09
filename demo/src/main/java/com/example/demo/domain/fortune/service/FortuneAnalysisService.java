package com.example.demo.domain.fortune.service;

import com.example.demo.domain.fortune.dto.FortuneRequest.*;
import com.example.demo.domain.saju.entity.Saju;
import com.example.demo.domain.saju.util.FiveElement;
import com.example.demo.domain.saju.util.FiveElementProfile;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@Service
public class FortuneAnalysisService {
    public UserSaju profile(Saju saju) {
        // Validate the day master without inferring missing birth information.
        stemElement(saju.getDayStem());
        double[] values = {saju.getWoodCount(), saju.getFireCount(), saju.getEarthCount(),
                saju.getMetalCount(), saju.getWaterCount()};
        if (Arrays.stream(values).anyMatch(v -> !Double.isFinite(v) || v < 0)
                || Arrays.stream(values).sum() <= 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "사주 오행 데이터를 다시 계산해 주세요.");
        }
        double max = Arrays.stream(values).max().orElseThrow();
        double min = Arrays.stream(values).min().orElseThrow();
        List<FiveElement> strong = new ArrayList<>(), weak = new ArrayList<>();
        if (max - min > 0.000001) {
            for (int i = 0; i < values.length; i++) {
                if (Math.abs(values[i] - max) < 0.000001) strong.add(FiveElement.values()[i]);
                if (Math.abs(values[i] - min) < 0.000001) weak.add(FiveElement.values()[i]);
            }
        }
        return new UserSaju(saju.getDayStem(), strong, weak,
                new FiveElementProfile(values[0], values[1], values[2], values[3], values[4]));
    }

    public Analysis analyze(UserSaju user, Today today) {
        Set<String> positives = new LinkedHashSet<>(), cautions = new LinkedHashSet<>();
        for (FiveElement element : today.mainElements()) {
            for (FiveElement weak : user.weakElements()) {
                if (element == weak || element.generates() == weak)
                    positives.add("오늘의 " + element + " 기운이 약한 " + weak + " 요소를 보완하므로 준비와 점진적인 시도에 집중하세요.");
                if (element.overcomes() == weak)
                    cautions.add("오늘의 " + element + " 기운이 약한 " + weak + " 요소를 제약하므로 무리한 시도보다 점검과 휴식을 우선하세요.");
            }
            for (FiveElement strong : user.strongElements()) {
                if (element == strong || element.generates() == strong)
                    cautions.add("이미 강한 " + strong + " 요소가 더 강조되므로 과한 확장보다 페이스 조절과 협업을 의식하세요.");
                if (strong.generates() == element)
                    positives.add("강한 " + strong + " 요소를 오늘의 " + element + " 방향으로 활용해 익숙한 역할과 꾸준한 실행에 집중하세요.");
            }
        }
        if (positives.isEmpty()) positives.add("익숙한 역할과 기본 준비를 유지하며 차분히 집중하기 좋은 흐름입니다.");
        if (cautions.isEmpty()) cautions.add("결과를 서두르지 말고 상황을 확인하며 무리하지 않는 페이스를 유지하세요.");
        return new Analysis(List.copyOf(positives), List.copyOf(cautions));
    }

    public static FiveElement stemElement(String stem) {
        if (stem == null) throw invalidPillar();
        return switch (stem) {
            case "甲", "乙", "갑", "을" -> FiveElement.WOOD;
            case "丙", "丁", "병", "정" -> FiveElement.FIRE;
            case "戊", "己", "무", "기" -> FiveElement.EARTH;
            case "庚", "辛", "경", "신" -> FiveElement.METAL;
            case "壬", "癸", "임", "계" -> FiveElement.WATER;
            default -> throw invalidPillar();
        };
    }

    public static FiveElement branchElement(String branch) {
        if (branch == null) throw invalidPillar();
        return switch (branch) {
            case "寅", "卯", "인", "묘" -> FiveElement.WOOD;
            case "巳", "午", "사", "오" -> FiveElement.FIRE;
            case "丑", "辰", "未", "戌", "축", "진", "미", "술" -> FiveElement.EARTH;
            case "申", "酉", "신", "유" -> FiveElement.METAL;
            case "子", "亥", "자", "해" -> FiveElement.WATER;
            default -> throw invalidPillar();
        };
    }

    private static ResponseStatusException invalidPillar() {
        return new ResponseStatusException(HttpStatus.BAD_GATEWAY, "천간·지지 데이터가 올바르지 않습니다.");
    }
}


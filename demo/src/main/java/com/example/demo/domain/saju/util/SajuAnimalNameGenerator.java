package com.example.demo.domain.saju.util;

import java.util.Map;

public final class SajuAnimalNameGenerator {

    private static final Map<String, String> STEM_COLORS = Map.ofEntries(
            Map.entry("甲", "푸른"), Map.entry("갑", "푸른"),
            Map.entry("乙", "푸른"), Map.entry("을", "푸른"),
            Map.entry("丙", "붉은"), Map.entry("병", "붉은"),
            Map.entry("丁", "붉은"), Map.entry("정", "붉은"),
            Map.entry("戊", "노란"), Map.entry("무", "노란"),
            Map.entry("己", "노란"), Map.entry("기", "노란"),
            Map.entry("庚", "하얀"), Map.entry("경", "하얀"),
            Map.entry("辛", "하얀"), Map.entry("신", "하얀"),
            Map.entry("壬", "검은"), Map.entry("임", "검은"),
            Map.entry("癸", "검은"), Map.entry("계", "검은")
    );

    private static final Map<String, String> BRANCH_ANIMALS = Map.ofEntries(
            Map.entry("子", "쥐"), Map.entry("자", "쥐"),
            Map.entry("丑", "소"), Map.entry("축", "소"),
            Map.entry("寅", "호랑이"), Map.entry("인", "호랑이"),
            Map.entry("卯", "토끼"), Map.entry("묘", "토끼"),
            Map.entry("辰", "용"), Map.entry("진", "용"),
            Map.entry("巳", "뱀"), Map.entry("사", "뱀"),
            Map.entry("午", "말"), Map.entry("오", "말"),
            Map.entry("未", "양"), Map.entry("미", "양"),
            Map.entry("申", "원숭이"), Map.entry("신", "원숭이"),
            Map.entry("酉", "닭"), Map.entry("유", "닭"),
            Map.entry("戌", "개"), Map.entry("술", "개"),
            Map.entry("亥", "돼지"), Map.entry("해", "돼지")
    );

    private SajuAnimalNameGenerator() {
    }

    public static String generate(String dayStem, String dayBranch) {
        String color = STEM_COLORS.get(dayStem);
        String animal = BRANCH_ANIMALS.get(dayBranch);

        if (color == null || animal == null) {
            throw new IllegalArgumentException(
                    "지원하지 않는 일주입니다: dayStem=" + dayStem + ", dayBranch=" + dayBranch
            );
        }

        return color + " " + animal;
    }
}

// domain/mypage/util/ElementStyleMapper.java
package com.example.demo.domain.mypage.util;

import com.example.demo.domain.saju.util.FiveElement;

import java.util.Map;

public class ElementStyleMapper {

    private static final Map<FiveElement, String> STYLE_DESCRIPTIONS = Map.of(
            FiveElement.WOOD, "성장과 확장을 좋아하는 목(木) 기운이 강해요. 캐릭터를 키워나가는 성장형 RPG나, 팀원과 함께 발전하는 협동 게임에서 강점을 보여요.",
            FiveElement.FIRE, "열정적이고 승부욕 강한 화(火) 기운이 강해요. 빠른 판단과 공격적인 플레이가 필요한 액션, 배틀로얄 장르에서 빛을 발해요.",
            FiveElement.EARTH, "안정적이고 신뢰를 주는 토(土) 기운이 강해요. 팀의 중심을 잡아주는 서포터, 탱커 포지션이나 전략 시뮬레이션에 잘 맞아요.",
            FiveElement.METAL, "냉철하고 계획적인 금(金) 기운이 강해요. 정교한 전략과 판단력이 중요한 전략 게임, 퍼즐, 저격 포지션에서 강점을 보여요.",
            FiveElement.WATER, "유연하고 적응력이 뛰어난 수(水) 기운이 강해요. 상황에 맞춰 유동적으로 대응하는 유틸리티, 정글/로밍 포지션에 잘 맞아요."
    );

    public static String describe(FiveElement dominant) {
        return STYLE_DESCRIPTIONS.get(dominant);
    }
}
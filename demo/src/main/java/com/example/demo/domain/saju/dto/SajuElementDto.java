package com.example.demo.domain.saju.dto;

import com.example.demo.domain.saju.util.FiveElementProfile;
import lombok.Getter;
import lombok.Setter;

// 사주 오행 DTO
@Getter
@Setter
public class SajuElementDto {

    private Long userId;
    private double wood;
    private double fire;
    private double earth;
    private double metal;
    private double water;

    // DTO 요소를 FiveElementProfile로 변환
    public FiveElementProfile toProfile() {
        return new FiveElementProfile(wood, fire, earth, metal, water);
    }

}

package com.example.demo.domain.saju.util;

// 오행 enum
public enum FiveElement {
    WOOD, FIRE, EARTH, METAL, WATER;

    // 상생: 목생화 -> 화생토 -> 토생금 -> 금생수 -> 수생목
    public FiveElement generates(){
        return switch (this) {
            case WOOD -> FIRE;
            case FIRE -> EARTH;
            case EARTH -> METAL;
            case METAL -> WATER;
            case WATER -> WOOD;
        };
    }

    // 상극: 목극토 -> 토극수 -> 수극화 -> 화극금 -> 금극목
    public FiveElement overcomes(){
        return switch (this) {
            case WOOD -> EARTH;
            case EARTH -> WATER;
            case WATER -> FIRE;
            case FIRE -> METAL;
            case METAL -> WOOD;
        };
    }

    // this-other 상생관계 확인
    public boolean isGeneratesWith(FiveElement other) {
        return this.generates() == other || other.generates() == this;
    }

    // this-other 상극관계 확인
    public boolean isOvercomesWith(FiveElement other) {
        return this.overcomes() == other || other.overcomes() == this;
    }
}

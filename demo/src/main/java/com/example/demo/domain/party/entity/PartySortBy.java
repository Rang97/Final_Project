package com.example.demo.domain.party.entity;

// 파티 기본 정렬 기준 enum
public enum PartySortBy {
    TITLE, GENRE, MEMBER_COUNT, WOOD, FIRE, EARTH, METAL, WATER, CHEMISTRY_MATCH;

    // sortBy 값이 오행 5개 중 하나인지 판단
    public boolean isElement() {
        return switch (this) {
            case WOOD, FIRE, EARTH, METAL, WATER -> true;
            default -> false;
        };
    }
}

package com.example.demo.infra.sajuapi.dto;

import com.example.demo.domain.user.entity.Gender;

// 외부 사주 API로 보내는 DTO
public record SajuApiRequest(

        int birth_year,
        int birth_month,
        int birth_day,
        Integer birth_hour,
        Integer birth_minute,
        boolean is_lunar,
        String gender
) {
    public static SajuApiRequest of(
            int birthYear,
            int birthMonth,
            int birthDay,
            Integer birthHour,
            Integer birthMinute,
            boolean isLunar,
            Gender gender
    ) {
        return new SajuApiRequest(
                birthYear,
                birthMonth,
                birthDay,
                birthHour,
                birthMinute,
                isLunar,
                toApiGender(gender)
        );
    }

    private static String toApiGender(Gender gender) {
        return switch (gender) {
            case MALE -> "male";
            case FEMALE -> "female";
        };
    }
}

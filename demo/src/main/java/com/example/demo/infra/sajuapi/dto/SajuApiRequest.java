package com.example.demo.infra.sajuapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

// 외부 사주 API로 보내는 DTO
public record SajuApiRequest(

        int birth_year,
        int birth_month,
        int birth_day,
        int birth_hour,
        int birth_minute,
        boolean is_lunar,
        String gender
) {
}

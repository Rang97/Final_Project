package com.example.demo.infra.sajuapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// 외부 사주 API에서 받아오는 DTO
@JsonIgnoreProperties(ignoreUnknown = true)
public record SajuApiResponse(FiveElements five_elements, HourPillar hour_pillar,  DayPillar day_pillar, MonthPillar month_pillar, YearPillar year_pillar) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record FiveElements(
            double wood,
            double fire,
            double earth,
            double metal,
            double water,
            String dominant,
            String weakest
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record YearPillar(
            String stem,
            String stem_korean,
            String branch,
            String branch_korean
    ){}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record MonthPillar(
            String stem,
            String stem_korean,
            String branch,
            String branch_korean
    ){}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record DayPillar(
            String stem,
            String stem_korean,
            String branch,
            String branch_korean
    ){}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record HourPillar(
            String stem,
            String stem_korean,
            String branch,
            String branch_korean
    ){}
}

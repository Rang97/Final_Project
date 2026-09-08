package com.example.demo.domain.saju.entity;

import com.example.demo.domain.user.entity.BirthTimeBranch;
import com.example.demo.domain.user.entity.Gender;
import com.example.demo.infra.sajuapi.dto.SajuApiResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Saju {

    private Long sajuId;
    private Long userId;
    private Gender gender;
    private LocalDate birthDate;
    private CalendarType calendarType;
    private String birthTimeType;
    private LocalTime birthTime;
    private BirthTimeBranch birthTimeBranch;
    private String yearStem;
    private String yearBranch;
    private String monthStem;
    private String monthBranch;
    private String dayStem;
    private String dayBranch;
    private String hourStem;
    private String hourBranch;
    private double woodCount;
    private double fireCount;
    private double earthCount;
    private double metalCount;
    private double waterCount;
    private String sajuAnimalName;
    private LocalDateTime calculatedAt;
    private LocalDateTime updatedAt;

    private Saju(Long userId, SajuInput input, SajuApiResponse response) {
        SajuApiResponse.FiveElements elements = response.five_elements();

        this.userId = userId;
        this.gender = input.getGender();
        this.birthDate = input.getBirthDate();
        this.calendarType = input.getCalendarType();
        this.birthTimeType = input.getBirthTimeType();
        this.birthTime = null;
        this.birthTimeBranch = input.getBirthTimeBranch();
        this.yearStem = response.year_pillar().stem();
        this.yearBranch = response.year_pillar().branch();
        this.monthStem = response.month_pillar().stem();
        this.monthBranch = response.month_pillar().branch();
        this.dayStem = response.day_pillar().stem();
        this.dayBranch = response.day_pillar().branch();
        this.hourStem = response.hour_pillar() == null ? null : response.hour_pillar().stem();
        this.hourBranch = response.hour_pillar() == null ? null : response.hour_pillar().branch();
        this.woodCount = elements.wood();
        this.fireCount = elements.fire();
        this.earthCount = elements.earth();
        this.metalCount = elements.metal();
        this.waterCount = elements.water();
        this.sajuAnimalName = null;
    }

    public static Saju from(Long userId, SajuInput input, SajuApiResponse response) {
        return new Saju(userId, input, response);
    }
}

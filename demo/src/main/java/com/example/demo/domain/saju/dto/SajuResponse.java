package com.example.demo.domain.saju.dto;

import com.example.demo.domain.saju.entity.CalendarType;
import com.example.demo.domain.saju.entity.Saju;
import com.example.demo.domain.user.entity.BirthTimeBranch;
import com.example.demo.domain.user.entity.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record SajuResponse(
        Long sajuId,
        Long userId,
        Gender gender,
        LocalDate birthDate,
        CalendarType calendarType,
        String birthTimeType,
        BirthTimeBranch birthTimeBranch,
        String yearStem,
        String yearBranch,
        String monthStem,
        String monthBranch,
        String dayStem,
        String dayBranch,
        String hourStem,
        String hourBranch,
        double woodCount,
        double fireCount,
        double earthCount,
        double metalCount,
        double waterCount,
        String sajuAnimalName,
        LocalDateTime calculatedAt
) {
    public static SajuResponse from(Saju saju) {
        return new SajuResponse(
                saju.getSajuId(),
                saju.getUserId(),
                saju.getGender(),
                saju.getBirthDate(),
                saju.getCalendarType(),
                saju.getBirthTimeType(),
                saju.getBirthTimeBranch(),
                saju.getYearStem(),
                saju.getYearBranch(),
                saju.getMonthStem(),
                saju.getMonthBranch(),
                saju.getDayStem(),
                saju.getDayBranch(),
                saju.getHourStem(),
                saju.getHourBranch(),
                saju.getWoodCount(),
                saju.getFireCount(),
                saju.getEarthCount(),
                saju.getMetalCount(),
                saju.getWaterCount(),
                saju.getSajuAnimalName(),
                saju.getCalculatedAt()
        );
    }
}

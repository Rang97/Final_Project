package com.example.demo.domain.saju.dto;

import com.example.demo.domain.saju.entity.CalendarType;
import com.example.demo.domain.saju.entity.SajuInput;
import com.example.demo.domain.user.entity.BirthTimeBranch;
import com.example.demo.domain.user.entity.Gender;

import java.time.LocalDate;

public record SajuInputResponse(
        Long sajuInputId,
        Long userId,
        LocalDate birthDate,
        BirthTimeBranch birthTimeBranch,
        Gender gender,
        CalendarType calendarType,
        String birthTimeType
) {
    public static SajuInputResponse from(SajuInput input) {
        return new SajuInputResponse(
                input.getSajuInputId(),
                input.getUserId(),
                input.getBirthDate(),
                input.getBirthTimeBranch(),
                input.getGender(),
                input.getCalendarType(),
                input.getBirthTimeType()
        );
    }
}

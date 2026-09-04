package com.example.demo.domain.saju.entity;

import com.example.demo.domain.user.entity.BirthTimeBranch;
import com.example.demo.domain.user.entity.Gender;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SajuInput {

    private Long sajuInputId;
    private Long userId;
    private LocalDate birthDate;
    private Gender gender;
    private CalendarType calendarType;
    private String birthTimeType;
    private BirthTimeBranch birthTimeBranch;

    private SajuInput(
            Long userId,
            LocalDate birthDate,
            Gender gender,
            CalendarType calendarType,
            String birthTimeType,
            BirthTimeBranch birthTimeBranch
    ) {
        this.userId = userId;
        this.birthDate = birthDate;
        this.gender = gender;
        this.calendarType = calendarType;
        this.birthTimeType = birthTimeType;
        this.birthTimeBranch = birthTimeBranch;
    }

    public static SajuInput from(
            Long userId,
            LocalDate birthDate,
            Gender gender,
            CalendarType calendarType,
            BirthTimeBranch birthTimeBranch
    ) {
        boolean unknown = birthTimeBranch == null || birthTimeBranch == BirthTimeBranch.UNKNOWN;
        return new SajuInput(
                userId,
                birthDate,
                gender,
                calendarType,
                unknown ? "UNKNOWN" : "TIME_BRANCH",
                unknown ? null : birthTimeBranch
        );
    }
}

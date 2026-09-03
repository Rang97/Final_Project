package com.example.demo.domain.user.dto;

import com.example.demo.domain.saju.entity.CalendarType;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.entity.BirthTimeBranch;
import com.example.demo.domain.user.entity.Gender;

import java.time.LocalDate;

public record SignupResponse(
        Long userId,
        String loginId,
        String nickname,
        LocalDate birthDate,
        String birthTimeBranch,
        Gender gender,
        CalendarType calendarType,
        String role
) {

    public static SignupResponse from(
            User user,
            LocalDate birthDate,
            BirthTimeBranch birthTimeBranch,
            Gender gender,
            CalendarType calendarType
    ) {
        return new SignupResponse(
                user.getUserId(),
                user.getLoginId(),
                user.getNickname(),
                birthDate,
                birthTimeBranch == null ? null : birthTimeBranch.name(),
                gender,
                calendarType,
                user.getRole()
        );
    }
}

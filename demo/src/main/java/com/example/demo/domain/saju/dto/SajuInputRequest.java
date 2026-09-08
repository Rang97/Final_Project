package com.example.demo.domain.saju.dto;

import com.example.demo.domain.saju.entity.CalendarType;
import com.example.demo.domain.user.entity.BirthTimeBranch;
import com.example.demo.domain.user.entity.Gender;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record SajuInputRequest(
        @NotNull(message = "생년월일은 필수입니다.")
        @Past(message = "생년월일은 과거 날짜여야 합니다.")
        LocalDate birthDate,

        BirthTimeBranch birthTimeBranch,

        @NotNull(message = "성별은 필수입니다.")
        Gender gender,

        @NotNull(message = "양력/음력 선택은 필수입니다.")
        CalendarType calendarType
) {
}

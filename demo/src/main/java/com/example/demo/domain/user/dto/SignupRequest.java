package com.example.demo.domain.user.dto;

import com.example.demo.domain.saju.entity.CalendarType;
import com.example.demo.domain.user.entity.BirthTimeBranch;
import com.example.demo.domain.user.entity.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.AssertTrue;

import java.time.LocalDate;

public record SignupRequest(
        @NotBlank(message = "아이디는 필수입니다.")
        @Size(min = 4, max = 30, message = "아이디는 4자 이상 30 이하여야 합니다.")
        @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "아이디는 영문, 숫자, 밑줄만 사용할 수 있습니다.")
        String loginId,

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 4, max = 30, message = "비밀번호는 4자 이상 30 이하여야 합니다.")
        String password,

        @NotBlank(message = "닉네임은 필수입니다.")
        @Size(max = 15, message = "닉네임은 15 이하여야 합니다.")
        String nickname,

        @Past(message = "생년월일은 과거 날짜여야 합니다.")
        LocalDate birthDate,

        BirthTimeBranch birthTimeBranch,

        Gender gender,

        CalendarType calendarType
) {
    @AssertTrue(message = "출생 시간대를 입력하려면 생년월일도 입력해야 합니다.")
    public boolean isBirthTimeValid() {
        return birthTimeBranch == null || birthDate != null;
    }

    @AssertTrue(message = "생년월일을 입력하면 성별과 양력/음력을 선택해야 합니다.")
    public boolean isSajuInputValid() {
        if (birthDate == null) {
            return gender == null && calendarType == null;
        }
        return gender != null && calendarType != null;
    }
}

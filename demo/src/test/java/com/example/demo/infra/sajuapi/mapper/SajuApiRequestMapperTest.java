package com.example.demo.infra.sajuapi.mapper;

import com.example.demo.domain.saju.entity.CalendarType;
import com.example.demo.domain.saju.entity.SajuInput;
import com.example.demo.domain.user.entity.BirthTimeBranch;
import com.example.demo.domain.user.entity.Gender;
import com.example.demo.infra.sajuapi.dto.SajuApiRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SajuApiRequestMapperTest {

    private final SajuApiRequestMapper mapper = new SajuApiRequestMapper();

    @ParameterizedTest
    @MethodSource("birthTimeBranches")
    void 출생_시간대를_대표_시각으로_변환한다(BirthTimeBranch branch, int expectedHour) {
        SajuApiRequest request = mapper.from(input(branch, Gender.MALE, CalendarType.SOLAR));

        assertThat(request.birth_hour()).isEqualTo(expectedHour);
        assertThat(request.birth_minute()).isEqualTo(30);
    }

    @Test
    void 출생_시간대가_UNKNOWN이면_시와_분을_null로_변환한다() {
        SajuApiRequest request = mapper.from(
                input(BirthTimeBranch.UNKNOWN, Gender.MALE, CalendarType.SOLAR)
        );

        assertThat(request.birth_hour()).isNull();
        assertThat(request.birth_minute()).isNull();
    }

    @Test
    void 양력과_남성을_외부_API_형식으로_변환한다() {
        SajuApiRequest request = mapper.from(input(BirthTimeBranch.JA, Gender.MALE, CalendarType.SOLAR));

        assertThat(request.birth_year()).isEqualTo(2000);
        assertThat(request.birth_month()).isEqualTo(9);
        assertThat(request.birth_day()).isEqualTo(1);
        assertThat(request.is_lunar()).isFalse();
        assertThat(request.gender()).isEqualTo("male");
    }

    @Test
    void 음력과_여성을_외부_API_형식으로_변환한다() {
        SajuApiRequest request = mapper.from(input(BirthTimeBranch.JA, Gender.FEMALE, CalendarType.LUNAR));

        assertThat(request.is_lunar()).isTrue();
        assertThat(request.gender()).isEqualTo("female");
    }

    private SajuInput input(BirthTimeBranch branch, Gender gender, CalendarType calendarType) {
        return SajuInput.from(
                1L,
                LocalDate.of(2000, 9, 1),
                gender,
                calendarType,
                branch
        );
    }

    private static Stream<Arguments> birthTimeBranches() {
        return Stream.of(
                Arguments.of(BirthTimeBranch.JA, 0),
                Arguments.of(BirthTimeBranch.CHUK, 2),
                Arguments.of(BirthTimeBranch.IN, 4),
                Arguments.of(BirthTimeBranch.MYO, 6),
                Arguments.of(BirthTimeBranch.JIN, 8),
                Arguments.of(BirthTimeBranch.SA, 10),
                Arguments.of(BirthTimeBranch.O, 12),
                Arguments.of(BirthTimeBranch.MI, 14),
                Arguments.of(BirthTimeBranch.SIN, 16),
                Arguments.of(BirthTimeBranch.YU, 18),
                Arguments.of(BirthTimeBranch.SUL, 20),
                Arguments.of(BirthTimeBranch.HAE, 22)
        );
    }
}

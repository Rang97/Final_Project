package com.example.demo.domain.saju.service;

import com.example.demo.domain.saju.entity.CalendarType;
import com.example.demo.domain.saju.entity.SajuInput;
import com.example.demo.domain.saju.repository.SajuInputMapper;
import com.example.demo.domain.user.entity.BirthTimeBranch;
import com.example.demo.domain.user.entity.Gender;
import com.example.demo.infra.sajuapi.dto.SajuApiRequest;
import com.example.demo.infra.sajuapi.mapper.SajuApiRequestMapper;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SajuApiPreviewServiceTest {

    private final SajuInputMapper sajuInputMapper = mock(SajuInputMapper.class);
    private final SajuApiPreviewService service = new SajuApiPreviewService(
            sajuInputMapper,
            new SajuApiRequestMapper()
    );

    @Test
    void 저장된_사주_입력을_외부_API_요청으로_변환한다() {
        SajuInput input = SajuInput.from(
                2L,
                LocalDate.of(2000, 9, 1),
                Gender.MALE,
                CalendarType.SOLAR,
                BirthTimeBranch.CHUK
        );
        when(sajuInputMapper.findByUserId(2L)).thenReturn(Optional.of(input));

        SajuApiRequest request = service.preview(2L);

        assertThat(request.birth_year()).isEqualTo(2000);
        assertThat(request.birth_month()).isEqualTo(9);
        assertThat(request.birth_day()).isEqualTo(1);
        assertThat(request.birth_hour()).isEqualTo(2);
        assertThat(request.birth_minute()).isEqualTo(30);
        assertThat(request.is_lunar()).isFalse();
        assertThat(request.gender()).isEqualTo("male");
    }

    @Test
    void 저장된_사주_입력이_없으면_404를_반환한다() {
        when(sajuInputMapper.findByUserId(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.preview(2L))
                .isInstanceOf(ResponseStatusException.class)
                .satisfies(exception -> assertThat(
                        ((ResponseStatusException) exception).getStatusCode().value()
                ).isEqualTo(404));
    }
}

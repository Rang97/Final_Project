package com.example.demo.domain.saju.service;

import com.example.demo.domain.saju.dto.SajuResponse;
import com.example.demo.domain.saju.entity.CalendarType;
import com.example.demo.domain.saju.entity.Saju;
import com.example.demo.domain.saju.entity.SajuInput;
import com.example.demo.domain.saju.repository.SajuInputMapper;
import com.example.demo.domain.saju.repository.SajuMapper;
import com.example.demo.domain.user.entity.BirthTimeBranch;
import com.example.demo.domain.user.entity.Gender;
import com.example.demo.infra.sajuapi.SajuApiClient;
import com.example.demo.infra.sajuapi.dto.SajuApiRequest;
import com.example.demo.infra.sajuapi.dto.SajuApiResponse;
import com.example.demo.infra.sajuapi.mapper.SajuApiRequestMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SajuCalculationServiceTest {

    private SajuInputMapper sajuInputMapper;
    private SajuMapper sajuMapper;
    private SajuApiClient sajuApiClient;
    private SajuCalculationService service;

    @BeforeEach
    void setUp() {
        sajuInputMapper = mock(SajuInputMapper.class);
        sajuMapper = mock(SajuMapper.class);
        sajuApiClient = mock(SajuApiClient.class);
        service = new SajuCalculationService(
                sajuInputMapper,
                sajuMapper,
                new SajuApiRequestMapper(),
                sajuApiClient
        );
    }

    @Test
    void 저장된_입력으로_외부_API를_호출하고_결과를_저장한다() {
        SajuInput input = input();
        SajuApiResponse apiResponse = apiResponse();
        AtomicReference<Saju> saved = new AtomicReference<>();

        when(sajuInputMapper.findByUserId(2L)).thenReturn(Optional.of(input));
        when(sajuApiClient.calculate(any(SajuApiRequest.class))).thenReturn(apiResponse);
        when(sajuMapper.upsert(any(Saju.class))).thenAnswer(invocation -> {
            saved.set(invocation.getArgument(0));
            return 1;
        });
        when(sajuMapper.findByUserId(2L)).thenAnswer(invocation -> Optional.of(saved.get()));

        SajuResponse response = service.calculate(2L);

        ArgumentCaptor<SajuApiRequest> requestCaptor = ArgumentCaptor.forClass(SajuApiRequest.class);
        verify(sajuApiClient).calculate(requestCaptor.capture());
        assertThat(requestCaptor.getValue().birth_hour()).isEqualTo(2);
        assertThat(requestCaptor.getValue().birth_minute()).isEqualTo(30);
        assertThat(requestCaptor.getValue().gender()).isEqualTo("male");

        ArgumentCaptor<Saju> sajuCaptor = ArgumentCaptor.forClass(Saju.class);
        verify(sajuMapper).upsert(sajuCaptor.capture());
        assertThat(sajuCaptor.getValue().getYearStem()).isEqualTo("戊");
        assertThat(sajuCaptor.getValue().getDayBranch()).isEqualTo("酉");
        assertThat(sajuCaptor.getValue().getWoodCount()).isEqualTo(5.8);
        assertThat(sajuCaptor.getValue().getSajuAnimalName()).isNull();
        assertThat(response.userId()).isEqualTo(2L);
        assertThat(response.waterCount()).isEqualTo(1.0);
    }

    @Test
    void 사주_입력이_없으면_API를_호출하지_않고_404를_반환한다() {
        when(sajuInputMapper.findByUserId(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.calculate(2L))
                .isInstanceOf(ResponseStatusException.class)
                .satisfies(exception -> assertThat(
                        ((ResponseStatusException) exception).getStatusCode().value()
                ).isEqualTo(404));

        verify(sajuApiClient, never()).calculate(any(SajuApiRequest.class));
        verify(sajuMapper, never()).upsert(any(Saju.class));
    }

    @Test
    void 외부_API_호출이_실패하면_저장하지_않고_502를_반환한다() {
        when(sajuInputMapper.findByUserId(2L)).thenReturn(Optional.of(input()));
        when(sajuApiClient.calculate(any(SajuApiRequest.class)))
                .thenThrow(new ResourceAccessException("connection failed"));

        assertThatThrownBy(() -> service.calculate(2L))
                .isInstanceOf(ResponseStatusException.class)
                .satisfies(exception -> assertThat(
                        ((ResponseStatusException) exception).getStatusCode().value()
                ).isEqualTo(502));

        verify(sajuMapper, never()).upsert(any(Saju.class));
    }

    private SajuInput input() {
        return SajuInput.from(
                2L,
                LocalDate.of(2000, 9, 1),
                Gender.MALE,
                CalendarType.SOLAR,
                BirthTimeBranch.CHUK
        );
    }

    private SajuApiResponse apiResponse() {
        return new SajuApiResponse(
                new SajuApiResponse.FiveElements(5.8, 1.9, 1.3, 2.0, 1.0, "목", "수"),
                new SajuApiResponse.HourPillar("壬", "임", "寅", "인"),
                new SajuApiResponse.DayPillar("丁", "정", "酉", "유"),
                new SajuApiResponse.MonthPillar("甲", "갑", "寅", "인"),
                new SajuApiResponse.YearPillar("戊", "무", "寅", "인")
        );
    }
}

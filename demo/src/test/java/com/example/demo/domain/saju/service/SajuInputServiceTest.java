package com.example.demo.domain.saju.service;

import com.example.demo.domain.saju.dto.SajuInputRequest;
import com.example.demo.domain.saju.dto.SajuInputResponse;
import com.example.demo.domain.saju.entity.CalendarType;
import com.example.demo.domain.saju.entity.SajuInput;
import com.example.demo.domain.saju.repository.SajuInputMapper;
import com.example.demo.domain.user.entity.BirthTimeBranch;
import com.example.demo.domain.user.entity.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SajuInputServiceTest {

    private SajuInputMapper sajuInputMapper;
    private SajuInputService sajuInputService;

    @BeforeEach
    void setUp() {
        sajuInputMapper = mock(SajuInputMapper.class);
        sajuInputService = new SajuInputService(sajuInputMapper);
    }

    @Test
    void 기존_입력이_없으면_로그인_사용자_ID로_등록한다() {
        SajuInputRequest request = request();
        when(sajuInputMapper.findByUserId(2L))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(input(2L)));

        SajuInputResponse response = sajuInputService.saveOrUpdate(2L, request);

        verify(sajuInputMapper).insert(any(SajuInput.class));
        verify(sajuInputMapper, never()).updateByUserId(any(SajuInput.class));
        assertThat(response.userId()).isEqualTo(2L);
        assertThat(response.birthTimeBranch()).isEqualTo(BirthTimeBranch.JA);
    }

    @Test
    void 기존_입력이_있으면_로그인_사용자_ID의_정보를_수정한다() {
        SajuInput existing = input(2L);
        when(sajuInputMapper.findByUserId(2L)).thenReturn(Optional.of(existing));

        sajuInputService.saveOrUpdate(2L, request());

        verify(sajuInputMapper).updateByUserId(any(SajuInput.class));
        verify(sajuInputMapper, never()).insert(any(SajuInput.class));
    }

    @Test
    void 로그인_사용자의_사주_입력을_조회한다() {
        when(sajuInputMapper.findByUserId(2L)).thenReturn(Optional.of(input(2L)));

        SajuInputResponse response = sajuInputService.getByUserId(2L);

        assertThat(response.userId()).isEqualTo(2L);
        assertThat(response.gender()).isEqualTo(Gender.MALE);
        assertThat(response.calendarType()).isEqualTo(CalendarType.SOLAR);
    }

    private SajuInputRequest request() {
        return new SajuInputRequest(
                LocalDate.of(2000, 9, 1),
                BirthTimeBranch.JA,
                Gender.MALE,
                CalendarType.SOLAR
        );
    }

    private SajuInput input(Long userId) {
        return SajuInput.from(
                userId,
                LocalDate.of(2000, 9, 1),
                Gender.MALE,
                CalendarType.SOLAR,
                BirthTimeBranch.JA
        );
    }
}

package com.example.demo.domain.user.controller;

import com.example.demo.domain.saju.entity.CalendarType;
import com.example.demo.domain.saju.dto.SajuInputRequest;
import com.example.demo.domain.saju.service.SajuCalculationService;
import com.example.demo.domain.user.dto.SajuRegistrationStatus;
import com.example.demo.domain.user.dto.SignupRequest;
import com.example.demo.domain.user.dto.SignupResponse;
import com.example.demo.domain.user.entity.BirthTimeBranch;
import com.example.demo.domain.user.entity.Gender;
import com.example.demo.domain.user.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthControllerTest {

    private AuthService authService;
    private SajuCalculationService sajuCalculationService;
    private AuthController authController;

    @BeforeEach
    void setUp() {
        authService = mock(AuthService.class);
        sajuCalculationService = mock(SajuCalculationService.class);
        authController = new AuthController(authService, sajuCalculationService);
    }

    @Test
    void 사주정보가_없으면_계산하지_않고_회원가입만_완료한다() {
        SignupRequest request = new SignupRequest("tester", "password123!", "테스터", null);
        when(authService.signup(request)).thenReturn(pendingSignupResponse());

        ResponseEntity<SignupResponse> response = authController.signup(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().sajuStatus()).isEqualTo(SajuRegistrationStatus.NOT_PROVIDED);
        verify(sajuCalculationService, never()).calculate(1L);
    }

    @Test
    void 회원가입_후_사주_계산에_성공하면_완료_상태를_반환한다() {
        SignupRequest request = signupRequest();
        when(authService.signup(request)).thenReturn(pendingSignupResponse());

        ResponseEntity<SignupResponse> response = authController.signup(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().sajuStatus()).isEqualTo(SajuRegistrationStatus.CALCULATED);
        verify(sajuCalculationService).calculate(1L);
    }

    @Test
    void 사주_계산에_실패해도_회원가입은_성공하고_재시도_안내를_반환한다() {
        SignupRequest request = signupRequest();
        when(authService.signup(request)).thenReturn(pendingSignupResponse());
        doThrow(new RuntimeException("외부 API 오류"))
                .when(sajuCalculationService).calculate(1L);

        ResponseEntity<SignupResponse> response = authController.signup(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().sajuStatus()).isEqualTo(SajuRegistrationStatus.CALCULATION_FAILED);
        assertThat(response.getBody().sajuMessage()).contains("재시도");
    }

    private SignupRequest signupRequest() {
        return new SignupRequest(
                "tester",
                "password123!",
                "테스터",
                new SajuInputRequest(
                        LocalDate.of(1997, 3, 15),
                        BirthTimeBranch.JA,
                        Gender.MALE,
                        CalendarType.SOLAR
                )
        );
    }

    private SignupResponse pendingSignupResponse() {
        return new SignupResponse(
                1L,
                "tester",
                "테스터",
                "USER",
                SajuRegistrationStatus.NOT_PROVIDED,
                "사주 정보가 입력되지 않았습니다."
        );
    }
}

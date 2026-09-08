package com.example.demo.domain.user.controller;

import com.example.demo.domain.user.dto.LoginRequest;
import com.example.demo.domain.user.dto.LoginResponse;
import com.example.demo.domain.user.dto.LoginUserResponse;
import com.example.demo.domain.user.dto.BirthTimeOptionResponse;
import com.example.demo.domain.user.dto.SignupRequest;
import com.example.demo.domain.user.dto.SignupResponse;
import com.example.demo.domain.user.dto.SajuRegistrationStatus;
import com.example.demo.domain.user.entity.BirthTimeBranch;
import com.example.demo.domain.user.service.AuthService;
import com.example.demo.domain.saju.service.SajuCalculationService;
import com.example.demo.global.jwt.AuthenticatedUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final SajuCalculationService sajuCalculationService;

    @GetMapping("/birth-time-options")
    public ResponseEntity<List<BirthTimeOptionResponse>> birthTimeOptions() {
        List<BirthTimeOptionResponse> options = Arrays.stream(BirthTimeBranch.values())
                .map(BirthTimeOptionResponse::from)
                .toList();
        return ResponseEntity.ok(options);
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest request) {
        SignupResponse signupResponse = authService.signup(request);

        if (request.sajuInput() == null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(signupResponse);
        }

        try {
            sajuCalculationService.calculate(signupResponse.userId());
            signupResponse = signupResponse.withSajuStatus(
                    SajuRegistrationStatus.CALCULATED,
                    "사주 계산 완료"
            );
        } catch (RuntimeException exception) {
            log.warn("회원가입 후 사주 계산 실패. userId={}", signupResponse.userId(), exception);
            signupResponse = signupResponse.withSajuStatus(
                    SajuRegistrationStatus.CALCULATION_FAILED,
                    "회원가입은 완료됐지만 사주 계산에 실패했습니다. /api/saju/calculate로 재시도할 수 있습니다."
            );
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(signupResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<LoginUserResponse> me(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser
    ) {
        return ResponseEntity.ok(authService.getCurrentUser(authenticatedUser.userId()));
    }
}

package com.example.demo.domain.user.controller;

import com.example.demo.domain.user.dto.LoginRequest;
import com.example.demo.domain.user.dto.LoginResponse;
import com.example.demo.domain.user.dto.LoginUserResponse;
import com.example.demo.domain.user.dto.BirthTimeOptionResponse;
import com.example.demo.domain.user.dto.SignupRequest;
import com.example.demo.domain.user.dto.SignupResponse;
import com.example.demo.domain.user.entity.BirthTimeBranch;
import com.example.demo.domain.user.service.AuthService;
import com.example.demo.global.jwt.AuthenticatedUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
public class AuthController {

    private final AuthService authService;

    @GetMapping("/birth-time-options")
    public ResponseEntity<List<BirthTimeOptionResponse>> birthTimeOptions() {
        List<BirthTimeOptionResponse> options = Arrays.stream(BirthTimeBranch.values())
                .map(BirthTimeOptionResponse::from)
                .toList();
        return ResponseEntity.ok(options);
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(request));
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

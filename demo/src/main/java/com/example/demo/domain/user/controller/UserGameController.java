package com.example.demo.domain.user.controller;

import com.example.demo.domain.user.dto.UserGameCreateRequest;
import com.example.demo.domain.user.service.UserGameService;
import com.example.demo.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/me/games")
@RequiredArgsConstructor
@Tag(name = "선호 게임", description = "로그인한 사용자의 선호 게임 관리")
@SecurityRequirement(name = "bearerAuth")
public class UserGameController {

    private final UserGameService userGameService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "선호 게임 등록")
    public ApiResponse<Void> register(@Valid @RequestBody UserGameCreateRequest request) {
        userGameService.register(request);
        return ApiResponse.success();
    }
}

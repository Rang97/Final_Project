package com.example.demo.domain.fortune.controller;

import com.example.demo.domain.fortune.dto.FortuneResponse;
import com.example.demo.domain.fortune.service.FortuneService;
import com.example.demo.global.common.ApiResponse;
import com.example.demo.global.util.CurrentUserProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/me/fortunes")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class FortuneController {
    private final FortuneService service;
    private final CurrentUserProvider currentUserProvider;

    @PostMapping("/today")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "사주 미계산 또는 생성 중",
            content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = com.example.demo.common.exception.ErrorResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "502", description = "외부 API 또는 응답 검증 실패: code, message, errors 확인",
            content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = com.example.demo.common.exception.ErrorResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "503", description = "Gemini 설정 누락 또는 이전 생성 실패",
            content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = com.example.demo.common.exception.ErrorResponse.class)))
    })
    @Operation(summary = "게임 테마 오늘의 운세", description = "저장된 운세를 반환하거나 최초 요청에서 생성합니다. 생성 중에는 409, 실패 상태에서는 503을 반환하며 자동 재생성하지 않습니다.")
    public ApiResponse<FortuneResponse> today() {
        return ApiResponse.success(service.today(currentUserProvider.getCurrentUserId()));
    }
}

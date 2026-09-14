package com.example.demo.domain.user.controller;

import com.example.demo.domain.user.dto.UserGameResponse;
import com.example.demo.domain.user.service.UserGameService;
import com.example.demo.global.common.ApiResponse;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
@Tag(name = "게임", description = "게임 카탈로그 조회 (인증 불필요)")
public class GameCatalogController {

    private final UserGameService userGameService;

    @GetMapping
    @Operation(summary = "전체 게임 목록 조회")
    public ApiResponse<List<UserGameResponse>> getAllGames() {
        return ApiResponse.success(userGameService.getAllGames());
    }
}
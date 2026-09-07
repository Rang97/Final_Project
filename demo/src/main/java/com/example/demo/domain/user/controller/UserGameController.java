package com.example.demo.domain.user.controller;

import com.example.demo.domain.user.dto.UserGameCreateRequest;
import com.example.demo.domain.user.dto.UserGameResponse;
import java.util.List;
import com.example.demo.domain.user.service.UserGameService;
import com.example.demo.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping
    @Operation(summary = "내 선호 게임 조회")
    public ApiResponse<List<UserGameResponse>> getMyGames() {
        return ApiResponse.success(userGameService.getMyGames());
    }

    @PatchMapping("/{gameId}/main")
    @Operation(summary = "대표 게임 변경", description = "등록한 선호 게임 중 하나를 대표 게임으로 지정합니다.")
    public ApiResponse<Void> changeMainGame(@PathVariable Long gameId) {
        userGameService.changeMainGame(gameId);
        return ApiResponse.success();
    }

    @DeleteMapping("/{gameId}")
    @Operation(summary = "선호 게임 삭제", description = "대표 게임 삭제 시 다른 게임을 자동으로 대표 지정하지 않습니다.")
    public ApiResponse<Void> delete(@PathVariable Long gameId) {
        userGameService.delete(gameId);
        return ApiResponse.success();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "선호 게임 등록")
    public ApiResponse<Void> register(@Valid @RequestBody UserGameCreateRequest request) {
        userGameService.register(request);
        return ApiResponse.success();
    }
}

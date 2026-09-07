package com.example.demo.domain.user.controller;

import com.example.demo.domain.user.dto.UserGameCreateRequest;
import com.example.demo.domain.user.dto.UserGameResponse;
import java.util.List;
import com.example.demo.domain.user.service.UserGameService;
import com.example.demo.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "로그인 토큰이 없거나 유효하지 않음", content = @Content)
})
public class UserGameController {

    private final UserGameService userGameService;

    @GetMapping
    @Operation(summary = "내 선호 게임 조회", description = "로그인한 사용자의 선호 게임을 반환합니다. 대표 게임이 먼저 표시되며 등록 내역이 없으면 data는 빈 배열입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공")
    })
    public ApiResponse<List<UserGameResponse>> getMyGames() {
        return ApiResponse.success(userGameService.getMyGames());
    }

    @PatchMapping("/{gameId}/main")
    @Operation(summary = "대표 게임 변경", description = "등록한 선호 게임 중 하나를 대표 게임으로 지정합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "기존 대표 해제 및 새 대표 지정 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "사용자 또는 본인이 등록한 선호 게임이 없음", content = @Content)
    })
    public ApiResponse<Void> changeMainGame(
            @Parameter(description = "본인이 등록한 게임 ID", example = "1", required = true) @PathVariable Long gameId) {
        userGameService.changeMainGame(gameId);
        return ApiResponse.success();
    }

    @DeleteMapping("/{gameId}")
    @Operation(summary = "선호 게임 삭제", description = "대표 게임 삭제 시 다른 게임을 자동으로 대표 지정하지 않습니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "삭제 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "사용자 또는 본인이 등록한 선호 게임이 없음", content = @Content)
    })
    public ApiResponse<Void> delete(
            @Parameter(description = "삭제할 선호 게임의 게임 ID", example = "1", required = true) @PathVariable Long gameId) {
        userGameService.delete(gameId);
        return ApiResponse.success();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "선호 게임 등록", description = "game 테이블에 존재하는 gameId를 입력합니다. 사용자 ID는 JWT에서 가져오며 최대 5개까지 등록할 수 있습니다. 등록 시 대표 여부는 false입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "등록 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "gameId 누락 또는 양수가 아닌 값 등 잘못된 요청", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "사용자 또는 게임이 없음", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "이미 등록한 게임이거나 최대 5개 제한 초과", content = @Content)
    })
    public ApiResponse<Void> register(@Valid @RequestBody UserGameCreateRequest request) {
        userGameService.register(request);
        return ApiResponse.success();
    }
}

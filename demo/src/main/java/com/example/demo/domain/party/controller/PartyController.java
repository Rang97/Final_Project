package com.example.demo.domain.party.controller;

import com.example.demo.domain.party.dto.PartyCreateRequest;
import com.example.demo.domain.party.dto.PartyListResponse;
import com.example.demo.domain.party.entity.ChemistryType;
import com.example.demo.domain.party.entity.Party;
import com.example.demo.domain.party.entity.PartySortBy;
import com.example.demo.domain.party.entity.PartyStatus;
import com.example.demo.domain.party.service.PartyMemberService;
import com.example.demo.domain.party.service.PartyService;
import com.example.demo.global.jwt.AuthenticatedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/party")
@RequiredArgsConstructor
@Tag(name = "파티", description = "게임 파티 생성 및 파티원 관리 API")
@SecurityRequirement(name = "bearerAuth")
public class PartyController {

    private final PartyService partyService;
    private final PartyMemberService partyMemberService;

    // 파티 생성
    @PostMapping("/create")
    @Operation(summary = "파티 생성", description = "로그인한 사용자가 파티장이 되어 새로운 게임 파티를 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "파티 생성 성공"),
            @ApiResponse(responseCode = "400", description = "입력값 검증 실패", content = @Content),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content)
    })
    public ResponseEntity<Party> createParty(
            @Valid
            @RequestBody PartyCreateRequest request, // json 바디
            @Parameter(hidden = true) @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        Party party = partyService.createParty(authenticatedUser.userId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(party);
    }

    // 파티 삭제
    @DeleteMapping("/delete/{partyId}")
    @Operation(summary = "파티 삭제", description = "파티장이 자신이 만든 파티를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "파티 삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content),
            @ApiResponse(responseCode = "403", description = "파티장 권한 없음", content = @Content)
    })
    public ResponseEntity<Void> deleteParty(
            @Parameter(description = "삭제할 파티 ID", example = "1") @PathVariable Long partyId,
            @Parameter(hidden = true) @AuthenticationPrincipal AuthenticatedUser authenticatedUser
    ){
        partyService.deleteParty(authenticatedUser.userId(), partyId);
        return ResponseEntity.noContent().build();
    }

    // 파티원 추방
    @DeleteMapping("/{partyId}/kicked/{userId}")
    @Operation(summary = "파티원 추방", description = "파티장이 지정한 사용자를 파티에서 추방합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "파티원 추방 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content),
            @ApiResponse(responseCode = "403", description = "파티장 권한 없음", content = @Content)
    })
    public ResponseEntity<Void> deletePartyMember(
            @Parameter(description = "파티 ID", example = "1") @PathVariable Long partyId,
            @Parameter(description = "추방할 사용자 ID", example = "2") @PathVariable Long userId, // 추방 대상 id
            @Parameter(hidden = true) @AuthenticationPrincipal AuthenticatedUser authenticatedUser // 추방 요청

    ){
        partyService.deletePartyMember(authenticatedUser.userId(), partyId, userId);
        return ResponseEntity.noContent().build();
    }

    // 파티 가입
    @PostMapping("/{partyId}/join")
    @Operation(summary = "파티 가입", description = "로그인한 사용자가 모집 중인 파티에 가입합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "파티 가입 성공"),
            @ApiResponse(responseCode = "400", description = "가입 불가 또는 이미 가입한 파티", content = @Content),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content)
    })
    public ResponseEntity<Void> joinParty(
            @Parameter(description = "가입할 파티 ID", example = "1") @PathVariable Long partyId,
            @Parameter(hidden = true) @AuthenticationPrincipal AuthenticatedUser authenticatedUser
    ){
        partyMemberService.joinParty(partyId, authenticatedUser.userId());
        return ResponseEntity.ok().build();
    }

    // 파티 떠나기
    @PostMapping("/{partyId}/leave")
    @Operation(summary = "파티 탈퇴", description = "로그인한 사용자가 참여 중인 파티에서 나갑니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "파티 탈퇴 성공"),
            @ApiResponse(responseCode = "400", description = "참여 중인 파티가 아님", content = @Content),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content)
    })
    public ResponseEntity<Void> leaveParty(
            @Parameter(description = "탈퇴할 파티 ID", example = "1") @PathVariable Long partyId,
            @Parameter(hidden = true) @AuthenticationPrincipal AuthenticatedUser authenticatedUser
    ){
        partyMemberService.leaveParty(partyId, authenticatedUser.userId());
        return ResponseEntity.ok().build();
    }

    // 파티 단건 조회
    @GetMapping("/{partyId}")
    public ResponseEntity<Party> getParty (
            @PathVariable Long partyId
    ){
        Party party = partyService.getParty(partyId);
        return ResponseEntity.ok(party);
    }

    // 파티 목록 조회 (정렬 포함)
    @GetMapping("/party-list")
    @Operation(summary = "파티 목록 조회", description = "정렬 기준(sortBy)에 따라 파티 목록을 정렬해서 반환합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content)
    })
    public ResponseEntity<List<PartyListResponse>> getPartyList(
            @Parameter(description = "정렬 기준. 비우면 최신순", example = "MEMBER_COUNT")
            @RequestParam(required = false) PartySortBy sortBy,
            @Parameter(description = "오름차순 여부", example = "true")
            @RequestParam(required = false, defaultValue = "true") boolean ascending,
            @Parameter(hidden = true) @AuthenticationPrincipal AuthenticatedUser user
    ){
        List<PartyListResponse> parties = partyService.getPartyList(sortBy, ascending, user);
        return ResponseEntity.ok(parties);
    }




}

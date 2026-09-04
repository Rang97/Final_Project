package com.example.demo.domain.party.controller;

import com.example.demo.domain.party.dto.PartyCreateRequest;
import com.example.demo.domain.party.entity.Party;
import com.example.demo.domain.party.service.PartyMemberService;
import com.example.demo.domain.party.service.PartyService;
import com.example.demo.global.jwt.AuthenticatedUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/party")
@RequiredArgsConstructor
public class PartyController {

    private final PartyService partyService;
    private final PartyMemberService partyMemberService;

    // 파티 생성
    @PostMapping("/create")
    public ResponseEntity<Party> createParty(
            @Valid
            @RequestBody PartyCreateRequest request, // json 바디
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        Party party = partyService.createParty(authenticatedUser.userId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(party);
    }

    // 파티 삭제
    @DeleteMapping("/delete/{partyId}")
    public ResponseEntity<Void> deleteParty(
            @PathVariable Long partyId,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser
    ){
        partyService.deleteParty(authenticatedUser.userId(), partyId);
        return ResponseEntity.noContent().build();
    }

    // 파티원 추방
    @DeleteMapping("/{partyId}/kicked/{userId}")
    public ResponseEntity<Void> deletePartyMember(
            @PathVariable Long partyId,
            @PathVariable Long userId, // 추방 대상 id
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser // 추방 요청

    ){
        partyService.deletePartyMember(authenticatedUser.userId(), partyId, userId);
        return ResponseEntity.noContent().build();
    }

    // 파티 가입
    @PostMapping("/{partyId}/join")
    public ResponseEntity<Void> joinParty(
            @PathVariable Long partyId,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser
    ){
        partyMemberService.joinParty(partyId, authenticatedUser.userId());
        return ResponseEntity.ok().build();
    }

    // 파티 떠나기
    @PostMapping("/{partyId}/leave")
    public ResponseEntity<Void> leaveParty(
            @PathVariable Long partyId,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser
    ){
        partyMemberService.leaveParty(partyId, authenticatedUser.userId());
        return ResponseEntity.ok().build();
    }


}

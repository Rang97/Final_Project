package com.example.demo.domain.party.controller;

import com.example.demo.domain.party.dto.PartyCreateRequest;
import com.example.demo.domain.party.entity.Party;
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

    // 파티 생성
    @PostMapping("/create")
    public ResponseEntity<Party> createParty(
            @Valid
            @RequestBody PartyCreateRequest request, // json 바디
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        Party party = partyService.createParty(authenticatedUser.userId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(party);
    }
}

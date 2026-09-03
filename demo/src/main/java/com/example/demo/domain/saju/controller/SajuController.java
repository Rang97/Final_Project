package com.example.demo.domain.saju.controller;

import com.example.demo.domain.saju.dto.SajuInputRequest;
import com.example.demo.domain.saju.dto.SajuInputResponse;
import com.example.demo.domain.saju.service.SajuInputService;
import com.example.demo.global.jwt.AuthenticatedUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/saju")
@RequiredArgsConstructor
public class SajuController {

    private final SajuInputService sajuInputService;

    @PutMapping("/input")
    public ResponseEntity<SajuInputResponse> saveOrUpdate(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @Valid @RequestBody SajuInputRequest request
    ) {
        return ResponseEntity.ok(
                sajuInputService.saveOrUpdate(authenticatedUser.userId(), request)
        );
    }

    @GetMapping("/input")
    public ResponseEntity<SajuInputResponse> getInput(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser
    ) {
        return ResponseEntity.ok(
                sajuInputService.getByUserId(authenticatedUser.userId())
        );
    }
}

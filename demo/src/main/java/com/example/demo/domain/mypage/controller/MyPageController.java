package com.example.demo.domain.mypage.controller;

import com.example.demo.domain.mypage.dto.MyPageSajuResponse;
import com.example.demo.domain.mypage.dto.MyPageSummaryResponse;
import com.example.demo.domain.mypage.service.MyPageService;
import com.example.demo.domain.saju.dto.SajuInputRequest;
import com.example.demo.domain.saju.dto.SajuInputResponse;
import com.example.demo.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @PutMapping("/saju-input")
    public ApiResponse<SajuInputResponse> updateBirthInfo(@Valid @RequestBody SajuInputRequest request) {
        return ApiResponse.success(myPageService.updateBirthInfo(request));
    }

    @PostMapping("/saju/calculate")
    public ApiResponse<MyPageSajuResponse> calculateSaju() {
        return ApiResponse.success(myPageService.calculateSaju());
    }

    @GetMapping("/saju")
    public ApiResponse<MyPageSajuResponse> getMySaju() {
        return ApiResponse.success(myPageService.getMySaju());
    }

    @GetMapping("/summary")
    public ApiResponse<MyPageSummaryResponse> getSummary() {
        return ApiResponse.success(myPageService.getMyPageSummary());
    }
}
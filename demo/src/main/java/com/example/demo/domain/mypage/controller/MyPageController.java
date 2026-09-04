// domain/mypage/controller/MyPageController.java
package com.example.demo.domain.mypage.controller;

import com.example.demo.domain.mypage.dto.MyPageSajuResponse;
import com.example.demo.domain.mypage.service.MyPageService;
import com.example.demo.domain.saju.dto.SajuInputRequest;
import com.example.demo.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/saju")
    public ApiResponse<MyPageSajuResponse> getMySaju() {
        return ApiResponse.success(myPageService.getMySaju());
    }

    @PutMapping("/saju")
    public ApiResponse<MyPageSajuResponse> updateBirthInfo(@Valid @RequestBody SajuInputRequest request) {
        return ApiResponse.success(myPageService.updateBirthInfo(request));
    }
}
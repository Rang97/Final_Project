package com.example.demo.domain.saju.controller;

import com.example.demo.infra.sajuapi.SajuApiClient;
import com.example.demo.infra.sajuapi.dto.SajuApiRequest;
import com.example.demo.infra.sajuapi.dto.SajuApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/saju")
@RequiredArgsConstructor
public class SajuCalculationController {

    private final SajuApiClient sajuApiClient;

    @PostMapping("/calculate")
    public SajuApiResponse calculateSaju(@RequestBody SajuApiRequest request) {
        return sajuApiClient.calculate(request);
    }
}

package com.example.demo.domain.saju.service;

import com.example.demo.domain.saju.dto.SajuResponse;
import com.example.demo.domain.saju.entity.Saju;
import com.example.demo.domain.saju.entity.SajuInput;
import com.example.demo.domain.saju.repository.SajuInputMapper;
import com.example.demo.domain.saju.repository.SajuMapper;
import com.example.demo.infra.sajuapi.SajuApiClient;
import com.example.demo.infra.sajuapi.dto.SajuApiRequest;
import com.example.demo.infra.sajuapi.dto.SajuApiResponse;
import com.example.demo.infra.sajuapi.mapper.SajuApiRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class SajuCalculationService {

    private final SajuInputMapper sajuInputMapper;
    private final SajuMapper sajuMapper;
    private final SajuApiRequestMapper sajuApiRequestMapper;
    private final SajuApiClient sajuApiClient;

    @Transactional
    public SajuResponse calculate(Long userId) {
        SajuInput input = sajuInputMapper.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "등록된 사주 입력정보가 없습니다."
                ));

        SajuApiRequest request = sajuApiRequestMapper.from(input);
        SajuApiResponse apiResponse;

        try {
            apiResponse = sajuApiClient.calculate(request);
        } catch (RestClientException exception) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "외부 사주 API 호출에 실패했습니다.",
                    exception
            );
        }

        validateResponse(apiResponse);

        Saju saju = Saju.from(userId, input, apiResponse);
        sajuMapper.upsert(saju);

        return SajuResponse.from(
                sajuMapper.findByUserId(userId)
                        .orElseThrow(() -> new IllegalStateException("사주 결과 저장에 실패했습니다."))
        );
    }

    private void validateResponse(SajuApiResponse response) {
        if (response == null
                || response.five_elements() == null
                || response.year_pillar() == null
                || response.month_pillar() == null
                || response.day_pillar() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "외부 사주 API 응답이 올바르지 않습니다."
            );
        }
    }
}

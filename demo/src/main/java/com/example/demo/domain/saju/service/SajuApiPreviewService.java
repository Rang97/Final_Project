package com.example.demo.domain.saju.service;

import com.example.demo.domain.saju.entity.SajuInput;
import com.example.demo.domain.saju.repository.SajuInputMapper;
import com.example.demo.infra.sajuapi.dto.SajuApiRequest;
import com.example.demo.infra.sajuapi.mapper.SajuApiRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class SajuApiPreviewService {

    private final SajuInputMapper sajuInputMapper;
    private final SajuApiRequestMapper sajuApiRequestMapper;

    @Transactional(readOnly = true)
    public SajuApiRequest preview(Long userId) {
        SajuInput input = sajuInputMapper.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "등록된 사주 입력정보가 없습니다."
                ));

        return sajuApiRequestMapper.from(input);
    }
}

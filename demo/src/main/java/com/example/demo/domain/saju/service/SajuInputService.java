package com.example.demo.domain.saju.service;

import com.example.demo.domain.saju.dto.SajuInputRequest;
import com.example.demo.domain.saju.dto.SajuInputResponse;
import com.example.demo.domain.saju.entity.SajuInput;
import com.example.demo.domain.saju.repository.SajuInputMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class SajuInputService {

    private final SajuInputMapper sajuInputMapper;

    @Transactional
    public SajuInputResponse saveOrUpdate(Long userId, SajuInputRequest request) {
        SajuInput input = SajuInput.from(
                userId,
                request.birthDate(),
                request.gender(),
                request.calendarType(),
                request.birthTimeBranch()
        );

        if (sajuInputMapper.findByUserId(userId).isPresent()) {
            sajuInputMapper.updateByUserId(input);
        } else {
            sajuInputMapper.insert(input);
        }

        return SajuInputResponse.from(
                sajuInputMapper.findByUserId(userId)
                        .orElseThrow(() -> new IllegalStateException("사주 입력정보 저장에 실패했습니다."))
        );
    }

    @Transactional(readOnly = true)
    public SajuInputResponse getByUserId(Long userId) {
        SajuInput input = sajuInputMapper.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "등록된 사주 입력정보가 없습니다."
                ));

        return SajuInputResponse.from(input);
    }
}

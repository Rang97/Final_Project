package com.example.demo.domain.fortune.service;

import com.example.demo.domain.fortune.repository.FortuneMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class FortuneStore {
    private final FortuneMapper mapper;

    @Transactional
    public void complete(Long userId, LocalDate date, String json) {
        if (mapper.finish(userId, date, "SUCCEEDED") != 1)
            throw new IllegalStateException("운세 생성 상태가 변경되었습니다.");
        mapper.save(userId, date, json);
    }
}


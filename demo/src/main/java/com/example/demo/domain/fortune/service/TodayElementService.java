package com.example.demo.domain.fortune.service;

import com.example.demo.domain.fortune.dto.FortuneRequest.Today;
import com.example.demo.domain.fortune.exception.FortuneException;
import com.example.demo.domain.saju.util.FiveElement;
import com.example.demo.infra.sajuapi.SajuApiClient;
import com.example.demo.infra.sajuapi.dto.SajuApiRequest;
import com.example.demo.infra.sajuapi.dto.SajuApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class TodayElementService {
    private final SajuApiClient client;
    private Today cached;

    // A bounded, single-date cache. Only the day pillar is used, never the synthetic birth profile.
    public synchronized Today get(LocalDate date) {
        if (cached != null && cached.date().equals(date)) return cached;
        SajuApiResponse response;
        try {
            response = client.calculate(new SajuApiRequest(date.getYear(), date.getMonthValue(),
                    date.getDayOfMonth(), null, null, false, "male"));
        } catch (RestClientException e) {
            throw FortuneException.upstream("SAJU", "TODAY_ELEMENT_CALL", e);
        }
        if (response == null || response.day_pillar() == null)
            throw FortuneException.invalid("TODAY_PILLAR_MISSING",
                    "사주 API 응답에 오늘의 day_pillar가 없습니다.", "TODAY_ELEMENT_RESPONSE");
        var pillar = response.day_pillar();
        List<FiveElement> elements;
        try {
            elements = Stream.of(FortuneAnalysisService.stemElement(pillar.stem()),
                    FortuneAnalysisService.branchElement(pillar.branch())).distinct().toList();
        } catch (ResponseStatusException e) {
            throw FortuneException.invalid("TODAY_PILLAR_INVALID",
                    "오늘의 천간·지지 값이 지원하는 형식이 아닙니다.", "TODAY_ELEMENT_RESPONSE");
        }
        cached = new Today(date, pillar.stem(), pillar.branch(), elements);
        return cached;
    }
}

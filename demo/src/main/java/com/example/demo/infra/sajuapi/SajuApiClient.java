package com.example.demo.infra.sajuapi;

import com.example.demo.infra.sajuapi.dto.SajuApiRequest;
import com.example.demo.infra.sajuapi.dto.SajuApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
@RequiredArgsConstructor
public class SajuApiClient {

    private final RestTemplate restTemplate;

    // application.yml 값들 호출
    @Value("${saju.api.base-url}")
    private String baseUrl;

    @Value("${saju.api.key}")
    private String apiKey;

    // API 호출 (생년월일시 호출)
    public SajuApiResponse calculate(SajuApiRequest requestBody) {
        // 요청 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 요청 body + 헤더 객체
        HttpEntity<SajuApiRequest> entity = new HttpEntity<>(requestBody, headers);

        // HTTP POST 요청
        ResponseEntity<SajuApiResponse> response = restTemplate.postForEntity
                        // 어디로 보낼지
                        (baseUrl + "/v1/saju/calculate",
                                // body + 헤더 상자
                                entity,
                                // 응답 JSON 타입 설정
                                SajuApiResponse.class);

        // 데이터 반환
        return response.getBody();
    }

}

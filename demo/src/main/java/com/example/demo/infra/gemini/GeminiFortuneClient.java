package com.example.demo.infra.gemini;

import com.example.demo.domain.fortune.dto.FortuneRequest;
import com.example.demo.domain.fortune.exception.FortuneException;
import com.example.demo.domain.fortune.service.FortuneJsonCodec;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class GeminiFortuneClient {
    private final RestTemplate http;
    private final ObjectMapper mapper;
    private final FortuneJsonCodec codec;
    private final String apiKey;
    private final String model;
    private final String baseUrl;
    private final String systemPrompt;

    public GeminiFortuneClient(ObjectMapper mapper, FortuneJsonCodec codec,
            @Value("${gemini.api.key:}") String apiKey,
            @Value("${gemini.api.model}") String model,
            @Value("${gemini.api.base-url}") String baseUrl,
            @Value("${gemini.api.connect-timeout-ms:5000}") int connectTimeout,
            @Value("${gemini.api.read-timeout-ms:60000}") int readTimeout) throws IOException {
        this.mapper = mapper;
        this.codec = codec;
        this.apiKey = apiKey;
        this.model = model;
        this.baseUrl = baseUrl;
        var factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(connectTimeout);
        factory.setReadTimeout(readTimeout);
        http = new RestTemplate(factory);
        systemPrompt = new ClassPathResource("prompts/fortune-system.txt").getContentAsString(StandardCharsets.UTF_8);
    }

    public void requireConfigured() {
        if (apiKey == null || apiKey.isBlank())
            throw new FortuneException(HttpStatus.SERVICE_UNAVAILABLE, "GEMINI_NOT_CONFIGURED",
                    "GEMINI_API_KEY 설정과 .env 로딩을 확인해 주세요.", Map.of("stage", "GEMINI_CONFIG"));
    }

    public String generate(FortuneRequest request) {
        requireConfigured();
        try {
            var body = Map.of(
                    "systemInstruction", Map.of("parts", List.of(Map.of("text", systemPrompt))),
                    "contents", List.of(Map.of("role", "user", "parts", List.of(Map.of("text",
                            "다음 데이터를 기반으로 오늘의 운세를 생성하세요. 반드시 지정된 JSON 응답 형식만 반환하세요.\n"
                                    + mapper.writeValueAsString(request))))),
                    "generationConfig", Map.of("responseMimeType", "application/json",
                            "responseJsonSchema", codec.schema(), "candidateCount", 1, "maxOutputTokens", 8192));
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-goog-api-key", apiKey);
            JsonNode response = http.postForObject(baseUrl + "/models/{model}:generateContent",
                    new HttpEntity<>(body, headers), JsonNode.class, model);
            if (response == null) throw invalidResponse();
            var candidates = response.path("candidates");
            if (!candidates.isArray() || candidates.size() != 1) throw invalidResponse();
            if (!"STOP".equals(candidates.get(0).path("finishReason").asText())) {
                boolean truncated = "MAX_TOKENS".equals(candidates.get(0).path("finishReason").asText());
                throw FortuneException.invalid(truncated ? "GEMINI_OUTPUT_TRUNCATED" : "GEMINI_OUTPUT_INCOMPLETE",
                        truncated ? "Gemini 응답이 출력 토큰 한도에서 중단됐습니다." : "Gemini 응답이 정상 완료되지 않았습니다.",
                        "GEMINI_RESPONSE");
            }
            var parts = candidates.get(0).path("content").path("parts");
            if (!parts.isArray()) throw invalidResponse();
            StringBuilder text = new StringBuilder();
            for (var part : parts) {
                if (!part.path("thought").asBoolean(false) && part.path("text").isTextual())
                    text.append(part.get("text").textValue());
            }
            if (text.isEmpty()) throw invalidResponse();
            return text.toString();
        } catch (RestClientException e) {
            throw FortuneException.upstream("GEMINI", "GEMINI_CALL", e);
        } catch (IOException e) {
            throw FortuneException.invalid("GEMINI_REQUEST_SERIALIZATION_ERROR",
                    "Gemini 요청 JSON 생성에 실패했습니다.", "GEMINI_REQUEST");
        }
    }

    private ResponseStatusException invalidResponse() {
        return FortuneException.invalid("GEMINI_INVALID_RESPONSE",
                "Gemini가 완성된 운세 텍스트를 반환하지 않았습니다.", "GEMINI_RESPONSE");
    }
}

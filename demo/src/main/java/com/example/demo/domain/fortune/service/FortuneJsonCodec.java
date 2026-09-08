package com.example.demo.domain.fortune.service;

import com.example.demo.domain.fortune.dto.FortuneResponse;
import com.example.demo.domain.fortune.exception.FortuneException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Component
public class FortuneJsonCodec {
    private final ObjectMapper mapper;
    private final JsonNode schema;

    public FortuneJsonCodec(ObjectMapper mapper) throws IOException {
        this.mapper = mapper.copy().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)
                .enable(JsonParser.Feature.STRICT_DUPLICATE_DETECTION);
        try (var input = new ClassPathResource("prompts/fortune-response.schema.json").getInputStream()) {
            schema = this.mapper.readTree(input);
        }
    }

    public JsonNode schema() { return schema.deepCopy(); }

    public FortuneResponse decode(String json, LocalDate date, List<Long> expectedIds) {
        try {
            if (json == null || json.length() > 50000) throw new IllegalArgumentException();
            JsonNode root = mapper.readTree(json);
            validate(root, schema);
            var response = mapper.treeToValue(root, FortuneResponse.class);
            if (!date.equals(response.date())) throw FortuneException.invalid("FORTUNE_DATE_MISMATCH",
                    "운세 응답 날짜가 요청 날짜와 다릅니다.", "RESPONSE_VALIDATION");
            var ids = response.gameFortunes().stream().map(FortuneResponse.GameFortune::gameId).toList();
            if (new HashSet<>(ids).size() != ids.size()) throw FortuneException.invalid("FORTUNE_DUPLICATE_GAME",
                    "운세 응답에 중복 gameId가 있습니다.", "RESPONSE_VALIDATION");
            if (expectedIds != null && (ids.size() != expectedIds.size()
                    || !new HashSet<>(ids).equals(new HashSet<>(expectedIds))))
                throw FortuneException.invalid("FORTUNE_GAME_MISMATCH",
                        "운세 응답의 게임 목록이 요청 목록과 다릅니다.", "RESPONSE_VALIDATION");
            return response;
        } catch (IOException | IllegalArgumentException e) {
            throw FortuneException.invalid("FORTUNE_INVALID_JSON",
                    "운세 JSON 형식·필수 필드·점수 범위가 올바르지 않습니다.", "RESPONSE_VALIDATION");
        }
    }

    // This validator handles every keyword used in our small, fixed response schema.
    private void validate(JsonNode value, JsonNode spec) {
        if (value == null || value.isNull()) throw new IllegalArgumentException();
        switch (spec.path("type").asText()) {
            case "object" -> {
                if (!value.isObject()) throw new IllegalArgumentException();
                JsonNode properties = spec.get("properties");
                if (value.size() != properties.size()) throw new IllegalArgumentException();
                properties.fields().forEachRemaining(e -> validate(value.get(e.getKey()), e.getValue()));
            }
            case "array" -> {
                if (!value.isArray() || value.size() > spec.path("maxItems").asInt(Integer.MAX_VALUE))
                    throw new IllegalArgumentException();
                value.forEach(v -> validate(v, spec.get("items")));
            }
            case "string" -> {
                if (!value.isTextual() || value.textValue().isBlank() || value.textValue().length() > 2000)
                    throw new IllegalArgumentException();
            }
            case "integer" -> {
                if (!value.isIntegralNumber() || !value.canConvertToLong()
                        || value.longValue() < spec.path("minimum").asLong(Long.MIN_VALUE)
                        || value.longValue() > spec.path("maximum").asLong(Long.MAX_VALUE))
                    throw new IllegalArgumentException();
            }
            default -> throw new IllegalStateException("Unknown schema type");
        }
    }

    public String encode(FortuneResponse response) {
        try { return mapper.writeValueAsString(response); }
        catch (IOException e) { throw new IllegalStateException("운세 직렬화 실패", e); }
    }
}

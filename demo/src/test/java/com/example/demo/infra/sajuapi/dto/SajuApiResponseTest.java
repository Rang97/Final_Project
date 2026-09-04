package com.example.demo.infra.sajuapi.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SajuApiResponseTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void 외부_API_JSON에서_저장에_필요한_값을_추출한다() throws Exception {
        String json = """
                {
                  "five_elements": {
                    "wood": 5.8,
                    "fire": 1.9,
                    "earth": 1.3,
                    "metal": 2,
                    "water": 1,
                    "dominant": "목",
                    "weakest": "수",
                    "percentages": {"wood": 48.3}
                  },
                  "hour_pillar": {
                    "stem": "壬",
                    "stem_korean": "임",
                    "branch": "寅",
                    "branch_korean": "인",
                    "stem_element": "수"
                  },
                  "day_pillar": {
                    "stem": "丁",
                    "stem_korean": "정",
                    "branch": "酉",
                    "branch_korean": "유"
                  },
                  "month_pillar": {
                    "stem": "甲",
                    "stem_korean": "갑",
                    "branch": "寅",
                    "branch_korean": "인"
                  },
                  "year_pillar": {
                    "stem": "戊",
                    "stem_korean": "무",
                    "branch": "寅",
                    "branch_korean": "인"
                  }
                }
                """;

        SajuApiResponse response = objectMapper.readValue(json, SajuApiResponse.class);

        assertThat(response.five_elements().wood()).isEqualTo(5.8);
        assertThat(response.year_pillar().stem()).isEqualTo("戊");
        assertThat(response.day_pillar().branch()).isEqualTo("酉");
        assertThat(response.hour_pillar().stem_korean()).isEqualTo("임");
    }
}

package com.example.demo.infra.sajuapi.dto;

import com.example.demo.domain.user.entity.Gender;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SajuApiRequestTest {

    @Test
    void 내부_남성_값을_외부_API_형식으로_변환한다() {
        SajuApiRequest request = createRequest(Gender.MALE);

        assertThat(request.gender()).isEqualTo("male");
    }

    @Test
    void 내부_여성_값을_외부_API_형식으로_변환한다() {
        SajuApiRequest request = createRequest(Gender.FEMALE);

        assertThat(request.gender()).isEqualTo("female");
    }

    private SajuApiRequest createRequest(Gender gender) {
        return SajuApiRequest.of(
                2000,
                9,
                1,
                0,
                0,
                false,
                gender
        );
    }
}

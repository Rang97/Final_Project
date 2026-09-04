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

    @Test
    void 출생_시간을_모르면_시와_분을_null로_생성할_수_있다() {
        SajuApiRequest request = SajuApiRequest.of(
                2000,
                9,
                1,
                null,
                null,
                false,
                Gender.MALE
        );

        assertThat(request.birth_hour()).isNull();
        assertThat(request.birth_minute()).isNull();
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

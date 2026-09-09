package com.example.demo.domain.saju.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SajuAnimalNameGeneratorTest {

    @ParameterizedTest
    @CsvSource({
            "甲, 子, 푸른 쥐",
            "丁, 酉, 붉은 닭",
            "己, 卯, 노란 토끼",
            "庚, 辰, 하얀 용",
            "癸, 亥, 검은 돼지",
            "기, 묘, 노란 토끼"
    })
    void 일주의_천간과_지지를_색상과_동물_이름으로_변환한다(
            String dayStem,
            String dayBranch,
            String expected
    ) {
        assertThat(SajuAnimalNameGenerator.generate(dayStem, dayBranch)).isEqualTo(expected);
    }

    @Test
    void 지원하지_않는_일주는_예외를_발생시킨다() {
        assertThatThrownBy(() -> SajuAnimalNameGenerator.generate("X", "卯"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("지원하지 않는 일주");
    }
}

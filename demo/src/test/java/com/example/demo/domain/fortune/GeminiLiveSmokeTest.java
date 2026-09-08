package com.example.demo.domain.fortune;

import com.example.demo.domain.fortune.dto.FortuneRequest;
import com.example.demo.domain.fortune.service.FortuneJsonCodec;
import com.example.demo.domain.saju.util.FiveElement;
import com.example.demo.domain.saju.util.FiveElementProfile;
import com.example.demo.infra.gemini.GeminiFortuneClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import java.time.LocalDate;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

/** Explicit opt-in only: one billable generation using synthetic data, no application DB. */
@EnabledIfEnvironmentVariable(named = "GEMINI_LIVE_TEST", matches = "true")
class GeminiLiveSmokeTest {
    @Test void generatesValidFortuneUsingConfiguredDefaultModel() throws Exception {
        var mapper = new ObjectMapper().findAndRegisterModules()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        var codec = new FortuneJsonCodec(mapper);
        var yaml = new org.yaml.snakeyaml.Yaml();
        String model;
        try (var stream = getClass().getResourceAsStream("/application.yml")) {
            java.util.Map<?, ?> config = yaml.load(stream);
            var api = (java.util.Map<?, ?>) ((java.util.Map<?, ?>) config.get("gemini")).get("api");
            String setting = api.get("model").toString();
            model = setting.substring(setting.indexOf(':') + 1, setting.length() - 1);
        }
        var client = new GeminiFortuneClient(mapper, codec, System.getenv("GEMINI_API_KEY"),
                model, "https://generativelanguage.googleapis.com/v1beta", 5000, 60000);
        var date = LocalDate.of(2026, 9, 8);
        var request = new FortuneRequest(
                new FortuneRequest.UserSaju("갑", List.of(FiveElement.WOOD), List.of(FiveElement.WATER),
                        new FiveElementProfile(3, 2, 2, 2, 1)),
                new FortuneRequest.Today(date, "갑", "자", List.of(FiveElement.WOOD, FiveElement.WATER)),
                new FortuneRequest.Analysis(List.of("준비와 협력에 집중하세요."), List.of("무리한 확장보다 페이스를 유지하세요.")),
                List.of(new FortuneRequest.FavoriteGame(15L, "테스트 게임", List.of("MOBA", "TEAM"))));
        var response = codec.decode(client.generate(request), date, List.of(15L));
        assertThat(response.overallFortune().score()).isBetween(0, 100);
        assertThat(response.gameFortunes()).hasSize(1);
    }
}

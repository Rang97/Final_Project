package com.example.demo.domain.fortune;

import com.example.demo.domain.fortune.service.FortuneJsonCodec;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.server.ResponseStatusException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

class FortuneJsonCodecTest {
    final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
    FortuneJsonCodec codec;
    String valid;
    final LocalDate date = LocalDate.of(2026, 9, 8);

    @BeforeEach void setup() throws Exception {
        codec = new FortuneJsonCodec(mapper);
        valid = new ClassPathResource("fortune-valid.json").getContentAsString(StandardCharsets.UTF_8);
    }
    @Test void validRoundTrip() {
        var response = codec.decode(valid, date, List.of(15L));
        assertThat(codec.decode(codec.encode(response), date, List.of(15L))).isEqualTo(response);
    }
    @Test void rejectsInvalidScoresWithoutCoercion() {
        for (String score : List.of("-1", "101", "70.5", "\"70\"", "null")) {
            assertThatThrownBy(() -> codec.decode(valid.replace("\"score\": 70", "\"score\": " + score), date, List.of(15L)))
                    .isInstanceOf(ResponseStatusException.class);
        }
    }
    @Test void rejectsMissingExtraAndDuplicateGames() throws Exception {
        assertThatThrownBy(() -> codec.decode(valid, date, List.of(16L))).isInstanceOf(ResponseStatusException.class);
        ObjectNode tree = (ObjectNode) mapper.readTree(valid);
        tree.withArray("gameFortunes").add(tree.withArray("gameFortunes").get(0).deepCopy());
        assertThatThrownBy(() -> codec.decode(tree.toString(), date, List.of(15L))).isInstanceOf(ResponseStatusException.class);
    }
    @Test void rejectsWrongDateTrailingDataUnknownAndMissingFields() throws Exception {
        assertThatThrownBy(() -> codec.decode(valid, date.plusDays(1), List.of(15L))).isInstanceOf(ResponseStatusException.class);
        assertThatThrownBy(() -> codec.decode(valid + " {}", date, List.of(15L))).isInstanceOf(ResponseStatusException.class);
        ObjectNode tree = (ObjectNode) mapper.readTree(valid);
        tree.put("extra", "value");
        assertThatThrownBy(() -> codec.decode(tree.toString(), date, List.of(15L))).isInstanceOf(ResponseStatusException.class);
        tree.remove("extra");
        tree.remove("dailyQuest");
        assertThatThrownBy(() -> codec.decode(tree.toString(), date, List.of(15L))).isInstanceOf(ResponseStatusException.class);
    }
    @Test void supportsNoFavoriteGames() throws Exception {
        ObjectNode tree = (ObjectNode) mapper.readTree(valid);
        tree.putArray("gameFortunes");
        assertThat(codec.decode(tree.toString(), date, List.of()).gameFortunes()).isEmpty();
    }
}


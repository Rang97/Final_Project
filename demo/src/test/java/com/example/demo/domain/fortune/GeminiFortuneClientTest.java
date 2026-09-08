package com.example.demo.domain.fortune;

import com.example.demo.domain.fortune.dto.FortuneRequest;
import com.example.demo.domain.fortune.service.FortuneJsonCodec;
import com.example.demo.infra.gemini.GeminiFortuneClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

class GeminiFortuneClientTest {
    GeminiFortuneClient client;
    MockRestServiceServer server;
    final FortuneRequest input = new FortuneRequest(null, null, null, List.of());

    @BeforeEach void setup() throws Exception {
        var mapper = new ObjectMapper().findAndRegisterModules();
        client = new GeminiFortuneClient(mapper, new FortuneJsonCodec(mapper), "test-key",
                "test-model", "https://example.test/v1beta", 1000, 1000);
        server = MockRestServiceServer.bindTo((RestTemplate) ReflectionTestUtils.getField(client, "http")).build();
    }
    @Test void usesHeaderSchemaAndSingleCallAndSkipsThoughts() {
        server.expect(requestTo("https://example.test/v1beta/models/test-model:generateContent"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header("x-goog-api-key", "test-key"))
                .andExpect(jsonPath("$.generationConfig.responseMimeType").value("application/json"))
                .andExpect(jsonPath("$.generationConfig.responseJsonSchema.properties.overallFortune").exists())
                .andExpect(jsonPath("$.systemInstruction.parts[0].text").isNotEmpty())
                .andRespond(withSuccess("""
                    {"candidates":[{"finishReason":"STOP","content":{"parts":[
                    {"thought":true,"text":"internal"},{"text":"{\"ok\":true}"}]}}]}
                    """.replace("{\"ok\":true}", "{\\\"ok\\\":true}"), MediaType.APPLICATION_JSON));
        assertThat(client.generate(input)).isEqualTo("{\"ok\":true}");
        server.verify();
    }
    @Test void rejectsTruncatedResponse() {
        server.expect(anything()).andRespond(withSuccess(
                "{\"candidates\":[{\"finishReason\":\"MAX_TOKENS\",\"content\":{\"parts\":[{\"text\":\"{}\"}]}}]}",
                MediaType.APPLICATION_JSON));
        assertThatThrownBy(() -> client.generate(input)).isInstanceOf(ResponseStatusException.class);
        server.verify();
    }
    @Test void upstreamFailureDoesNotRetryOrLeakBody() {
        server.expect(anything()).andRespond(withStatus(HttpStatus.TOO_MANY_REQUESTS).body("test-key private content"));
        assertThatThrownBy(() -> client.generate(input)).isInstanceOf(ResponseStatusException.class)
                .hasMessageNotContaining("test-key").hasMessageNotContaining("private content");
        server.verify();
    }
}


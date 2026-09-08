package com.example.demo.domain.fortune;

import com.example.demo.domain.fortune.controller.FortuneController;
import com.example.demo.domain.fortune.service.FortuneService;
import com.example.demo.domain.fortune.dto.FortuneResponse;
import com.example.demo.global.jwt.JwtProvider;
import com.example.demo.global.jwt.SecurityConfig;
import com.example.demo.global.util.CurrentUserProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FortuneController.class)
@Import(SecurityConfig.class)
class FortuneControllerTest {
    @Test void returnsSafeUpstreamDiagnostics() throws Exception {
        when(currentUser.getCurrentUserId()).thenReturn(12L);
        var upstream = org.springframework.web.client.HttpClientErrorException.create(
                org.springframework.http.HttpStatus.TOO_MANY_REQUESTS, "secret-key",
                org.springframework.http.HttpHeaders.EMPTY,
                "secret-key private body".getBytes(java.nio.charset.StandardCharsets.UTF_8),
                java.nio.charset.StandardCharsets.UTF_8);
        when(service.today(12L)).thenThrow(
                com.example.demo.domain.fortune.exception.FortuneException.upstream("GEMINI", "GEMINI_CALL", upstream));
        mvc.perform(post("/api/users/me/fortunes/today").with(user("test")))
                .andExpect(status().isBadGateway())
                .andExpect(jsonPath("$.code").value("GEMINI_HTTP_ERROR"))
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.errors.stage").value("GEMINI_CALL"))
                .andExpect(jsonPath("$.errors.upstreamStatus").value("429"))
                .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("secret-key"))))
                .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("private body"))));
    }
    @Test void returnsStatusReasonWithoutDefaultErrorDispatch() throws Exception {
        when(currentUser.getCurrentUserId()).thenReturn(12L);
        when(service.today(12L)).thenThrow(new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.CONFLICT, "사주 계산을 먼저 완료해 주세요."));
        mvc.perform(post("/api/users/me/fortunes/today").with(user("test")))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("사주 계산을 먼저 완료해 주세요."));
    }
    @Test void internalErrorsDoNotLeakSqlOrCredentials() throws Exception {
        when(currentUser.getCurrentUserId()).thenReturn(12L);
        when(service.today(12L)).thenThrow(new IllegalStateException("password secret SQL"));
        mvc.perform(post("/api/users/me/fortunes/today").with(user("test")))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value("FORTUNE_INTERNAL_ERROR"))
                .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("password secret"))));
    }
    @Autowired MockMvc mvc;
    @MockitoBean FortuneService service;
    @MockitoBean CurrentUserProvider currentUser;
    @MockitoBean JwtProvider jwtProvider;

    @Test void rejectsUnauthenticatedCall() throws Exception {
        mvc.perform(post("/api/users/me/fortunes/today")).andExpect(status().isUnauthorized());
        verifyNoInteractions(service, currentUser);
    }
    @Test void usesAuthenticatedUserAndWrapsResponse() throws Exception {
        when(currentUser.getCurrentUserId()).thenReturn(12L);
        when(service.today(12L)).thenReturn(new FortuneResponse(LocalDate.of(2026,9,8),
                new FortuneResponse.OverallFortune(70,"집중","준비하세요"), List.of(),
                new FortuneResponse.DailyQuest("준비","목표 정하기","가상 칭호"),"차분히"));
        mvc.perform(post("/api/users/me/fortunes/today").with(user("test")))
                .andExpect(status().isOk()).andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.date").value("2026-09-08"))
                .andExpect(jsonPath("$.data.overallFortune.score").value(70));
        verify(service).today(12L);
    }
}

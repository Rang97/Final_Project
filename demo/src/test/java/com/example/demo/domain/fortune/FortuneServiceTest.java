package com.example.demo.domain.fortune;

import com.example.demo.domain.fortune.dto.FortuneRequest;
import com.example.demo.domain.fortune.repository.FortuneMapper;
import com.example.demo.domain.fortune.service.*;
import com.example.demo.domain.saju.entity.Saju;
import com.example.demo.domain.saju.repository.SajuMapper;
import com.example.demo.domain.saju.util.*;
import com.example.demo.infra.gemini.GeminiFortuneClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class FortuneServiceTest {
    final FortuneMapper mapper = mock(FortuneMapper.class);
    final SajuMapper saju = mock(SajuMapper.class);
    final TodayElementService today = mock(TodayElementService.class);
    final FortuneAnalysisService analysis = mock(FortuneAnalysisService.class);
    final GeminiFortuneClient gemini = mock(GeminiFortuneClient.class);
    final FortuneStore store = mock(FortuneStore.class);
    final LocalDate date = LocalDate.of(2026, 9, 8);
    FortuneService service;
    String valid;

    @BeforeEach void setup() throws Exception {
        valid = new ClassPathResource("fortune-valid.json").getContentAsString(StandardCharsets.UTF_8);
        service = new FortuneService(mapper, saju, today, analysis, gemini,
                new FortuneJsonCodec(new ObjectMapper().findAndRegisterModules()), store,
                Clock.fixed(Instant.parse("2026-09-07T15:00:00Z"), ZoneId.of("Asia/Seoul")));
    }
    void prepare() {
        Saju entity = mock(Saju.class);
        when(saju.findByUserId(1L)).thenReturn(Optional.of(entity));
        var profile = new FortuneRequest.UserSaju("갑", List.of(FiveElement.WOOD), List.of(FiveElement.WATER),
                new FiveElementProfile(3, 2, 2, 2, 1));
        when(analysis.profile(entity)).thenReturn(profile);
        var daily = new FortuneRequest.Today(date, "갑", "자", List.of(FiveElement.WOOD, FiveElement.WATER));
        when(today.get(date)).thenReturn(daily);
        when(analysis.analyze(profile, daily)).thenReturn(new FortuneRequest.Analysis(List.of("집중"), List.of("무리 주의")));
        when(mapper.games(1L)).thenReturn(List.of(new FortuneMapper.GameRow(15L, "게임", "MOBA"),
                new FortuneMapper.GameRow(15L, "게임", "TEAM")));
        when(gemini.generate(any())).thenReturn(valid);
    }
    @Test void cacheHitNeverCallsAiOrPrerequisitesAndUsesSeoulDate() {
        when(mapper.find(1L, date)).thenReturn(new FortuneMapper.Stored(valid));
        assertThat(service.today(1L).overallFortune().score()).isEqualTo(70);
        verifyNoInteractions(saju, today, analysis, gemini, store);
    }
    @Test void generatesAllGamesInOneCallAndStores() {
        prepare();
        service.today(1L);
        var captor = org.mockito.ArgumentCaptor.forClass(FortuneRequest.class);
        verify(gemini, times(1)).generate(captor.capture());
        assertThat(captor.getValue().favoriteGames()).hasSize(1);
        assertThat(captor.getValue().favoriteGames().get(0).tags()).containsExactly("MOBA", "TEAM");
        verify(store).complete(eq(1L), eq(date), anyString());
    }
    @Test void concurrentLoserDoesNotCallAi() {
        prepare();
        when(mapper.claim(1L, date)).thenThrow(new DuplicateKeyException("duplicate"));
        when(mapper.status(1L, date)).thenReturn("PROCESSING");
        assertThatThrownBy(() -> service.today(1L)).isInstanceOfSatisfying(ResponseStatusException.class,
                e -> assertThat(e.getStatusCode()).isEqualTo(HttpStatus.CONFLICT));
        verify(gemini, never()).generate(any());
        verifyNoInteractions(store, today);
    }
    @Test void failedAttemptDoesNotAutomaticallyRetry() {
        prepare();
        when(mapper.claim(1L, date)).thenThrow(new DuplicateKeyException("duplicate"));
        when(mapper.status(1L, date)).thenReturn("FAILED");
        assertThatThrownBy(() -> service.today(1L)).isInstanceOfSatisfying(ResponseStatusException.class,
                e -> assertThat(e.getStatusCode()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE));
        verify(gemini, never()).generate(any());
    }
    @Test void invalidAiResponseMarksFailedWithoutSaving() {
        prepare();
        when(gemini.generate(any())).thenReturn("{}");
        assertThatThrownBy(() -> service.today(1L)).isInstanceOf(ResponseStatusException.class);
        verify(mapper).finish(1L, date, "FAILED");
        verifyNoInteractions(store);
    }
    @Test void missingSajuDoesNotReserveAttempt() {
        when(saju.findByUserId(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.today(1L)).isInstanceOf(ResponseStatusException.class);
        verify(mapper, never()).claim(anyLong(), any());
        verifyNoInteractions(gemini);
    }
    @Test void completionDuringClaimRaceReturnsStoredResult() {
        prepare();
        when(mapper.find(1L, date)).thenReturn(null, new FortuneMapper.Stored(valid));
        when(mapper.claim(1L, date)).thenThrow(new DuplicateKeyException("duplicate"));
        assertThat(service.today(1L).date()).isEqualTo(date);
        verify(gemini, never()).generate(any());
    }
}


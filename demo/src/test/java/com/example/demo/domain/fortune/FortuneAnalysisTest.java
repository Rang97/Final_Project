package com.example.demo.domain.fortune;

import com.example.demo.domain.fortune.service.*;
import com.example.demo.domain.fortune.dto.FortuneRequest.*;
import com.example.demo.domain.saju.entity.Saju;
import com.example.demo.domain.saju.util.*;
import com.example.demo.infra.sajuapi.*;
import com.example.demo.infra.sajuapi.dto.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class FortuneAnalysisTest {
    final FortuneAnalysisService analysis = new FortuneAnalysisService();
    @Test void balancedProfileDoesNotMarkEveryElementStrongAndWeak() {
        Saju saju = mock(Saju.class);
        when(saju.getDayStem()).thenReturn("갑");
        when(saju.getWoodCount()).thenReturn(2.0);
        when(saju.getFireCount()).thenReturn(2.0);
        when(saju.getEarthCount()).thenReturn(2.0);
        when(saju.getMetalCount()).thenReturn(2.0);
        when(saju.getWaterCount()).thenReturn(2.0);
        var result = analysis.profile(saju);
        assertThat(result.strongElements()).isEmpty();
        assertThat(result.weakElements()).isEmpty();
    }
    @Test void directionalRelationsProduceFactors() {
        var profile = new UserSaju("갑", List.of(FiveElement.WOOD), List.of(FiveElement.WATER),
                new FiveElementProfile(4, 2, 2, 2, 0));
        var result = analysis.analyze(profile, new Today(LocalDate.now(), "경", "신", List.of(FiveElement.METAL)));
        assertThat(result.positiveFactors()).anyMatch(s -> s.contains("WATER") && s.contains("보완"));
    }
    @Test void todayUsesOnlyDayPillarAndCachesByDate() {
        var client = mock(SajuApiClient.class);
        when(client.calculate(any())).thenReturn(new SajuApiResponse(null, null,
                new SajuApiResponse.DayPillar("甲", "갑", "子", "자"), null, null));
        var service = new TodayElementService(client);
        var date = LocalDate.of(2026, 9, 8);
        assertThat(service.get(date).mainElements()).containsExactly(FiveElement.WOOD, FiveElement.WATER);
        service.get(date);
        verify(client, times(1)).calculate(any());
        service.get(date.plusDays(1));
        verify(client, times(2)).calculate(any());
    }
}


package com.example.demo.domain.fortune.dto;

import java.time.LocalDate;
import java.util.List;

public record FortuneResponse(LocalDate date, OverallFortune overallFortune,
                              List<GameFortune> gameFortunes, DailyQuest dailyQuest, String oneLineMessage) {
    public record OverallFortune(Integer score, String title, String content) {}
    public record GameFortune(Long gameId, Integer score, String title, String content, Effect buff, Effect caution) {}
    public record Effect(String name, String effect) {}
    public record DailyQuest(String title, String mission, String reward) {}
}


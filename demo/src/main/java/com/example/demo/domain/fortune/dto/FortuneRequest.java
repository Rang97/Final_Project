package com.example.demo.domain.fortune.dto;

import com.example.demo.domain.saju.util.FiveElement;
import com.example.demo.domain.saju.util.FiveElementProfile;
import java.time.LocalDate;
import java.util.List;

public record FortuneRequest(UserSaju userSaju, Today today, Analysis analysis, List<FavoriteGame> favoriteGames) {
    public record UserSaju(String dayMaster, List<FiveElement> strongElements,
                           List<FiveElement> weakElements, FiveElementProfile elements) {}
    public record Today(LocalDate date, String stem, String branch, List<FiveElement> mainElements) {}
    public record Analysis(List<String> positiveFactors, List<String> cautionFactors) {}
    public record FavoriteGame(Long gameId, String gameName, List<String> tags) {}
}


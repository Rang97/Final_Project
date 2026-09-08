package com.example.demo.domain.fortune.service;

import com.example.demo.domain.fortune.dto.*;
import com.example.demo.domain.fortune.exception.FortuneException;
import com.example.demo.domain.fortune.dto.FortuneRequest.FavoriteGame;
import com.example.demo.domain.fortune.repository.FortuneMapper;
import com.example.demo.domain.saju.repository.SajuMapper;
import com.example.demo.infra.gemini.GeminiFortuneClient;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FortuneService {
    private final FortuneMapper mapper;
    private final SajuMapper sajuMapper;
    private final TodayElementService todayElements;
    private final FortuneAnalysisService analysis;
    private final GeminiFortuneClient gemini;
    private final FortuneJsonCodec codec;
    private final FortuneStore store;
    private final Clock fortuneClock;

    public FortuneResponse today(Long userId) {
        LocalDate date = LocalDate.now(fortuneClock);
        var saved = mapper.find(userId, date);
        if (saved != null) return readSaved(saved, date);
        // Validate prerequisites before reserving the one daily generation attempt.
        var saju = sajuMapper.findByUserId(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.CONFLICT, "사주 계산을 먼저 완료해 주세요."));
        var profile = analysis.profile(saju);
        gemini.requireConfigured();
        try {
            mapper.claim(userId, date);
        } catch (DuplicateKeyException e) {
            saved = mapper.find(userId, date);
            if (saved != null) return readSaved(saved, date);
            String status = mapper.status(userId, date);
            if ("PROCESSING".equals(status))
                throw new FortuneException(HttpStatus.CONFLICT, "FORTUNE_GENERATING",
                        "오늘 운세를 생성 중입니다. 잠시 후 다시 조회해 주세요.", Map.of("stage", "GENERATION_STATE"));
            throw new FortuneException(HttpStatus.SERVICE_UNAVAILABLE, "FORTUNE_PREVIOUS_ATTEMPT_FAILED",
                    "이전 운세 생성 시도가 완료되지 않았습니다. 자동 재생성하지 않습니다. 이전 실패의 상세 원인은 DB에 저장되지 않습니다.",
                    Map.of("stage", "GENERATION_STATE"));
        }
        try {
            var today = todayElements.get(date);
            var games = favoriteGames(userId);
            var request = new FortuneRequest(profile, today, analysis.analyze(profile, today), games);
            String json = gemini.generate(request);
            var response = codec.decode(json, date, games.stream().map(FavoriteGame::gameId).toList());
            store.complete(userId, date, codec.encode(response));
            return response;
        } catch (RuntimeException e) {
            // Preserve the original error even when the DB itself is unavailable.
            try { mapper.finish(userId, date, "FAILED"); } catch (RuntimeException markingFailure) {
                e.addSuppressed(markingFailure);
            }
            throw e;
        }
    }

    private FortuneResponse readSaved(FortuneMapper.Stored saved, LocalDate date) {
        if (saved.responseJson() == null)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이전 형식의 운세 데이터입니다. 관리자 확인이 필요합니다.");
        // Validate against the stored snapshot, not today's possibly changed preferences.
        return codec.decode(saved.responseJson(), date, null);
    }

    private List<FavoriteGame> favoriteGames(Long userId) {
        var rows = mapper.games(userId);
        Map<Long, String> names = new LinkedHashMap<>();
        Map<Long, Set<String>> tags = new LinkedHashMap<>();
        for (var row : rows) {
            names.putIfAbsent(row.gameId(), row.gameName());
            tags.computeIfAbsent(row.gameId(), id -> new LinkedHashSet<>());
            if (row.tag() != null) tags.get(row.gameId()).add(row.tag());
        }
        if (names.size() > 5) throw new ResponseStatusException(HttpStatus.CONFLICT, "선호 게임은 최대 5개입니다.");
        return names.entrySet().stream()
                .map(e -> new FavoriteGame(e.getKey(), e.getValue(), List.copyOf(tags.get(e.getKey())))).toList();
    }
}

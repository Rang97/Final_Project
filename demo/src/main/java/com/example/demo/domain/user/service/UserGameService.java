package com.example.demo.domain.user.service;

import com.example.demo.global.util.CurrentUserProvider;
import com.example.demo.domain.user.dto.UserGameCreateRequest;
import com.example.demo.domain.user.repository.UserGameMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserGameService {

    private static final int MAX_GAME_COUNT = 5;

    private final CurrentUserProvider currentUserProvider;
    private final UserGameMapper userGameMapper;

    public Long getCurrentUserId() {
        return currentUserProvider.getCurrentUserId();
    }

    @Transactional
    public void changeMainGame(Long gameId) {
        Long userId = lockCurrentUser();
        if (!userGameMapper.existsByUserIdAndGameId(userId, gameId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "등록한 선호 게임을 찾을 수 없습니다.");
        }

        userGameMapper.clearMainByUserId(userId);
        if (userGameMapper.setMain(userId, gameId) != 1) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "등록한 선호 게임을 찾을 수 없습니다.");
        }
    }

    @Transactional
    public void delete(Long gameId) {
        Long userId = lockCurrentUser();
        if (userGameMapper.deleteByUserIdAndGameId(userId, gameId) != 1) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "등록한 선호 게임을 찾을 수 없습니다.");
        }
    }

    @Transactional
    public void register(UserGameCreateRequest request) {
        Long userId = lockCurrentUser();
        Long gameId = request.getGameId();

        if (!userGameMapper.existsGame(gameId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "게임을 찾을 수 없습니다.");
        }
        if (userGameMapper.existsByUserIdAndGameId(userId, gameId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 등록한 게임입니다.");
        }
        if (userGameMapper.countByUserId(userId) >= MAX_GAME_COUNT) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "선호 게임은 최대 5개까지 등록할 수 있습니다.");
        }

        try {
            userGameMapper.insert(userId, gameId);
        } catch (DuplicateKeyException exception) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 등록한 게임입니다.", exception);
        }
    }
    // 모든 변경은 같은 사용자 행을 먼저 잠그고, 트랜잭션 종료까지 잠금을 유지한다.
    private Long lockCurrentUser() {
        Long userId = getCurrentUserId();
        if (userGameMapper.lockUserById(userId) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.");
        }
        return userId;
    }
}

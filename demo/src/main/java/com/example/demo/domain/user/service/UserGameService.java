package com.example.demo.domain.user.service;

import com.example.demo.global.util.CurrentUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserGameService {

    private final CurrentUserProvider currentUserProvider;

    public Long getCurrentUserId() {
        return currentUserProvider.getCurrentUserId();
    }
}

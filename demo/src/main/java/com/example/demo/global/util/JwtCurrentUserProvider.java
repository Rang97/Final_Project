// global/util/JwtCurrentUserProvider.java
package com.example.demo.global.util;

import com.example.demo.global.jwt.AuthenticatedUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class JwtCurrentUserProvider implements CurrentUserProvider {

    @Override
    public Long getCurrentUserId() {
        Long id = getCurrentUserIdOrNull();
        if (id == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }
        return id;
    }

    @Override
    public Long getCurrentUserIdOrNull() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof AuthenticatedUser user)) {
            return null;
        }
        return user.userId();
    }
}
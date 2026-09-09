package com.example.demo.global.jwt;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// 채팅 접속 상태 저장
@Component
public class StompSessionRegistry implements ApplicationListener<SessionDisconnectEvent> {

    private final Map<Long, String> connectedUsers = new ConcurrentHashMap<>();

    // 이미 접속 중인 유저 등록/거부
    public boolean tryConnect(Long userId, String sessionId) {
        // 있으면 등록 실패 (false), 없으면 등록 성공 (true)
        return connectedUsers.putIfAbsent(userId, sessionId) == null;
    }

    // 유저 접속 기록 = 지금 끊긴 세션 일치할 때만 삭제
    public void disconnect(Long userId, String sessionId) {
        // key, value(sessionId)가 둘 다 일치할 때만 제거
        connectedUsers.remove(userId, sessionId);
    }

    // 소켓 끊길 때마다 자동 호출
    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        Principal principal = event.getUser();

        if (principal != null){
            AuthenticatedUser authenticatedUser = (AuthenticatedUser) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
            Long userId = authenticatedUser.userId();
            String sessionId = StompHeaderAccessor.wrap(event.getMessage()).getSessionId();
            disconnect(userId, sessionId);
        }
    }

}

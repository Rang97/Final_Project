package com.example.demo.global.jwt;

import com.example.demo.domain.party.entity.PartyMember;
import com.example.demo.domain.party.entity.PartyMemberStatus;
import com.example.demo.domain.party.repository.PartyMapper;
import com.example.demo.domain.party.repository.PartyMemberMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

// 보안 강화 (아무나 토큰 소켓에 붙여 구독/전송 되는 것 방지)
// CONNECT: 로그인한 사용자인지 확인 / SUBSCRIBE: 이 파티원 맞는지 확인
@Component
@RequiredArgsConstructor
public class StompAuthInterceptor implements ChannelInterceptor {

    private final JwtProvider jwtProvider;
    private final PartyMemberMapper partyMemberMapper;
    private final StompSessionRegistry sessionRegistry;

    // 클라이언트가 보내는 모든 STOMP 프레임이 여기를 거쳐감 (CONNECT, SEND, SUBSCRIBE)
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        // 프레임 까기 (STOMP 프레임 = 바이트, accessor로 감싸 커맨드/헤더 꺼냄)
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            handleConnect(accessor);
        } else if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
            handleSubscribe(accessor);
        }

        return ChannelInterceptor.super.preSend(message, channel);
    }

    // CONNECT 처리: 토큰 검증, 이 소켓 세션에 유저 정보 입력
    private void handleConnect(StompHeaderAccessor accessor) {
        String token = resolveToken(accessor.getFirstNativeHeader("Authorization"));

        // 토큰 없으면 연결 차단
        if (token == null) {
            throw new MessagingException("인증 토큰이 없습니다.");
        }

        try {
            // 토큰 검증 + 안의 정보 꺼냄
            Claims claims = jwtProvider.parseClaims(token);
            Long userId = Long.valueOf(claims.getSubject());
            String loginId = claims.get("loginId", String.class);
            String role = claims.get("role", String.class);

            if (!sessionRegistry.tryConnect(userId, accessor.getSessionId())) {
                throw new MessagingException("이미 다른 채팅에서 접속 중입니다.");
            }

            // 꺼낸 정보로 유저 객체(AuthenticatedUser) 조립
            AuthenticatedUser principal = new AuthenticatedUser(userId, loginId, role);
            // 소켓 세션 내부 "누구인지" 확인
            accessor.setUser(new UsernamePasswordAuthenticationToken(
                    principal, null, List.of(new SimpleGrantedAuthority("ROLE_" + role))
            ));
        } catch (JwtException | IllegalArgumentException e) {
            // 토큰 위조/만료 시 파싱 실패 -> 연결 거부
            throw new MessagingException("인증 토큰이 유효하지 않습니다.");
        }
    }

    // SUBSCRIBE 처리: destination에서 partyId 뽑고, 유저가 이 파티 멤버인지 확인
    private void handleSubscribe(StompHeaderAccessor accessor){
        Long partyId = extractPartyId(accessor.getDestination());
        if (partyId == null){
            return; // 파티 채팅방 구독이 아니면 그냥 통과
        }

        // 유저 정보 다시 꺼냄
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) accessor.getUser();
        if (authentication == null){
            throw new MessagingException("인증 정보가 없습니다.");
        }

        AuthenticatedUser principal = (AuthenticatedUser) authentication.getPrincipal();
        PartyMember partyMember = partyMemberMapper.findByPartyIdAndUserId(partyId, principal.userId());

        if (partyMember == null || partyMember.getStatus() != PartyMemberStatus.APPROVED){
            throw new MessagingException("파티원만 채팅에 참여할 수 있습니다.");
        }
    }

    // 구독 주소 문자열에서 파티 번호만 뽑아냄 (파싱 전용)
    private Long extractPartyId(String destination) {
        if (destination == null || !destination.startsWith("/sub/party/")) {
            return null;
        }
        try {
            return Long.parseLong(destination.substring("/sub/party/".length()));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // 토큰 문자열에서 순수 토큰값만 뽑아냄
    private String resolveToken(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }
        String token = header.substring("Bearer ".length()).trim();
        return token.isEmpty() ? null : token;

    }

}

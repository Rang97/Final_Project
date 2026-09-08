package com.example.demo.domain.party.controller;

import com.example.demo.domain.party.dto.ChatMessageRequest;
import com.example.demo.domain.party.dto.ChatMessageResponse;
import com.example.demo.domain.party.entity.ChatMessageType;
import com.example.demo.domain.party.entity.PartyMember;
import com.example.demo.domain.party.entity.PartyMemberStatus;
import com.example.demo.domain.party.repository.PartyMemberMapper;
import com.example.demo.domain.party.util.BadWordFilter;
import com.example.demo.global.jwt.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final PartyMemberMapper partyMemberMapper;

    @MessageMapping("/party/{partyId}/chat")
    public void sendChat(
            @DestinationVariable Long partyId,
            @Payload ChatMessageRequest request,
            Principal principal
    ){
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        AuthenticatedUser sender = (AuthenticatedUser) authenticationToken.getPrincipal();

        // 쓰기 권한 확인
        PartyMember partyMember = partyMemberMapper.findByPartyIdAndUserId(partyId, sender.userId());
        if (partyMember == null || partyMember.getStatus() != PartyMemberStatus.APPROVED){
            throw new MessagingException("파티원만 채팅에 참여할 수 있습니다.");
        }

        // 금칙어 예외
        if (BadWordFilter.contains(request.content())) {
            throw new MessagingException("금칙어가 포함되어 있어 전송할 수 없습니다.");
        }

        // 실제로 뿌릴 메시지 조립
        ChatMessageResponse response = new ChatMessageResponse(
                sender.loginId(),
                request.content(),
                ChatMessageType.CHAT,
                LocalDateTime.now()
        );

        // 구독 중인 유저 전원에게 response 보냄
        messagingTemplate.convertAndSend("/sub/party/" + partyId, response);


    }
}

package com.example.demo.domain.party.service;

import com.example.demo.domain.party.dto.ChatMessageResponse;
import com.example.demo.domain.party.entity.ChatMessageType;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
// 시스템 메시지
public class PartyChatNotifier {
    private final SimpMessagingTemplate messagingTemplate;
    private final UserMapper userMapper;

    public void notifySystemMessage(Long partyId, Long userId, String action){
        User user = userMapper.findById(userId).orElseThrow(() -> new MessagingException("사용자를 찾을 수 없습니다."));
        String nickname = user.getNickname();

        // 시스템 메시지 조립
        ChatMessageResponse response = new ChatMessageResponse(
            nickname,
                nickname + "님이 " + action,
                ChatMessageType.SYSTEM,
                LocalDateTime.now()
        );
        // 메시지 뿌리기
        messagingTemplate.convertAndSend("/sub/party/" + partyId, response);
    }
}

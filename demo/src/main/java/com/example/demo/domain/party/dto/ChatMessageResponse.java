package com.example.demo.domain.party.dto;

import com.example.demo.domain.party.entity.ChatMessageType;

import java.time.LocalDateTime;

public record ChatMessageResponse (
        String sender,
        String content,
        ChatMessageType type,
        LocalDateTime timestamp
){
}

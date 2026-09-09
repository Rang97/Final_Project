package com.example.demo.domain.post.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

// CommentResponse.java
@Getter
@Builder
public class CommentResponse {
    private Long commentId;
    private Long postId;
    private String writerNickname; //유저 닉네임
    private String content;
    private LocalDateTime createdAt;
}
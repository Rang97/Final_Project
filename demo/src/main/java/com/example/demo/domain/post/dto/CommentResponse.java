package com.example.demo.domain.post.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

// domain/post/dto/CommentResponse.java
@Getter
@Builder
public class CommentResponse {
    private Long commentId;
    private Long postId;
    private String writerAnimalName;
    private String content;
    private LocalDateTime createdAt;
}
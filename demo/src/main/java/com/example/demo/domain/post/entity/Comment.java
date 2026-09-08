package com.example.demo.domain.post.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// domain/post/entity/Comment.java
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Long commentId;
    private Long postId;
    private Long writerId;
    private String content;
    private LocalDateTime createdAt;
}
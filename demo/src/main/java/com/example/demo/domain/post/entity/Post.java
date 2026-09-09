package com.example.demo.domain.post.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// domain/post/entity/Post.java
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Post {
    private Long postId;
    private Long writerId;
    private String title;
    private String content;
    private int viewCount;
    private LocalDateTime createdAt;
}
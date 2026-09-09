package com.example.demo.domain.post.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostListResponse {
    private Long postId;
    private String title;
    private String writerNickname;   //유저 닉네임
    private int viewCount;
    private LocalDateTime createdAt;
}
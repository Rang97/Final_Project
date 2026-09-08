package com.example.demo.domain.post.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

// domain/post/dto/PostListResponse.java  (목록용, content 생략)
@Getter
@Builder
public class PostListResponse {
    private Long postId;
    private String title;
    private String writerAnimalName; // saju_animal_name (익명 표시)
    private int viewCount;
    private LocalDateTime createdAt;
}
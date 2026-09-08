package com.example.demo.domain.post.dto;

import com.example.demo.domain.post.dto.CommentResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

// PostDetailResponse.java
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailResponse {
    private Long postId;
    private String title;
    private String content;
    private String writerNickname; //유저 닉네임
    private int viewCount;
    private LocalDateTime createdAt;
    private List<CommentResponse> comments;
}
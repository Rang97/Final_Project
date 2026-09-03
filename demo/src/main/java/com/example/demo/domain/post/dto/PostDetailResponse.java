package com.example.demo.domain.post.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailResponse {
    private Long postId;
    private String title;
    private String content;
    private String writerAnimalName;
    private int viewCount;
    private LocalDateTime createdAt;
    private List<CommentResponse> comments;
}
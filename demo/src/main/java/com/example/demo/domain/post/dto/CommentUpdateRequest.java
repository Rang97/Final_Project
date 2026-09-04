package com.example.demo.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

// domain/post/dto/CommentUpdateRequest.java (새 DTO)
@Getter
public class CommentUpdateRequest {
    @NotBlank
    private String content;
}
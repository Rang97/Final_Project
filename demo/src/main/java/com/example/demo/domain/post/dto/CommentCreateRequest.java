package com.example.demo.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

// domain/post/dto/CommentCreateRequest.java
@Getter
public class CommentCreateRequest {
    @NotBlank
    private String content;
}
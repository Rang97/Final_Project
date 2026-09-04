package com.example.demo.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

// domain/post/dto/PostCreateRequest.java
@Getter
public class PostCreateRequest {
    @NotBlank
    @Size(max = 200)
    private String title;

    @NotBlank
    private String content;
}
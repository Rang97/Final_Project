package com.example.demo.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

// domain/post/dto/PostCreateRequest.java
@Getter
public class PostCreateRequest {
    @Schema(description = "게시글 제목", example = "오늘의 운세 이야기", maxLength = 200)
    @NotBlank
    @Size(max = 200)
    private String title;

    @Schema(description = "게시글 내용", example = "오늘 본 운세에 대해 자유롭게 이야기해요.")
    @NotBlank
    private String content;
}

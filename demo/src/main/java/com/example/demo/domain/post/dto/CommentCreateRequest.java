package com.example.demo.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

// domain/post/dto/CommentCreateRequest.java
@Getter
public class CommentCreateRequest {
    @Schema(description = "댓글 내용", example = "좋은 이야기 감사합니다!")
    @NotBlank
    private String content;
}

package com.example.demo.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

// domain/post/dto/CommentUpdateRequest.java (새 DTO)
@Getter
public class CommentUpdateRequest {
    @Schema(description = "수정할 댓글 내용", example = "댓글 내용을 수정했습니다.")
    @NotBlank
    private String content;
}

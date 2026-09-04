package com.example.demo.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

// domain/post/dto/PostUpdateRequest.java
@Getter
public class PostUpdateRequest {
    @Schema(description = "수정할 제목", example = "수정된 운세 이야기", maxLength = 200)
    @NotBlank
    @Size(max = 200)
    private String title;
    @Schema(description = "수정할 내용", example = "게시글 내용을 수정했습니다.")
    @NotBlank
    private String content;
}

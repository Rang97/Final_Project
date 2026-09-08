package com.example.demo.domain.post.controller;

import com.example.demo.domain.post.dto.CommentCreateRequest;
import com.example.demo.domain.post.dto.CommentResponse;
import com.example.demo.domain.post.dto.CommentUpdateRequest;
import com.example.demo.domain.post.service.CommentService;
import com.example.demo.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// CommentController.java
// TODO: 로그인 필수 예정

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@RequiredArgsConstructor
@Tag(name = "자유게시판 댓글", description = "자유게시판 댓글 CRUD API")
@SecurityRequirement(name = "bearerAuth")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @Operation(summary = "댓글 작성", description = "게시글에 댓글을 작성합니다.")
    public ApiResponse<Long> create(
                                    @Parameter(description = "게시글 ID", example = "1") @PathVariable Long postId,
                                    @Valid @RequestBody CommentCreateRequest req) {
        return ApiResponse.success(commentService.createComment(postId, req));
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "댓글 삭제", description = "작성자 본인의 댓글을 삭제합니다.")
    public ApiResponse<Void> delete(
                                    @Parameter(description = "게시글 ID", example = "1") @PathVariable Long postId,
                                    @Parameter(description = "댓글 ID", example = "1") @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ApiResponse.success();
    }

    @GetMapping
    @Operation(summary = "댓글 목록 조회", description = "게시글의 댓글 목록을 조회합니다.")
    public ApiResponse<List<CommentResponse>> list(
            @Parameter(description = "게시글 ID", example = "1") @PathVariable Long postId) {
        return ApiResponse.success(commentService.getComments(postId));
    }

    @PutMapping("/{commentId}")
    @Operation(summary = "댓글 수정", description = "작성자 본인의 댓글을 수정합니다.")
    public ApiResponse<Void> update(
                                    @Parameter(description = "게시글 ID", example = "1") @PathVariable Long postId,
                                    @Parameter(description = "댓글 ID", example = "1") @PathVariable Long commentId,
                                    @Valid @RequestBody CommentUpdateRequest req) {
        commentService.updateComment(commentId, req);
        return ApiResponse.success();
    }
}

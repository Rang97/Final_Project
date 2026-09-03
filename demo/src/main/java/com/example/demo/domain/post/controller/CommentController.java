package com.example.demo.domain.post.controller;

import com.example.demo.domain.post.dto.CommentCreateRequest;
import com.example.demo.domain.post.dto.CommentResponse;
import com.example.demo.domain.post.dto.CommentUpdateRequest;
import com.example.demo.domain.post.service.CommentService;
import com.example.demo.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// CommentController.java
// TODO: 로그인 필수 예정

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ApiResponse<Long> create(@PathVariable Long postId,
                                    @Valid @RequestBody CommentCreateRequest req) {
        return ApiResponse.success(commentService.createComment(postId, req));
    }

    @DeleteMapping("/{commentId}")
    public ApiResponse<Void> delete(@PathVariable Long postId,
                                    @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ApiResponse.success();
    }

    @GetMapping
    public ApiResponse<List<CommentResponse>> list(@PathVariable Long postId) {
        return ApiResponse.success(commentService.getComments(postId));
    }

    @PutMapping("/{commentId}")
    public ApiResponse<Void> update(@PathVariable Long postId,
                                    @PathVariable Long commentId,
                                    @Valid @RequestBody CommentUpdateRequest req) {
        commentService.updateComment(commentId, req);
        return ApiResponse.success();
    }
}
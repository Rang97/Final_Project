package com.example.demo.domain.post.controller;

import com.example.demo.domain.post.dto.PostCreateRequest;
import com.example.demo.domain.post.dto.PostDetailResponse;
import com.example.demo.domain.post.dto.PostListResponse;
import com.example.demo.domain.post.dto.PostUpdateRequest;
import com.example.demo.domain.post.service.PostService;
import com.example.demo.global.common.ApiResponse;
import com.example.demo.global.common.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

// PostController.java
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    // TODO: JWT 완성되면 SecurityConfig에서 .authenticated() 적용 필요 (작성)
    @PostMapping
    public ApiResponse<Long> create(@Valid @RequestBody PostCreateRequest req) {
        return ApiResponse.success(postService.createPost(req));
    }

    // TODO: JWT 완성되면 SecurityConfig에서 .authenticated() 적용 필요 (상세 조회)
    @GetMapping("/{postId}")
    public ApiResponse<PostDetailResponse> detail(@PathVariable Long postId) {
        return ApiResponse.success(postService.getPostDetail(postId));
    }

    // TODO: 로그인 필수 예정
    @PutMapping("/{postId}")
    public ApiResponse<Void> update(@PathVariable Long postId, @Valid @RequestBody PostUpdateRequest req) {
        postService.updatePost(postId, req);
        return ApiResponse.success();
    }

    // TODO: 로그인 필수 예정
    @DeleteMapping("/{postId}")
    public ApiResponse<Void> delete(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ApiResponse.success();
    }

    @GetMapping
    public ApiResponse<PageResponse<PostListResponse>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.success(postService.getPostList(page, size));
    }


}
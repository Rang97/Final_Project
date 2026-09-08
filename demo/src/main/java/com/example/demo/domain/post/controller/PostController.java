package com.example.demo.domain.post.controller;

import com.example.demo.domain.post.dto.PostCreateRequest;
import com.example.demo.domain.post.dto.PostDetailResponse;
import com.example.demo.domain.post.dto.PostListResponse;
import com.example.demo.domain.post.dto.PostUpdateRequest;
import com.example.demo.domain.post.service.PostService;
import com.example.demo.global.common.ApiResponse;
import com.example.demo.global.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

// PostController.java
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Tag(name = "자유게시판", description = "자유게시판 게시글 CRUD API")
public class PostController {

    private final PostService postService;


    // TODO: JWT 완성되면 SecurityConfig에서 .authenticated() 적용 필요 (작성)
    @PostMapping
    @Operation(summary = "게시글 작성", description = "로그인한 사용자가 자유게시판 게시글을 작성합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "작성 성공 (생성된 게시글 ID 반환)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "입력값 검증 실패", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 필요", content = @Content)
    })
    public ApiResponse<Long> create(@Valid @RequestBody PostCreateRequest req) {
        return ApiResponse.success(postService.createPost(req));
    }

    // TODO: JWT 완성되면 SecurityConfig에서 .authenticated() 적용 필요 (상세 조회)
    @GetMapping("/{postId}")
    @Operation(summary = "게시글 상세 조회", description = "게시글과 댓글을 조회하고 조회수를 증가시킵니다.", security = @SecurityRequirement(name = "bearerAuth"))
    public ApiResponse<PostDetailResponse> detail(
            @Parameter(description = "게시글 ID", example = "1") @PathVariable Long postId) {
        return ApiResponse.success(postService.getPostDetail(postId));
    }

    // TODO: 로그인 필수 예정
    @PutMapping("/{postId}")
    @Operation(summary = "게시글 수정", description = "작성자 본인의 게시글을 수정합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    public ApiResponse<Void> update(
            @Parameter(description = "게시글 ID", example = "1") @PathVariable Long postId,
            @Valid @RequestBody PostUpdateRequest req) {
        postService.updatePost(postId, req);
        return ApiResponse.success();
    }

    // TODO: 로그인 필수 예정
    @DeleteMapping("/{postId}")
    @Operation(summary = "게시글 삭제", description = "작성자 본인의 게시글을 삭제합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    public ApiResponse<Void> delete(
            @Parameter(description = "게시글 ID", example = "1") @PathVariable Long postId) {
        postService.deletePost(postId);
        return ApiResponse.success();
    }

    @GetMapping
    @Operation(summary = "게시글 목록 조회", description = "자유게시판 게시글을 최신순으로 페이지 조회합니다. 로그인 없이 호출할 수 있습니다.")
    public ApiResponse<PageResponse<PostListResponse>> list(
            @Parameter(description = "페이지 번호(0부터 시작)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기", example = "10") @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.success(postService.getPostList(page, size));
    }


}

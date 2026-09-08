package com.example.demo.domain.post.service;

import com.example.demo.domain.post.dto.CommentCreateRequest;
import com.example.demo.domain.post.dto.CommentResponse;
import com.example.demo.domain.post.dto.CommentUpdateRequest;
import com.example.demo.domain.post.entity.Comment;
import com.example.demo.domain.post.repository.CommentMapper;
import com.example.demo.domain.post.repository.PostMapper;
import com.example.demo.global.util.CurrentUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;
    private final PostMapper postMapper;
    private final CurrentUserProvider currentUserProvider;

    @Transactional
    public Long createComment(Long postId, CommentCreateRequest req) {
        Long writerId = postMapper.findWriterId(postId);
        if (writerId == null) {
            throw new IllegalArgumentException("게시글을 찾을 수 없습니다.");
        }

        Comment comment = Comment.builder()
                .postId(postId)
                .writerId(currentUserProvider.getCurrentUserId())
                .content(req.getContent())
                .build();

        commentMapper.insertComment(comment);
        return comment.getCommentId();
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Long writerId = commentMapper.findWriterId(commentId);
        if (writerId == null) {
            throw new IllegalArgumentException("댓글을 찾을 수 없습니다.");
        }
        if (!writerId.equals(currentUserProvider.getCurrentUserId())) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }
        commentMapper.deleteComment(commentId);
    }

    @Transactional
    public void updateComment(Long commentId, CommentUpdateRequest req) {
        Long writerId = commentMapper.findWriterId(commentId);
        if (writerId == null) {
            throw new IllegalArgumentException("댓글을 찾을 수 없습니다.");
        }
        if (!writerId.equals(currentUserProvider.getCurrentUserId())) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }
        commentMapper.updateComment(commentId, req.getContent());
    }

    public List<CommentResponse> getComments(Long postId) {
        Long myId = currentUserProvider.getCurrentUserId();
        return commentMapper.findVisibleCommentsByPostId(postId, myId);
    }
}
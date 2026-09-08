package com.example.demo.domain.post.repository;

import com.example.demo.domain.post.dto.CommentResponse;
import com.example.demo.domain.post.entity.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("INSERT INTO comment (post_id, writer_id, content) VALUES (#{postId}, #{writerId}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "commentId")
    void insertComment(Comment comment);

    @Update("UPDATE comment SET content = #{content} WHERE comment_id = #{commentId}")
    void updateComment(@Param("commentId") Long commentId, @Param("content") String content);

    @Delete("DELETE FROM comment WHERE comment_id = #{commentId}")
    void deleteComment(@Param("commentId") Long commentId);

    @Select("SELECT writer_id FROM comment WHERE comment_id = #{commentId}")
    Long findWriterId(@Param("commentId") Long commentId);

    List<CommentResponse> findVisibleCommentsByPostId(@Param("postId") Long postId,
                                                      @Param("blockerId") Long blockerId);
}
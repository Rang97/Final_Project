package com.example.demo.domain.post.repository;

import com.example.demo.domain.post.dto.PostDetailResponse;
import com.example.demo.domain.post.dto.PostListResponse;
import com.example.demo.domain.post.entity.Post;
import org.apache.ibatis.annotations.*;

import java.util.List;

// domain/post/repository/PostMapper.java
@Mapper
public interface PostMapper {

    @Insert("INSERT INTO post (writer_id, title, content) VALUES (#{writerId}, #{title}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "postId")
    void insertPost(Post post);

    @Update("UPDATE post SET title = #{title}, content = #{content} WHERE post_id = #{postId}")
    void updatePost(Post post);

    @Delete("DELETE FROM post WHERE post_id = #{postId}")
    void deletePost(@Param("postId") Long postId);

    @Update("UPDATE post SET view_count = view_count + 1 WHERE post_id = #{postId}")
    void increaseViewCount(@Param("postId") Long postId);

    @Select("SELECT writer_id FROM post WHERE post_id = #{postId}")
    Long findWriterId(@Param("postId") Long postId);

    // ↓ 복잡한 조인 + 차단 필터는 XML에 정의 (PostMapper.xml)
    List<PostListResponse> findVisiblePosts(@Param("blockerId") Long blockerId,
                                            @Param("offset") int offset,
                                            @Param("limit") int limit);

    int countVisiblePosts(@Param("blockerId") Long blockerId);

    PostDetailResponse findPostDetail(@Param("postId") Long postId,
                                      @Param("blockerId") Long blockerId);
}
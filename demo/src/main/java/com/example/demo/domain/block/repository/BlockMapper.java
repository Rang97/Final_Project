// domain/block/repository/BlockMapper.xml.java
package com.example.demo.domain.block.repository;

import com.example.demo.domain.block.dto.BlockResponse;
import com.example.demo.domain.block.entity.Block;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BlockMapper {

    @Insert("INSERT INTO block (blocker_id, blocked_id) VALUES (#{blockerId}, #{blockedId})")
    @Options(useGeneratedKeys = true, keyProperty = "blockId")
    void insertBlock(Block block);

    @Delete("DELETE FROM block WHERE blocker_id = #{blockerId} AND blocked_id = #{blockedId}")
    int deleteBlock(@Param("blockerId") Long blockerId, @Param("blockedId") Long blockedId);

    @Select("SELECT COUNT(*) > 0 FROM block WHERE blocker_id = #{blockerId} AND blocked_id = #{blockedId}")
    boolean existsBlock(@Param("blockerId") Long blockerId, @Param("blockedId") Long blockedId);

    // 조인 필요 → XML
    List<BlockResponse> findBlockedUsers(@Param("blockerId") Long blockerId);
}
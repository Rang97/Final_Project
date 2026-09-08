// domain/block/service/BlockService.java
package com.example.demo.domain.block.service;

import com.example.demo.domain.block.dto.BlockResponse;
import com.example.demo.domain.block.entity.Block;
import com.example.demo.domain.block.repository.BlockMapper;
import com.example.demo.global.util.CurrentUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlockService {

    private final BlockMapper blockMapper;
    private final CurrentUserProvider currentUserProvider;

    @Transactional
    public void createBlock(Long blockedId) {
        Long myId = currentUserProvider.getCurrentUserId();

        if (myId.equals(blockedId)) {
            throw new IllegalArgumentException("자기 자신은 차단할 수 없습니다.");
        }
        if (blockMapper.existsBlock(myId, blockedId)) {
            throw new IllegalArgumentException("이미 차단한 유저입니다.");
        }

        Block block = Block.builder()
                .blockerId(myId)
                .blockedId(blockedId)
                .build();
        blockMapper.insertBlock(block);
    }

    @Transactional
    public void deleteBlock(Long blockedId) {
        Long myId = currentUserProvider.getCurrentUserId();
        int deleted = blockMapper.deleteBlock(myId, blockedId);
        if (deleted == 0) {
            throw new IllegalArgumentException("차단 목록에 없는 유저입니다.");
        }
    }

    public List<BlockResponse> getBlockedUsers() {
        Long myId = currentUserProvider.getCurrentUserId();
        return blockMapper.findBlockedUsers(myId);
    }
}
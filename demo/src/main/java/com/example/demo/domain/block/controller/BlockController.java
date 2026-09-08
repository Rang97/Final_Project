// domain/block/controller/BlockController.java
package com.example.demo.domain.block.controller;

import com.example.demo.domain.block.dto.BlockCreateRequest;
import com.example.demo.domain.block.dto.BlockResponse;
import com.example.demo.domain.block.service.BlockService;
import com.example.demo.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blocks")
@RequiredArgsConstructor
public class BlockController {

    private final BlockService blockService;

    @PostMapping
    public ApiResponse<Void> create(@Valid @RequestBody BlockCreateRequest req) {
        blockService.createBlock(req.getBlockedId());
        return ApiResponse.success();
    }

    @DeleteMapping("/{blockedUserId}")
    public ApiResponse<Void> delete(@PathVariable Long blockedUserId) {
        blockService.deleteBlock(blockedUserId);
        return ApiResponse.success();
    }

    @GetMapping
    public ApiResponse<List<BlockResponse>> list() {
        return ApiResponse.success(blockService.getBlockedUsers());
    }
}
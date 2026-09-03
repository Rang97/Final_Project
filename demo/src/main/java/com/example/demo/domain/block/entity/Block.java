// domain/block/entity/Block.java
package com.example.demo.domain.block.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Block {
    private Long blockId;
    private Long blockerId;
    private Long blockedId;
    private LocalDateTime createdAt;
}
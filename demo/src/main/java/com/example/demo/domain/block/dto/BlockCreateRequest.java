// domain/block/dto/BlockCreateRequest.java
package com.example.demo.domain.block.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BlockCreateRequest {
    @NotNull
    private Long blockedId;
}
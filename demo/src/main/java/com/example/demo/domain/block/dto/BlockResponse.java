// domain/block/dto/BlockResponse.java
package com.example.demo.domain.block.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlockResponse {
    private Long blockId;
    private Long blockedUserId;
    private String blockedNickname;   // 마이페이지 관리용이라 실명(닉네임) 노출
    private LocalDateTime createdAt;
}
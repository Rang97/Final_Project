package com.example.demo.domain.party.dto;

import com.example.demo.domain.party.entity.ChemistryType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

// 파티 생성 요청 DTO
public record PartyCreateRequest (
        @Schema(description = "파티 제목", example = "오늘 저녁 롤 같이 하실 분")
        @NotBlank(message = "제목은 필수입니다.")
        String title,

        @Schema(description = "게임 ID", example = "1")
        @NotNull
        Long gameId,

        @Schema(description = "최대 파티원 수(2~8명)", example = "5", minimum = "2", maximum = "8")
        @Min(2) @Max(8)
        int maxMemberCount,

        @Schema(description = "궁합 유형", example = "FIVE_ELEMENTS")
        @NotNull
        ChemistryType chemistryType
){
}

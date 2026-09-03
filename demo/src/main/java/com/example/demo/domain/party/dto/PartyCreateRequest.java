package com.example.demo.domain.party.dto;

import com.example.demo.domain.party.entity.ChemistryType;
import jakarta.validation.constraints.*;

// 파티 생성 요청 DTO
public record PartyCreateRequest (
        @NotBlank(message = "제목은 필수입니다.")
        String title,

        @NotNull
        Long gameId,

        @Min(2) @Max(8)
        int maxMemberCount,

        @NotNull
        ChemistryType chemistryType
){
}

package com.example.demo.domain.party.dto;

import com.example.demo.domain.party.entity.ChemistryType;
import com.example.demo.domain.party.entity.PartyStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

// 파티 목록 응답 DTO

public record PartyListResponse (
        Long partyId,
        Long hostId,
        String title,
        String coverUrl,
        Long gameId,
        String gameName,
        String genre,
        int maxMemberCount,
        int nowMemberCount,
        ChemistryType chemistryType,
        PartyStatus status,
        LocalDateTime createdAt
){

}

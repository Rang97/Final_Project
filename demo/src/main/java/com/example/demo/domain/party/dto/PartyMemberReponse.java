package com.example.demo.domain.party.dto;

// 파티원 목록 응답 DTO (userId + 사주 동물 닉네임)
public record PartyMemberReponse (
        Long userId,
        String nickname
){
}

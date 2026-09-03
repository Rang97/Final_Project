package com.example.demo.domain.party.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Party {

    private Long partyId;
    private Long hostId;
    private Long gameId;
    private String title;
    private String coverUrl;
    private int maxMemberCount;
    private int nowMemberCount;
    private ChemistryType chemistryType;
    private PartyStatus status;
    private LocalDateTime createdAt;

    // 파티 생성 + 사용자 정보 -> Party 객체 조립
    public Party(Long hostId, Long gameId, String title, int maxMemberCount, ChemistryType chemistryType) {
        this.hostId = hostId;
        this.gameId = gameId;
        this.title = title;
        this.maxMemberCount = maxMemberCount;
        this.chemistryType = chemistryType;
        this.status = PartyStatus.RECRUITING;
        this.nowMemberCount = 1;
    }
}

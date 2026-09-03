package com.example.demo.domain.party.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PartyMember {

    private Long partyMemberId;
    private Long partyId;
    private Long userId;
    private PartyMemberStatus status;

    // PartyMember 객체 조립
    public PartyMember(Long partyId, Long userId, PartyMemberStatus status) {
        this.partyId = partyId;
        this.userId = userId;
        this.status = status;
    }
}

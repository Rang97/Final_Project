package com.example.demo.domain.party.repository;

import com.example.demo.domain.party.entity.PartyMember;
import com.example.demo.domain.party.entity.PartyMemberStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PartyMemberMapper {

    // 파티원 조회
    public PartyMember findByPartyIdAndUserId(@Param("partyId") Long partyId,
                                              @Param("userId") Long userId);

    // 파티원 추가
    public void insertPartyMember(PartyMember partyMember);

    // 파티 현황 갱신 (추방, 탈퇴 시)
    public void updatePartyStatus(@Param("partyMemberId") Long partyMemberId,
                                  @Param("status") PartyMemberStatus status);

    // 파티 내부 APPROVED 멤버 userId 전체 목록
    List<Long> findApprovedMemberIds(@Param("partyId") Long partyId);
}

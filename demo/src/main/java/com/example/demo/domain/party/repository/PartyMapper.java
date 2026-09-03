package com.example.demo.domain.party.repository;

import com.example.demo.domain.party.entity.Party;
import com.example.demo.domain.party.entity.PartyStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PartyMapper {

    // 파티 조회
    public Party findById(Long partyId);

    // 파티 전체 조회
    public List<Party> findAllParty();

    // 파티 생성
    public void insertParty(Party party);

    // 파티 삭제
    public void deleteParty(Long partyId);

    // 파티 인원 갱신
    void updateMemberCount(@Param("partyId") Long partyId,
                           @Param("memberCount") int memberCount);

    // 파티 상태 변경: 정원 도달 시
    void updateStatus(@Param("partyId") Long partyId,
                      @Param("status") PartyStatus status);


}


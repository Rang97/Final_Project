package com.example.demo.domain.party.service;

import com.example.demo.domain.party.entity.Party;
import com.example.demo.domain.party.entity.PartyMember;
import com.example.demo.domain.party.entity.PartyMemberStatus;
import com.example.demo.domain.party.entity.PartyStatus;
import com.example.demo.domain.party.repository.PartyMapper;
import com.example.demo.domain.party.repository.PartyMemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PartyMemberService {

    private final PartyMapper partyMapper;
    private final PartyMemberMapper partyMemberMapper;


    // 파티 참가
    public void joinParty(Long partyId, Long userId) {
        Party party = partyMapper.findById(partyId);

        if (party.getStatus() == PartyStatus.RECRUITING && party.getNowMemberCount() < party.getMaxMemberCount()) {
            PartyMember existing = partyMemberMapper.findByPartyIdAndUserId(partyId, userId);
            // 새로 가입한 경우 -> insert
            if (existing == null) {
                PartyMember newMember = new PartyMember(partyId, userId, PartyMemberStatus.APPROVED);
                partyMemberMapper.insertPartyMember(newMember);
            // 추방 당했다가 가입한 경우 -> updateStatus
            } else {
                partyMemberMapper.updatePartyStatus(existing.getPartyMemberId(), PartyMemberStatus.APPROVED);
            }
            partyMapper.updateMemberCount(partyId, party.getNowMemberCount() + 1);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "가입할 수 없는 파티입니다.");
        }
    }

    // 파티 떠나기
    public void leaveParty(Long partyId, Long userId) {
        Party party = partyMapper.findById(partyId);
        if (party.getHostId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "방장은 파티를 떠날 수 없습니다, 삭제를 이용하세요.");
        }
        PartyMember partyMember = partyMemberMapper.findByPartyIdAndUserId(partyId, userId);
        if (partyMember == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "파티원이 아닙니다.");
        }
        partyMemberMapper.updatePartyStatus(partyMember.getPartyMemberId(), PartyMemberStatus.LEFT);
        partyMapper.updateMemberCount(partyId, party.getNowMemberCount() - 1);
    }
    //
}

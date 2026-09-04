package com.example.demo.domain.party.service;

import com.example.demo.domain.party.dto.PartyCreateRequest;
import com.example.demo.domain.party.dto.PartyListResponse;
import com.example.demo.domain.party.entity.*;
import com.example.demo.domain.party.repository.PartyMapper;
import com.example.demo.domain.party.repository.PartyMemberMapper;
import com.example.demo.domain.saju.dto.SajuElementDto;
import com.example.demo.domain.saju.repository.SajuMapper;
import com.example.demo.domain.saju.service.ChemistryService;
import com.example.demo.domain.saju.util.FiveElement;
import com.example.demo.domain.saju.util.FiveElementProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartyService {

    private final PartyMapper partyMapper;
    private final PartyMemberMapper partyMemberMapper;
    private final SajuMapper sajuMapper;
    private final ChemistryService chemistryService;

    // <방장>
    // 파티 생성
    @Transactional
    public Party createParty(Long userId, PartyCreateRequest request) {
        // 사주 정보 있어야 파티 가입 가능
        if (sajuMapper.findElementsByUserId(List.of(userId)).isEmpty()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "사주 정보를 등록해야 파티를 생성할 수 있습니다.");
        }

        // 파티 객체 조립
        Party party = new Party(userId,
                request.gameId(),
                request.title(),
                request.maxMemberCount(),
                request.chemistryType());
        partyMapper.insertParty(party);
        // 파티 멤버 객체 조립
        PartyMember partyMember = new PartyMember(
                party.getPartyId(),
                userId,
                PartyMemberStatus.APPROVED
        );
        partyMemberMapper.insertPartyMember(partyMember);
        return party;
    }

    // 파티 삭제
    public void deleteParty(Long userId, Long partyId) {
        Party party = partyMapper.findById(partyId);

        // 방장 아닐 시 삭제 X
        if (!party.getHostId().equals(userId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "방장만 삭제할 수 있습니다.");
        }

        partyMapper.deleteParty(partyId);
    }

    // 파티원 추방
    @Transactional
    public void deletePartyMember(Long hostId, Long partyId, Long targetUserId) {
        // 1. 방장 확인
        Party party = partyMapper.findById(partyId);

        // 2. 방장 권한 확인
        if (!party.getHostId().equals(hostId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "방장은 추방될 수 없습니다.");
        }

        // 3. 자기 자신 추방 방지
        if (targetUserId.equals(hostId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "자기 자신은 추방할 수 없습니다.");
        }

        // 4. 파티원 조회
        PartyMember partyMember = partyMemberMapper.findByPartyIdAndUserId(partyId, targetUserId);

        // 5. 없는 파티원 추방 시도 방지
        if (partyMember == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 파티원을 찾을 수 없습니다.");
        }

        // 6. 파티 상태 업데이트
        partyMemberMapper.updatePartyStatus(partyMember.getPartyMemberId(), PartyMemberStatus.KICKED);

        // 7. 파티 인원 업데이트
        partyMapper.updateMemberCount(partyId, party.getNowMemberCount() - 1);

        // 8. 파티 상태 변경 (정원 도달한 후)
        if (party.getStatus() == PartyStatus.FULL){
            partyMapper.updateStatus(partyId, PartyStatus.RECRUITING);
        }
    }

    //======================================================================

    // 파티 전체 목록 조회
    public List<PartyListResponse> getPartyList() {
        return partyMapper.findAllParty();
    }


}

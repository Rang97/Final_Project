package com.example.demo.domain.party.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Party {

    @Schema(description = "파티 ID", example = "1")
    private Long partyId;
    @Schema(description = "파티장 사용자 ID", example = "1")
    private Long hostId;
    @Schema(description = "게임 ID", example = "1")
    private Long gameId;
    @Schema(description = "파티 제목", example = "오늘 저녁 롤 같이 하실 분")
    private String title;
    @Schema(description = "게임 커버 이미지 URL")
    private String coverUrl;
    @Schema(description = "최대 파티원 수", example = "5")
    private int maxMemberCount;
    @Schema(description = "현재 파티원 수", example = "1")
    private int nowMemberCount;
    @Schema(description = "궁합 유형", example = "FIVE_ELEMENTS")
    private ChemistryType chemistryType;
    @Schema(description = "파티 상태", example = "RECRUITING")
    private PartyStatus status;
    @Schema(description = "생성 일시", example = "2026-09-04T18:30:00")
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

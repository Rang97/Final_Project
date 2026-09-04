package com.example.demo.domain.saju.controller;

import com.example.demo.domain.saju.dto.SajuElementDto;
import com.example.demo.domain.saju.repository.SajuMapper;
import com.example.demo.domain.saju.service.ChemistryService;
import com.example.demo.domain.saju.util.FiveElementProfile;
import com.example.demo.domain.saju.util.GroupElementSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saju")
@RequiredArgsConstructor
public class ChemistryController {

    private final SajuMapper sajuMapper;
    private final ChemistryService chemistryService;

    // 궁합 계산
    @GetMapping("/chemistry")
    public GroupElementSummary getGroupChemistry(@RequestParam List<Long> userIds){
        List<SajuElementDto> rows = sajuMapper.findElementsByUserId(userIds);

        List<FiveElementProfile> profiles = rows.stream()
                .map(SajuElementDto::toProfile)
                .toList();
        return chemistryService.summarizeGroup(profiles);
    }
}

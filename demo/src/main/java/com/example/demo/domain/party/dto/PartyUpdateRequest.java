package com.example.demo.domain.party.dto;

import com.example.demo.domain.party.entity.ChemistryType;
import io.micrometer.common.lang.Nullable;

public record PartyUpdateRequest (
        String title,
        ChemistryType chemistryType){
}

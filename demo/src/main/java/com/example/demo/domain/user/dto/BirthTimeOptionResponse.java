package com.example.demo.domain.user.dto;

import com.example.demo.domain.user.entity.BirthTimeBranch;

public record BirthTimeOptionResponse(String value, String label, String timeRange) {

    public static BirthTimeOptionResponse from(BirthTimeBranch branch) {
        return new BirthTimeOptionResponse(branch.name(), branch.getLabel(), branch.getTimeRange());
    }
}

package com.ercall.server.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class MatchRequest {
    private String majorInjuryName;

    private String place;
}

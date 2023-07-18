package com.ercall.server.dto;

import lombok.*;

import javax.persistence.Entity;

@Builder
@Getter
public class MatchDto {
    private String majorInjuryName;
    private String place;
}

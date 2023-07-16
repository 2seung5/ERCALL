package com.ercall.server.dto;

import lombok.Data;

@Data
public class GeoLocation {

    private String x; // 경도(longitude)
    private String y; // 위도(latitude)

    public GeoLocation(String x, String y) {
        this.x = x;
        this.y = y;
    }
}

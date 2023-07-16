package com.ercall.server.dto;

import lombok.Data;

import java.util.Map;

@Data
public class DocumentAttribute {

    private String addressName;
    private String categoryGroupName;
    private String categoryGroupCode;
    private String categoryName;
    private String distance;
    private String id;
    private String phone;
    private String placeName;
    private String placeUrl;
    private String roadAddressName;
    private String x;
    private String y;

    private DocumentAttribute(Map<String, String> documents) {
        this.addressName = documents.get("address_name");               // 주소
        this.categoryGroupCode = documents.get("category_group_code");  // 카테고리 그룹코드
        this.categoryGroupName = documents.get("category_group_name");  // 카테고리 그룹명
        this.categoryName = documents.get("category_name");             // 카테고리명
        this.distance = documents.get("distance");                      // 거리
        this.id = documents.get("id");                                  // 식별자
        this.phone = documents.get("phone");                            // 폰번호
        this.placeName = documents.get("place_name");                   // 장소명
        this.placeUrl = documents.get("place_url");                     // URL
        this.roadAddressName = documents.get("road_address_name");      // 도로명주소
        this.x = documents.get("x");
        this.y = documents.get("y");
    }

    public static DocumentAttribute ofKakao(Map<String, String> documents) {
        return new DocumentAttribute(documents);
    }

}

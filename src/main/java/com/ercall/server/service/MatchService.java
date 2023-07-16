package com.ercall.server.service;


import com.ercall.server.dto.DocumentAttribute;
import com.ercall.server.dto.GeoLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.swing.text.Document;
import java.lang.reflect.Array;
import java.net.URI;
import java.util.*;

@Slf4j
@Service
public class MatchService {

    private final static String REQUEST_URI = "https://dapi.kakao.com/v2/local/search/keyword.JSON";

    @Value(value = "${kakao.restApi.key}")
    private String REST_API_KEY;

    public List<DocumentAttribute> findNearByEmergencyRoom(GeoLocation geoLocation){
        log.info("RESTAPIKEY = {}", "KakaoAK " + REST_API_KEY);
        // set headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + REST_API_KEY);

        HttpEntity httpEntity = new HttpEntity(headers);

        // set parameters
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("query", "응급실");
        parameters.add("category_group_code", "HP8"); // 병원 카테고리로 결과 필터링
        parameters.add("x", geoLocation.getX());
        parameters.add("y", geoLocation.getY());
        parameters.add("sort", "distance");

        // set URI
        URI uri = UriComponentsBuilder.fromUriString(REQUEST_URI)
                .queryParams(parameters)
                .queryParam("radius", Integer.valueOf(10000))       // 10km 범위 병원 검색
                .encode()
                .build().toUri();

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> body = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, HashMap.class).getBody();

        ArrayList<Object> documents = (ArrayList<Object>) body.get("documents");
        ArrayList<DocumentAttribute> mapList = new ArrayList<>();

        for (int i = 0; i < documents.size(); i++) {
            Map<String, String> map = (Map<String, String>) documents.get(i);
            mapList.add(DocumentAttribute.ofKakao(map));
        }

        return mapList;
    }

}

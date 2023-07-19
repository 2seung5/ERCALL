package com.ercall.server.service;


import com.ercall.server.dto.DocumentAttribute;
import com.ercall.server.dto.GeoLocation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class AutoMatchService {

    private static String Base_URI = "http://localhost:8081"; // ER 서버의 주소
    private final static String REQUEST_URI = "https://dapi.kakao.com/v2/local/search/keyword.JSON";

    @Value(value = "${kakao.restApi.key}")
    private String REST_API_KEY;

    public String doAutoMatch(GeoLocation geoLocation) {
        
        List<DocumentAttribute> emergencyRoomList = findNearByEmergencyRoom(geoLocation); // 주변 10km 응급실 조회
        requestER(emergencyRoomList); // 요청 보내기
        return " ";
    }


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

    // 1분마다 범위를 넓혀감
    public void requestER(List<DocumentAttribute> documentAttributes) {

        WebClient webClient = WebClient.create(Base_URI);
        for(int i=1; i <= 4; i++){
            int requestBoundary = i * 3000;

            for (DocumentAttribute documentAttribute : documentAttributes) {
                if (Integer.parseInt(documentAttribute.getDistance()) < requestBoundary) {      // 현재 검색 범위의 응급실에 요청을 보냄

                    Mono<String> mono = webClient.post().retrieve().bodyToMono(String.class);
                    mono.subscribe();
                    documentAttributes.remove(documentAttribute);
                }
            }

            try
            {
                Thread.sleep(60000);
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


}

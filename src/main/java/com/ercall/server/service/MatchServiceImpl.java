package com.ercall.server.service;

import com.ercall.server.dto.MatchRequest;
import com.ercall.server.dto.MkiosktyDto;
import com.ercall.server.repository.MatchRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import org.json.simple.JSONObject;

import java.net.URI;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatchServiceImpl implements MatchService {
    @Autowired
    private final MatchRepository matchRepository;


    @Override
    public List<String> getErCode(MatchRequest matchRequest) {
        //String key = "APkKK1aI0luBz90HOfCjbTPn7JawJDfBR9ahisWA3FfP5UU0yeYXuD4t5ZYAALS%2FxVr3I9E4Ww8jCd%2FYKfy1nw%3D%3D";  //인코딩 키 웹이나 포스트맨에선 인코딩키로 요청
        String key = "APkKK1aI0luBz90HOfCjbTPn7JawJDfBR9ahisWA3FfP5UU0yeYXuD4t5ZYAALS/xVr3I9E4Ww8jCd/YKfy1nw=="; //디코딩 키  서버에서는 디코딩키로 요청

        int mkioskty = 0;
        //질환 번호 찾기
        if (matchRequest.getMajorInjuryName().contains("뇌출혈")) {
            mkioskty = 1;
        }
        String disease = Integer.toString(mkioskty);
        //위치 시,구로 분리
        String[] place = matchRequest.getPlace().split(" ");
        Arrays.toString(place);

        RestTemplate restTemplate = new RestTemplate();


        String base = "http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire";

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();

        queryParams.add("ServiceKey", "APkKK1aI0luBz90HOfCjbTPn7JawJDfBR9ahisWA3FfP5UU0yeYXuD4t5ZYAALS/xVr3I9E4Ww8jCd/YKfy1nw==");
        queryParams.add("STAGE1", "서울특별시");
        queryParams.add("STAGE2", place[1]);
        queryParams.add("_type", "json");
        queryParams.add("SM_TYPE", Integer.toString(mkioskty));
        queryParams.add("pageNo", "1");
        queryParams.add("numOfRows", "10");

        URI uri = UriComponentsBuilder
                .fromUriString(base)
                .queryParams(queryParams)
                .encode()
                .build()
                .toUri();

        log.info("URI : {}", uri);

        ResponseEntity<String> exchange = restTemplate.exchange(uri, HttpMethod.GET, null, String.class);
        log.info("{}", exchange.getBody());

        //질환 진료 가능한 병원 코드 리스트 가져오기
        List<String> codeParam = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(exchange.getBody()).get("response").get("body").get("items").get("item");
            jsonNode.forEach(t -> {
                if (t.get("MKioskTy1").toString().contains("Y")) {
                    codeParam.add(t.get("dutyName").toString());
                }
                ;
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        return codeParam;
    }

    @Override
    public Set<String> getErName(MatchRequest matchRequest) {

        int mkioskty = 0;
        //질환 번호 찾기

        //위치 시,구로 분리
        String[] place = matchRequest.getPlace().split(" ");
        Arrays.toString(place);
        List<String> codeParam = getErCode(matchRequest);

        RestTemplate restTemplate = new RestTemplate();

        String base = "http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire";

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();

        queryParams.add("ServiceKey", "APkKK1aI0luBz90HOfCjbTPn7JawJDfBR9ahisWA3FfP5UU0yeYXuD4t5ZYAALS/xVr3I9E4Ww8jCd/YKfy1nw==");
        queryParams.add("STAGE1", "서울특별시");
        queryParams.add("STAGE2", place[1]);
        queryParams.add("_type", "json");
        queryParams.add("pageNo", "1");
        queryParams.add("numOfRows", "10");

        URI uri = UriComponentsBuilder
                .fromUriString(base)
                .queryParams(queryParams)
                .encode()
                .build()
                .toUri();

        //log.info("URI : {}", uri);

        ResponseEntity<String> exchange = restTemplate.exchange(uri, HttpMethod.GET, null, String.class);
        //log.info("{}", exchange.getBody());

        //위 코드와 같은 질환 진료 가능한 병원 이름 리스트 가져오기
        Set<String> nameParam = new HashSet<>();  //병원 이름
        List<String> hpidParam = new ArrayList<>();  //병원 코드
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(exchange.getBody()).get("response").get("body").get("items").get("item");
            jsonNode.forEach(t -> {
                    hpidParam.add(t.get("hpid").toString());
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        for (String s : hpidParam){
            for (String c: codeParam){
                if (s.equals(c)){
                    try {
                        JsonNode jsonNode = objectMapper.readTree(exchange.getBody()).get("response").get("body").get("items").get("item");
                        jsonNode.forEach(t -> {
                            nameParam.add(t.get("dutyName").toString());
                        });
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return nameParam;
    }


    //json을 dto로 변환
    private MkiosktyDto makeMkiosktyDto(JSONObject item) {
        MkiosktyDto dto = MkiosktyDto.builder().
                mkioskty1((String) item.get("mkioskty1")).
                build();
        return dto;
    }

}


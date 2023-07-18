package com.ercall.server.service;

import com.ercall.server.dto.MatchRequest;
import com.ercall.server.repository.MatchRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService{
    @Autowired
    private final MatchRepository matchRepository;

    @Override
    public ResponseEntity<String> getErName(MatchRequest matchRequest)  {
        String key = "APkKK1aI0luBz90HOfCjbTPn7JawJDfBR9ahisWA3FfP5UU0yeYXuD4t5ZYAALS%2FxVr3I9E4Ww8jCd%2FYKfy1nw%3D%3D";
        //HashMap<String, Object> result = new HashMap<String, Object>();
        String jsonInString = "";

        RestTemplate restTemplate = new RestTemplate();

        String url = "http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire";
        MultiValueMap<String,String> queryParams = new LinkedMultiValueMap<String,String>();

        queryParams.add("STAGE1", matchRequest.getPlace());
        queryParams.add("STAGE2", matchRequest.getPlace());
        queryParams.add("SM_TYPE", "1");       //질환번호 (mkioskty)
        queryParams.add("pageNo", "1");
        queryParams.add("numOfRows", "10");

        queryParams.add("ServiceKey","APkKK1aI0luBz90HOfCjbTPn7JawJDfBR9ahisWA3FfP5UU0yeYXuD4t5ZYAALS%2FxVr3I9E4Ww8jCd%2FYKfy1nw%3D%3D");


        URI uri = UriComponentsBuilder.fromHttpUrl(url).queryParams(queryParams).encode().build().toUri();
        ResponseEntity<String> result = restTemplate.exchange(uri.toString(), HttpMethod.GET, null, String.class);
        //result.put("statusCode", resultMap.getStatusCodeValue()); //http status code를 확인
        //result.put("header", resultMap.getHeaders()); //헤더 정보 확인
        //result.put("body", resultMap.getBody()); //실제 데이터 정보 확인

        //데이터를 제대로 전달 받았는지 확인 string형태로 파싱해줌
        return result;
        //System.out.println(jsonInString);


    }

}

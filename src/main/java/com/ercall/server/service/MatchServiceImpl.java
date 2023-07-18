package com.ercall.server.service;

import com.ercall.server.dto.MatchRequest;
import com.ercall.server.dto.MkiosktyDto;
import com.ercall.server.repository.MatchRepository;

import lombok.RequiredArgsConstructor;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService{
    @Autowired
    private final MatchRepository matchRepository;

    @Override
    public String getErName(MatchRequest matchRequest)  {
        //String key = "APkKK1aI0luBz90HOfCjbTPn7JawJDfBR9ahisWA3FfP5UU0yeYXuD4t5ZYAALS%2FxVr3I9E4Ww8jCd%2FYKfy1nw%3D%3D";  //인코딩 키 웹이나 포스트맨에선 인코딩키로 요청
        String key = "APkKK1aI0luBz90HOfCjbTPn7JawJDfBR9ahisWA3FfP5UU0yeYXuD4t5ZYAALS/xVr3I9E4Ww8jCd/YKfy1nw=="; //디코딩 키  서버에서는 디코딩키로 요청
        //HashMap<String, Object> result = new HashMap<String, Object>();
        String jsonInString = "";
        int mkioskty = 0;
        //질환 번호 찾기
        if(matchRequest.getMajorInjuryName().contains("뇌출혈"))
        {
            mkioskty = 1;
        }
        String disease = Integer.toString(mkioskty);
        //위치 시,구로 분리
        String[] place = matchRequest.getPlace().split("\\s+");
        Arrays.toString(place);

        RestTemplate restTemplate = new RestTemplate();

        String url = "http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire?";
        MultiValueMap<String,String> queryParams = new LinkedMultiValueMap<String,String>();

        queryParams.add("ServiceKey","APkKK1aI0luBz90HOfCjbTPn7JawJDfBR9ahisWA3FfP5UU0yeYXuD4t5ZYAALS/xVr3I9E4Ww8jCd/YKfy1nw==");
        queryParams.add("&STAGE1", place[0]);                          //시
        queryParams.add("&STAGE2", place[1]);                          //구
        queryParams.add("&_type", "json");
        queryParams.add("&SM_TYPE", Integer.toString(mkioskty));       //질환번호 (mkioskty)
        queryParams.add("&pageNo", "1");
        queryParams.add("&numOfRows", "10");

        System.out.println(place[0]);
        System.out.println(mkioskty);

        URI uri = UriComponentsBuilder.fromHttpUrl(url).queryParams(queryParams).build().toUri();
        ResponseEntity<String> exchange = restTemplate.exchange(uri.toString(), HttpMethod.GET, null, String.class);

        try {


            String jsonString = restTemplate.getForObject(uri.toString(), String.class);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);
            // 가장 큰 JSON 객체 response 가져오기
            JSONObject jsonResponse = (JSONObject) jsonObject.get("response");

            // 그 다음 body 부분 파싱
            JSONObject jsonBody = (JSONObject) jsonResponse.get("body");

            // 그 다음 위치 정보를 배열로 담은 items 파싱
            JSONObject jsonItems = (JSONObject) jsonBody.get("items");

            // items는 JSON임, 이제 그걸 또 배열로 가져온다
            JSONArray jsonItemList = (JSONArray) jsonItems.get("item");



            List<MkiosktyDto> result = new ArrayList<>();

            for (Object o : jsonItemList) {
                JSONObject item = (JSONObject) o;
                result.add(makeMkiosktyDto(item));
            }

            //여기서 result 에 있는 리스트중에서 mkioskty1값이 Y인것 찾고 Y일때 기관명 찾고 기관명 return

        }
        catch (ParseException e){
            System.out.println("실패");
            e.printStackTrace();
        }
        return "응급실 이름리스트";
    }


//json을 dto로 변환
    private MkiosktyDto makeMkiosktyDto(JSONObject item) {
        MkiosktyDto dto = MkiosktyDto.builder().
                mkioskty1((String) item.get("mkioskty1")).
                build();
        return dto;
    }

}

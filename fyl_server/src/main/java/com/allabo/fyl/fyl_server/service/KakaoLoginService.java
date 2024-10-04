package com.allabo.fyl.fyl_server.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@PropertySource({"classpath:/application.properties"})
public class KakaoLoginService {

    // .env 파일에서 환경 변수 불러오기
    private final String KAKAO_TOKEN_REQUEST_URL = "https://kauth.kakao.com/oauth/token";
    @Value("${KAKAO_REST_API_KEY}")
    private String KAKAO_REST_API_KEY;
    @Value("${KAKAO_REDIRECT_URI}")
    private String KAKAO_REDIRECT_URI;


    public String getKakaoAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 요청 파라미터 설정
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", KAKAO_REST_API_KEY);
        params.add("redirect_uri", KAKAO_REDIRECT_URI);
        params.add("code", code); // 카카오 인가 코드

        // HttpEntity 생성 (헤더와 파라미터)
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        // 카카오 서버에 POST 요청하여 토큰 받아오기
        ResponseEntity<Map> response = restTemplate.exchange(
                KAKAO_TOKEN_REQUEST_URL,
                HttpMethod.POST,
                request,
                Map.class
        );

        // 응답에서 access_token 추출
        Map<String, Object> responseBody = response.getBody();
        if (responseBody != null && responseBody.get("access_token") != null) {
            return (String) responseBody.get("access_token");
        }
        return null;
    }
}

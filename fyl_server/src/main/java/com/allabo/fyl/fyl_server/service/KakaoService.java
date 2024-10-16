package com.allabo.fyl.fyl_server.service;

import com.allabo.fyl.fyl_server.dto.KakaoUserInfoDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KakaoService {

    private final String KAKAO_USERINFO_URL = "https://kauth.kakao.com/oauth/token";

    public String getUserNameFromKakaoToken(String kakaoToken) throws Exception {
        // Kakao API에 인증 요청을 보낼 RestTemplate 사용
        RestTemplate restTemplate = new RestTemplate();

        // Authorization 헤더에 Bearer 토큰 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + kakaoToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // 카카오 서버로부터 사용자 정보를 받아옴
        ResponseEntity<KakaoUserInfoDTO> response = restTemplate.exchange(
                KAKAO_USERINFO_URL,
                HttpMethod.GET,
                entity,
                KakaoUserInfoDTO.class
        );

        // 요청이 성공했는지 확인
        if (response.getStatusCode() == HttpStatus.OK) {
            KakaoUserInfoDTO userInfo = response.getBody();
            if (userInfo != null) {
                // 카카오 사용자 정보에서 닉네임 또는 사용자 ID를 가져옴
                return userInfo.getKakao_account().getProfile().getEmail();
            } else {
                throw new Exception("Failed to retrieve user info from Kakao");
            }
        } else {
            throw new Exception("Failed to authenticate Kakao token");
        }
    }
}

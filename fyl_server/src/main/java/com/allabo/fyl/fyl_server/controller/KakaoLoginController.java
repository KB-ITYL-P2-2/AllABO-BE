package com.allabo.fyl.fyl_server.controller;

import com.allabo.fyl.fyl_server.service.KakaoLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class KakaoLoginController {
    @Autowired
    private KakaoLoginService kakaoLoginService;

    @PostMapping("/kakao-redirect")
    public ResponseEntity<Map<String, Object>> callback(@RequestBody Map<String, String> map) {
//        System.out.println("kakao auth code: " + map.get("code"));

        // 서비스로 인가 코드 전달하여 토큰 요청
        String accessToken = kakaoLoginService.getKakaoAccessToken(map.get("code"));

        Map<String, Object> response = kakaoLoginService.getUserInfo(accessToken);

        if (accessToken != null) {
//            System.out.println("kakao access token: " + accessToken);
//            response.put("access_token", accessToken);
            System.out.println(response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            System.out.println("kakao access request fail");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

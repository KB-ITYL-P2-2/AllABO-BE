package com.allabo.fyl.fyl_server.controller;

import com.allabo.fyl.fyl_server.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kakao")
@RequiredArgsConstructor
public class KakaoController {
    private final KakaoService kakaoService;

    @PostMapping("/login")
    public ResponseEntity<String> kakaoLogin(@RequestBody String kakaoToken) {
        try {
            // KakaoService를 사용해 카카오 사용자 정보에서 이메일을 가져옴
            String email = kakaoService.getUserNameFromKakaoToken(kakaoToken);

            // 여기서 이메일을 기반으로 추가적인 로직 수행 가능 (ex. JWT 생성 등)
            return ResponseEntity.ok("카카오 로그인 성공, 사용자 이메일: " + email);
        } catch (Exception e) {
            // 예외 발생 시 오류 메시지 반환
            return ResponseEntity.status(401).body("카카오 로그인 실패: " + e.getMessage());
        }
    }
}

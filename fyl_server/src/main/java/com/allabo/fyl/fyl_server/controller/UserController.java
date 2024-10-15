package com.allabo.fyl.fyl_server.controller;

import com.allabo.fyl.fyl_server.dto.UserDTO;
import com.allabo.fyl.fyl_server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 토큰에서 받은 userId로 사용자 정보 조회
    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile(Authentication authentication) {
        try {
            UserDTO userDto = userService.getUserProfile(authentication.getName());
            return ResponseEntity.ok(userDto);
        }catch(Exception e){
            return ResponseEntity.internalServerError().body(null);
        }
    }
    // 사용자 프로필 수정 요청 처리
    @PutMapping("/profile")
    public ResponseEntity<String> updateUserProfile(Authentication authentication, @RequestBody UserDTO userDto) {
        try {
            userService.updateUserProfile(userDto);
            return ResponseEntity.ok("프로필 정보 수정 성공");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("프로필 정보 수정 실패");
        }
    }
}

package com.allabo.fyl.fyl_server.controller;

import com.allabo.fyl.fyl_server.dto.UserDTO;
import com.allabo.fyl.fyl_server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 토큰에서 받은 userId로 사용자 정보 조회
    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile(@RequestParam String userId) {
        try {
            UserDTO userDto = userService.getUserProfile(userId);
            return ResponseEntity.ok(userDto);
        }catch(Exception e){
            return ResponseEntity.internalServerError().body(null);
        }
    }
}

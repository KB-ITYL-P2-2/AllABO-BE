package com.allabo.fyl.fyl_server.controller;

import com.allabo.fyl.fyl_server.dto.UserDto;
import com.allabo.fyl.fyl_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 토큰에서 받은 userId로 사용자 정보 조회
    @GetMapping("/profile")
    public UserDto getUserProfile(@RequestParam String userId) {
        return userService.getUserProfile(userId);
    }
}

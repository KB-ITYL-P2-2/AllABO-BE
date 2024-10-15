package com.allabo.fyl.fyl_server.controller;

import com.allabo.fyl.fyl_server.dto.UserDTO;
import com.allabo.fyl.fyl_server.dto.UserResetDTO;
import com.allabo.fyl.fyl_server.exception.InvalidInputException;
import com.allabo.fyl.fyl_server.exception.UserNotFoundException;
import com.allabo.fyl.fyl_server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    public ResponseEntity<String> updateUserProfile(Authentication authentication) {
        UserDTO userDto = userService.getUserProfile(authentication.getName());

        try {
            userService.updateUserProfile(userDto);
            return ResponseEntity.ok("프로필 정보 수정 성공");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("프로필 정보 수정 실패");
        }
    }

//    @PostMapping("/reset-password")
//    public ResponseEntity<String> ResetPwd(@RequestBody UserResetDTO dto) {
//        try {
//            userService.updateUserPassword(dto);
//            return ResponseEntity.ok(dto.getId()+"님의 패스워드가 변경되었습니다.");
//        }catch(Exception e){
//            return ResponseEntity.internalServerError().body(null);
//        }
//    }

    @PostMapping("/validate")
    public ResponseEntity<Map<String, String>> validateUser(@RequestBody UserResetDTO dto) {
        UserDTO userDto;
        try {
            userDto = userService.getUserProfile(dto.getId());
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "사용자를 찾을 수 없습니다."));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("message", "프로필 정보 조회 실패"));
        }

        if (!userDto.getPhoneNumber().equals(dto.getPhoneNumber())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "전화번호가 일치하지 않습니다."));
        }

        return ResponseEntity.ok()
                .body(Map.of("message", "검증 성공"));
    }
    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPwd(@RequestBody UserResetDTO dto) {
        // 비밀번호 재설정 로직
        try {
            userService.resetUserPassword(dto.getId(), dto.getPwd());
            return ResponseEntity.ok()
                    .body(Map.of("message", "비밀번호 재설정 성공"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("message", "비밀번호 재설정 실패: " + e.getMessage()));
        }
    }
}

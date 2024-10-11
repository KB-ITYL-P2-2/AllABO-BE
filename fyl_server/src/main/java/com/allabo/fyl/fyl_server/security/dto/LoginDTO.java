package com.allabo.fyl.fyl_server.security.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Slf4j
public class LoginDTO {
    private String id;
    private String pwd;

    public static LoginDTO of(HttpServletRequest request) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // 요청 본문을 파싱하여 LoginDTO로 변환
            return objectMapper.readValue(request.getInputStream(), LoginDTO.class);
        } catch (IOException e) {
            // 예외가 발생하면 로그를 남기고 BadCredentialsException을 발생시킴
            log.error("JSON 데이터 파싱 중 오류 발생: {}", e.getMessage());
            throw new BadCredentialsException("잘못된 로그인 요청입니다. id 또는 pwd가 없습니다.");
        }
    }
}

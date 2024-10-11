package com.allabo.fyl.fyl_server.security.handler;

import com.allabo.fyl.fyl_server.security.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class MyLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JWTUtil jwtUtil;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("Login Success Handler......................");

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        log.info("authentication={}", authentication );
        log.info("authentication.getPrincipal()={}", authentication.getPrincipal());
        log.info("authentication.getCredentials()={}", authentication.getCredentials());
        log.info("authentication.getName()={}", authentication.getName());
        //username

        Map<String, Object> claim = Map.of("my", authentication.getName());
        //Access Token 유효기간 1일
        String accessToken = jwtUtil.generateToken(claim, 1);
//        //Refresh Token 유효기간 10일
        String refreshToken = jwtUtil.generateToken(claim, 10);


        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, String> keyMap = Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken);

        try {
            String jsonStr = objectMapper.writeValueAsString(keyMap);
            response.getWriter().println(jsonStr);
        } catch (IOException e) {
            e.printStackTrace(); // 예외 처리
        }

    }
}

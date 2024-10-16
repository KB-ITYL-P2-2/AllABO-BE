package com.allabo.fyl.fyl_server.security.filter;

import com.allabo.fyl.fyl_server.security.dto.LoginDTO;
import com.allabo.fyl.fyl_server.security.handler.MyLoginFailureHandler;
import com.allabo.fyl.fyl_server.security.handler.MyLoginSuccessHandler;
import com.allabo.fyl.fyl_server.security.util.JWTUtil;
import com.allabo.fyl.fyl_server.service.KakaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
@Slf4j
public class MyLoginFilter
        extends
        UsernamePasswordAuthenticationFilter
//        AbstractAuthenticationProcessingFilter
{
    private final KakaoService kakaoService;

    public MyLoginFilter(
            AuthenticationManager authenticationManager,
            String filterProcessesUrl,KakaoService kakaoService
    ) {
        super(authenticationManager);
        this.kakaoService = kakaoService;
        log.error("MyLoginFilter");
        setFilterProcessesUrl(filterProcessesUrl); // POST 로그인 요청 url
    }


    private Map<String, String> getJSonData(HttpServletRequest request) {
        log.info("--요청전달된 JSON 데이터를 분석해서 id, pw 전달 값을 Map으로 처리--");

        ObjectMapper objectMapper = new ObjectMapper();
        //JSON 데이터를 분석해서 id, pw 전달 값을 Map으로 처리
        Map<String, String> jsonData = new HashMap<>();
        try {
            Reader reader = new InputStreamReader(request.getInputStream());
            jsonData = objectMapper.readValue(reader, Map.class);
            log.info("jsonData:{}", jsonData);
            return jsonData;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("----MyLoginFilter  attemptAuthentication----");


        if (request.getMethod().equalsIgnoreCase("GET")) {
            log.info("GET METHOD NOT SUPPORT");
            return null;
        }
        log.info("request.getMethod()={}", request.getMethod());

        // 요청 헤더나 파라미터를 통해 카카오 로그인을 구분
        String loginType = request.getHeader("loginType");

        if ("kakao".equalsIgnoreCase(loginType)) {
            // 카카오 로그인 처리 로직 추가
            String kakaoToken = getJSonData(request).get("kakaoToken");

            // 카카오 서버에 토큰 유효성 검사를 요청하고, 해당 정보를 바탕으로 인증 처리
            // 사용자 정보를 가져와서 UsernamePasswordAuthenticationToken 생성 및 인증 요청
            // 예시로, username을 카카오 프로필에서 가져온다고 가정
            String username = null;
            try {
                username = kakaoService.getUserNameFromKakaoToken(kakaoToken);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null);

            return getAuthenticationManager().authenticate(authenticationToken);
        }


        log.info("--JSON 데이터로 UsernamePasswordAuthenticationToken생성--");
        Map<String, String> jsonData = getJSonData(request);
        String username = jsonData.get("id");
        String password=  jsonData.get("pwd");
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(username,password);//authenticate token 발급
//
//        String username = request.getParameter("id");
//        String password = request.getParameter("pwd");
//
//        UsernamePasswordAuthenticationToken authenticationToken
//                = new UsernamePasswordAuthenticationToken(username, password);

        log.info("--AuthenticationManager에게 인증 요청 getAuthenticationManager()={}, id={}, pwd={}--", getAuthenticationManager(), username, password);

        return getAuthenticationManager().authenticate(authenticationToken);
    }
}

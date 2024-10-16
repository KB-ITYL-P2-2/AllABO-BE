package com.allabo.fyl.fyl_server.security.filter;
import com.allabo.fyl.fyl_server.security.exception.AccessTokenException;
import com.allabo.fyl.fyl_server.security.mapper.CustomerMapper;
import com.allabo.fyl.fyl_server.security.util.JWTUtil;
import com.allabo.fyl.fyl_server.service.UserFinancialsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
//헤더 통해 온 토큰 인증
@Slf4j
@RequiredArgsConstructor
//@PropertySource({"classpath:/application.properties"})
public class MyTokenCheckFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JWTUtil jwtUtil;
    private final CustomerMapper customerMapper;  // CustomerMapper 인터페이스 from mybatis to Spring 주입
    private final UserFinancialsService service;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        //요청 URI가 "/assets/analyze"로 시작하지 않으면, 토큰 검증을 건너뛰고 필터 체인을 계속 진행
        if (!path.startsWith("/assets/analyze") &&
                !path.startsWith("/assets/loan") &&
                !path.startsWith("/assets/ratio") &&
                !path.startsWith("/assets/saving") &&
                !path.startsWith("/assets/income-level") &&
                !path.startsWith("/assets/saving-ratio") &&
                !path.startsWith("/assets/expenditure") &&
                !path.startsWith("/assets/portfolio") &&
                !path.startsWith("/api/user/reset-password") &&
                !path.startsWith("/api/user/validate") &&
                !path.startsWith("/insurance/rebalancing") &&
                !path.startsWith("/api/favorites/add") &&
                !path.startsWith("/api/favorites/remove") &&
                !path.startsWith("/api/favorites/list") &&
                !path.startsWith("/api/favorites/mapping-list") &&
                !path.startsWith("/api/user/profile") &&
                !path.startsWith("/assets/plan") &&
                !path.startsWith("/assets/after-year")
        )
        {
            log.info("skip MyTokenCheckFilterfilter.....path:{}", path);
            filterChain.doFilter(request, response);
            return;
        }
        log.info("Token Check Filter..........................");
        log.info("JWTUtil: {}",jwtUtil);
//        filterChain.doFilter(request,response);
        try{
            Map<String, Object> payload = validateAccessToken(request);//기한만료,탈취 체크
            log.info("payload :{} ", payload);//토큰 정상적이면 payload가 옴
            //id
            String id = (String)payload.get("my");

            log.info("id: " + id);

            UserDetails userDetails = userDetailsService.loadUserByUsername(id);//auth
//            UserFinancialsDTO dto =
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);


            filterChain.doFilter(request,response);
        }catch(AccessTokenException accessTokenException){
            AccessTokenException.TOKEN_ERROR token_error = accessTokenException.getToken_error();
            log.info("AccessTokenException token_error.getStatus()={}", token_error.getStatus());
            response.setStatus(token_error.getStatus());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = new HashMap<>();
            map.put("msg", token_error.getMsg());
            map.put("time", new Date());
            String responseStr = mapper.writeValueAsString(map);
            response.getWriter().println(responseStr);
        }
    }

    private Map<String, Object> validateAccessToken(HttpServletRequest request) throws AccessTokenException {
        String headerStr = request.getHeader("Authorization");
        log.info("headerStr: {}, headerStr.length()={}", headerStr, headerStr.length());
        if(headerStr == null  || headerStr.length() < 8){
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.UNACCEPT);
        }

        //Bearer 생략
        String tokenType = headerStr.substring(0,6);
        String tokenStr =  headerStr.substring(7);

        if(tokenType.equalsIgnoreCase("Bearer") == false){
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADTYPE);
        }
        log.info("tokenStr={}", tokenStr);
        try{
            Map<String, Object> values = jwtUtil.validateToken(tokenStr);
            return values;
        }catch(MalformedJwtException malformedJwtcException){//포맷 잘못된 경우
            log.error("MalformedJwtException----------------------");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.MALFORM);
        }catch(SignatureException signatureException){//access key 잘못된 경우
            log.error("SignatureException----------------------");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADSIGN);
        }catch(ExpiredJwtException expiredJwtException){//access key 기한 만료
            log.error("ExpiredJwtException----------------------");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.EXPIRED);
        }
    }
}
package com.allabo.fyl.fyl_server.security.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JWTUtil {
    //    static private final int TOKEN_EXPIRATION = 60*24 ; // 60분*24 :1일
    static private final int TOKEN_EXPIRATION = 1 ; // 1분
    private String secretKey = "충분히 긴 임의의(랜덤한) 비밀키 문자열 배정 ";
    private Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    /**
     * 토큰 문자열 생성
     * @param valueMap
     * @param days
     * @return
     */
    public String generateToken(Map<String, Object> valueMap, int days){


//        return null;

        //헤더 부분
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ","JWT");
        headers.put("alg","HS256");

        //payload 부분 설정
        Map<String, Object> payloads = new HashMap<>();
        payloads.putAll(valueMap);

        //테스트 시에는 짧은 유효 기간
        int time = TOKEN_EXPIRATION * days;
        String jwtStr = Jwts.builder()
                .setHeader(headers)
                .setClaims(payloads)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(time).toInstant()))
                .signWith(key)
//                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes(StandardCharsets.UTF_8))
                .compact();
        log.info("generateToken  jwtStr={}", jwtStr);
        return jwtStr;
    }

    /**
     * 토큰 검증
     * @param token
     * @return 클레임
     * @throws JwtException
     */
    public Map<String, Object> validateToken(String token)throws JwtException {

        Map<String, Object> claim = null;

        claim = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token) // 파싱 및 검증, 실패 시 에러
                .getBody();
        return claim;
    }

}
package com.allabo.fyl.kb_server.assets.security.util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JWTUtil {
    //    static private final int TOKEN_EXPIRATION = 60*24 ; // 60분*24 :1일
    static private final int TOKEN_EXPIRATION = 1 ; // 1분
    private String secretKey = "충분히 긴 임의의(랜덤한) 비밀키 문자열 배정 ";
    private Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    // JWT 토큰에서 클레임을 추출하는 메서드
    public Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey.getBytes()) // 서명 검증을 위한 비밀키 설정
                    .parseClaimsJws(token)
                    .getBody(); // 클레임 반환
        } catch (SignatureException e) {
            throw new RuntimeException("Invalid JWT signature");
        } catch (Exception e) {
            throw new RuntimeException("Invalid token");
        }
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
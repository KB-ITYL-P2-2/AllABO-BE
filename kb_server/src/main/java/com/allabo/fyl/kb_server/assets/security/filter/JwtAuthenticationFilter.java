package com.allabo.fyl.kb_server.assets.security.filter;

import com.allabo.fyl.kb_server.assets.dto.TotalUserAssetDTO;
import com.allabo.fyl.kb_server.assets.security.mapper.TotalUserAssetMapper;
import com.allabo.fyl.kb_server.assets.security.util.JWTUtil;
import com.allabo.fyl.kb_server.assets.security.exception.AccessTokenException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    private final TotalUserAssetMapper mapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // JWT가 포함된 Authorization 헤더 추출
        String header = request.getHeader("Authorization");

        // JWT 토큰이 없으면 필터 통과
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7); // "Bearer " 이후의 토큰 추출

        try {
            // JWT 토큰 검증
            Map<String, Object> claims = jwtUtil.validateToken(token);
            String userIdentity = (String) claims.get("identityNumber"); // JWT 클레임에서 사용자 이름(또는 ID) 추출
            log.info("주민번호 왔다!",userIdentity);
            if (userIdentity != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // UserDetails를 로드하여 보안 컨텍스트 설정

                TotalUserAssetDTO user = mapper.get(userIdentity);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user, null, null);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (ExpiredJwtException | SignatureException | MalformedJwtException e) {
            log.error("JWT Token validation error: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 다음 필터로 진행
        filterChain.doFilter(request, response);
    }
}

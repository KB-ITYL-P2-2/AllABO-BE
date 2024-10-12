package com.allabo.fyl.kb_server.assets.security.config;

import com.allabo.fyl.kb_server.assets.dto.TotalUserAssetDTO;
import com.allabo.fyl.kb_server.assets.security.filter.JwtAuthenticationFilter;
import com.allabo.fyl.kb_server.assets.security.mapper.TotalUserAssetMapper;
import com.allabo.fyl.kb_server.assets.security.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@Slf4j
@ComponentScan(basePackages = {
        //"com.allabo.fyl.kb_server.assets.security.service",
        "com.allabo.fyl.kb_server.assets.security.util"})
@MapperScan(basePackages = {"com.allabo.fyl.kb_server.assets.security.mapper"}, annotationClass = org.apache.ibatis.annotations.Mapper.class)
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JWTUtil jwtUtil;
    private final TotalUserAssetMapper mapper;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtil, mapper);
        return http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // JWT 사용 시 세션 사용 안 함
                .and()
                .cors()  // CORS 설정 추가
                .and()
                .authorizeRequests()
                .antMatchers("/assets/analyze/**").authenticated()  // 특정 경로에 인증 필요
                .anyRequest().permitAll()  // 그 외 모든 요청은 인증 없이 허용
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)  // JWT 인증 필터 추가
                .build();
    }

    // CORS 설정을 Bean으로 등록
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);  // 자격 증명 허용
        config.addAllowedOriginPattern("*");  // 모든 출처 허용 (CORS 요청 허용)
        config.addAllowedHeader("*");  // 모든 헤더 허용
        config.addAllowedMethod("*");  // 모든 HTTP 메서드 허용
        source.registerCorsConfiguration("/**", config);  // 모든 경로에 대해 CORS 설정 적용
        return new CorsFilter(source);
    }
}
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
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT 사용 시 세션 사용 안 함
                .and()
                .authorizeRequests()
                .antMatchers("/assets/analyze/**").authenticated() // 특정 경로에만 인증 적용
                .anyRequest().permitAll()
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
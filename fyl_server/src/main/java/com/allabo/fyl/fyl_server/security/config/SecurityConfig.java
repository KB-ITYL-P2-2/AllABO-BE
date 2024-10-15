package com.allabo.fyl.fyl_server.security.config;

import com.allabo.fyl.fyl_server.security.filter.MyLoginFilter;
import com.allabo.fyl.fyl_server.security.filter.MyRefreshTokenFilter;
import com.allabo.fyl.fyl_server.security.filter.MyTokenCheckFilter;
import com.allabo.fyl.fyl_server.security.handler.MyLoginFailureHandler;
import com.allabo.fyl.fyl_server.security.handler.MyLoginSuccessHandler;
import com.allabo.fyl.fyl_server.security.mapper.CustomerMapper;
import com.allabo.fyl.fyl_server.security.service.MyUserDetailsService;
import com.allabo.fyl.fyl_server.security.util.JWTUtil;
import com.allabo.fyl.fyl_server.service.UserFinancialsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@EnableWebSecurity
@Slf4j
@ComponentScan(basePackages = {
        "com.allabo.fyl.fyl_server.security.service",
        "com.allabo.fyl.fyl_server.security.util"})
@MapperScan(basePackages = {"com.allabo.fyl.fyl_server.security.mapper"}, annotationClass = org.apache.ibatis.annotations.Mapper.class)
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JWTUtil jwtUtil;
    private final MyUserDetailsService userDetailsService;
    private final CustomerMapper mapper;
    private final UserFinancialsService service;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
//        return NoOpPasswordEncoder.getInstance(); //비밀번호 암호와 안하기
    }
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = authenticationManager();
        log.info("------authenticationManager = {}", authenticationManager);

        return http
                .cors() // CORS 설정 활성화
                .and()
                // 커스터마이즈한 필터를 등록

                //1.로그인인증 필터
                .addFilterBefore(
                        loginFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)

                //2. 인증유효성검사 필터
                .addFilterBefore(
                        tokenCheckFilter(userDetailsService),
                        UsernamePasswordAuthenticationFilter.class)

                //3. 리프레시토큰
                .addFilterBefore(
                        refreshTokenFilter(),
                        MyTokenCheckFilter.class)


                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/admin").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()

                .and()

                .httpBasic().disable()        // 기본 HTTP 인증 비활성화
                .csrf().disable()       // CSRF 비활성화
                .formLogin().disable()  // formLogin 비활성화  관련 필터 해제
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 생성 모드 설정

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .build();
    }

    private MyRefreshTokenFilter refreshTokenFilter() {
        return new MyRefreshTokenFilter("/refreshToken", jwtUtil);
    }

    private MyTokenCheckFilter tokenCheckFilter(MyUserDetailsService userDetailsService) {
        return new MyTokenCheckFilter(userDetailsService, jwtUtil,mapper,service);
    }

    private MyLoginFilter loginFilter(AuthenticationManager authenticationManager) throws Exception {
        MyLoginSuccessHandler loginSuccessHandler = new MyLoginSuccessHandler(jwtUtil);
        MyLoginFailureHandler loginFailureHandler = new MyLoginFailureHandler();
        MyLoginFilter loginFilter = new MyLoginFilter(authenticationManager,
                "/login"
        );
        loginFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        loginFilter.setAuthenticationFailureHandler(loginFailureHandler);
        return loginFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(authenticationProvider);
    }
}
package com.korit.pawsmarket.global.config.security;

import com.korit.pawsmarket.domain.tokenBlacklist.service.TokenBlackListService;
import com.korit.pawsmarket.global.jwt.filter.JwtFilter;
import com.korit.pawsmarket.global.jwt.util.JwtUtil;
import com.korit.pawsmarket.global.security.user.service.CustomUserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    // Swagger UI 및 API 문서에 대한 접근을 허용하는 경로 (모든 사용자에게 허용)
    private static final String[] AUTH_WHITELIST = {
            "/swagger-ui/**", "/swagger-ui-custom.html", "/swagger-ui.html",
            "/api-docs", "/api-docs/**", "/v3/api-docs/**"
    };
   /* // 관리자만 접근을 허용하는 경로
    private static final String[] ADMIN_ONLY = {
            "/comments"
    };

    // 로그인 한 사용자(관리자 + 일반유저)만 접근을 허용하는 경로
    private static final String[] ADMIN_USER_ONLY = {
            "/users/logout", "/posts/{postId}/likes", "/posts/{postId}/comments/{commentId}",
            "/posts/me", "/comments/me"
    };

    // 모든 사용자에게 접근을 허용하는 경로
    private static final String[] PUBLIC_ALL = {
            "/users", "/users/login", "/users/{userId}", "/posts/{postId}/thumbnail",
            "/posts/images/random", "/info/**"
    };
*/
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new Http403ForbiddenEntryPoint();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandlerImpl();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomUserDetailService customUserDetailService, TokenBlackListService tokenBlackListService, JwtUtil jwtUtil) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //세션 안씀
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtFilter(customUserDetailService, tokenBlackListService, jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(c -> c.authenticationEntryPoint(authenticationEntryPoint())
                        .accessDeniedHandler(accessDeniedHandler()))
                .authorizeHttpRequests(c -> c.anyRequest().permitAll())
                .build();
    }

}




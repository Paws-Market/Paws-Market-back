package com.korit.pawsmarket.global.jwt.util;

import com.korit.pawsmarket.domain.user.service.dto.JwtUserInfoDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    private final Key key;
    private final long accessTokenExpTime;

    //JWT 서명(Signature)에 사용할 비밀키와 토큰의 만료 시간을 설정하는 생성자
    public JwtUtil(
            @Value("${spring.jwt.secret.key}") String secretKey,
            @Value("${spring.jwt.expiration_time}") long accessTokenExpTime
    ) {
       byte[] decodeKey = Decoders.BASE64.decode(secretKey);
       this.key = Keys.hmacShaKeyFor(decodeKey);
       this.accessTokenExpTime = accessTokenExpTime;
    }

    //사용자 정보를 받아서 JWT를 생성하는 메서드
    public String generateAccessToken(JwtUserInfoDto jwtUserInfoDto) { return buildJwtToken(jwtUserInfoDto, accessTokenExpTime); }

    //jwt를 실제로 생성하는 함수 : 모든 종류의 JWT생성 로직을 담당하는 공통 함수
    public String buildJwtToken(JwtUserInfoDto jwtUserInfoDto, long expireTime) {

        Claims claims = Jwts.claims(); // JWT의 **Payload(데이터 부분)**을 생성.
        claims.put("userId", jwtUserInfoDto.userId());
        claims.put("roleType", jwtUserInfoDto.roleType());
        claims.put("authProvider", jwtUserInfoDto.authProvider());

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plus(expireTime, ChronoUnit.MILLIS);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(tokenValidity.toInstant()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact(); // JWT를 최종적으로 생성해서 문자열로 반환.

    }

    //parseClaims(token)을 호출해서 JWT의 Payload(데이터 부분)을 가져옴.
    //get("userId", Long.class) → JWT에서 userId 값을 Long 타입으로 가져옴.
    public Long getUserId(String token) {return parseClaims(token).get("userId", Long.class); }

    //주어진 JWT 토큰이 유효한지 검사하는 함수
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty", e);
        }
        return false;
    }

    //JWT에서 **Claims(페이로드 정보)**를 추출하는 함수
    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    // HTTP 요청의 헤더에서 JWT를 꺼내고, 검증한 후 userId를 추출
    public Long getUserIdFromToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        String token = null;

        if (authorization != null && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7); // "Bearer " 부분 제거하고 실제 JWT만 남김
        }

        if(token != null && validateToken(token)) { // 토큰이 유효하면
            return getUserId(token);// JWT에서 userId 추출
        }

        return null;
    }

    public String getUserRoleTypeFromToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        String token = null;

        if(authorization != null && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7);
        }

        if(token != null && validateToken(token)) {
            return parseClaims(token).get("roleType", String.class);
        }
        return null;
    }
}

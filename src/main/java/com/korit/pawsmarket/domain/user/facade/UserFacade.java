package com.korit.pawsmarket.domain.user.facade;

import com.korit.pawsmarket.domain.role.entity.Role;
import com.korit.pawsmarket.domain.tokenBlacklist.service.TokenBlackListService;
import com.korit.pawsmarket.domain.user.entity.User;
import com.korit.pawsmarket.domain.user.enums.AuthProvider;
import com.korit.pawsmarket.domain.user.service.create.CreateUserService;
import com.korit.pawsmarket.domain.user.service.dto.JwtUserInfoDto;
import com.korit.pawsmarket.domain.user.service.read.ReadUserService;
import com.korit.pawsmarket.global.exception.CustomException;
import com.korit.pawsmarket.global.exception.DuplicateException;
import com.korit.pawsmarket.global.exception.UnauthorizedException;
import com.korit.pawsmarket.global.jwt.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.korit.pawsmarket.web.api.user.req.LoginUserReqDto;
import com.korit.pawsmarket.web.api.user.req.UserCreateReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class UserFacade {

    private final ReadUserService readUserService;
    private final PasswordEncoder passwordEncoder;
    private final CreateUserService createUserService;
    private final JwtUtil jwtUtil;
    private final TokenBlackListService tokenBlackListService;


    public void createUser(UserCreateReqDto req) {
        try {
            //이메일 중복 체크
            if (readUserService.existsByEmail(req.email())) {
                throw new DuplicateException("이미 사용 중인 이메일입니다.");
            }

            if (readUserService.existByNick(req.nick())) {
                throw new DuplicateException("이미 사용 중인 닉네임입니다.");
            }

            //비밀번호 암호화(OAuth 회원이면 null)
            String encodedPassword = req.authProvider() == AuthProvider.COMMON
                    ? passwordEncoder.encode(req.password()) : null;

            //롤 객체 조회 해오기
            Role role = readUserService.findByRoletype(req.roletype())
                    .orElseThrow(() -> new IllegalArgumentException("유효하지 않는 역할 입니다."));

            // User 엔티티 생성
            User newUser = UserCreateReqDto.of(req, role, encodedPassword);
            createUserService.createUser(newUser);

        } catch (DuplicateException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new CustomException("잘못된 입력입니다.");
        } catch (Exception e) {
            throw new CustomException("회원가입 중 알 수 없는 오류 발생");
        }
    }

    public String login(LoginUserReqDto req) {

        User user = readUserService.findUserByEmail(req.email());

        //1. 이메일 검증
        if (user == null) {
            throw new IllegalArgumentException("해당 이메일을 사용하는 계정이 존재하지 않습니다.");
        }

        //2.OAuth 로그인 계정인지 확인
        if(user.getAuthProvider() != AuthProvider.COMMON) {
            throw new IllegalArgumentException("소셜 로그인으로 가입된 계정입니다. 소셜 로그인을 이용해주세요.");
        }

        //3. 비밀번호 검증
        if (!passwordEncoder.matches(req.password(), user.getPassword())) {
            throw new UnauthorizedException("비밀번호가 일치하지 않습니다.");
        }

        if(user.isDelete()) {
            throw new UnauthorizedException("탈퇴한 계정입니다.");
        }

        //4. jwt 토큰 생성
        JwtUserInfoDto jwtUserInfoDto = JwtUserInfoDto.of(user);
        return jwtUtil.generateAccessToken(jwtUserInfoDto);
    }

    public void logout(Authentication authentication, HttpServletRequest request) {
        if (authentication == null) {
            throw new UnauthorizedException("인증 실패 : authentication is Null");
        }

        if (request.getHeader("Authorization") == null ||
            !request.getHeader("Authorization").startsWith("Bearer ")) {
            throw new UnauthorizedException("인증 실패 : Authorizaion 헤더가 없거나 형식이 올바르지 않습니다.");
        }

        String name = authentication.getName();
        log.info("로그아웃 요청한 사용자 :  {}", name);

        String token = request.getHeader("Authorization").substring(7);
        log.info("token : {}", token);

        Claims claims = jwtUtil.parseClaims(token);

        LocalDateTime expTime = claims.getExpiration()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        log.info("expTime : {}", expTime);

        tokenBlackListService.createTokenBlacklist(token, expTime);
        request.getSession().removeAttribute("cart");
    }
}

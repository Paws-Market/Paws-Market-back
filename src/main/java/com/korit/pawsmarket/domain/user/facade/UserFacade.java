package com.korit.pawsmarket.domain.user.facade;

import com.korit.pawsmarket.domain.role.entity.Role;
import com.korit.pawsmarket.domain.user.entity.User;
import com.korit.pawsmarket.domain.user.enums.AuthProvider;
import com.korit.pawsmarket.domain.user.service.create.CreateUserService;
import com.korit.pawsmarket.domain.user.service.read.ReadUserService;
import com.korit.pawsmarket.global.exception.DuplicateException;
import com.korit.pawsmarket.web.api.user.req.UserCreateReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;


@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class UserFacade {

    private final ReadUserService readUserService;
    private final PasswordEncoder passwordEncoder;
    private final CreateUserService createUserService;


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
            Role role = readUserService.findByRoleId(req.roleId())
                    .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 roleId 입니다."));

            // User 엔티티 생성
            User newUser = UserCreateReqDto.of(req, role, encodedPassword);
            createUserService.createUser(newUser);
            System.out.println("***************Role ID: " + req.roleId());

        }catch (NullPointerException e) {
            throw e;
        }

    }
}

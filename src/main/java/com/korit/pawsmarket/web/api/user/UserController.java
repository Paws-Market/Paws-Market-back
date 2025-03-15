package com.korit.pawsmarket.web.api.user;
import com.korit.pawsmarket.global.response.ApiResponse;

import com.korit.pawsmarket.domain.user.facade.UserFacade;
import com.korit.pawsmarket.global.response.enums.Status;
import com.korit.pawsmarket.web.api.user.req.LoginUserReqDto;
import com.korit.pawsmarket.web.api.user.req.UserCreateReqDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "USER_API", description = "유저 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserFacade userFacade;

    @Operation(summary = "회원 가입")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createUser(@Valid @RequestBody UserCreateReqDto req) {

        userFacade.createUser(req);

        return ApiResponse.generateResp(Status.CREATE, "회원가입이 완료되었습니다.", null);
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> loginUser(@Valid @RequestBody LoginUserReqDto req) {
        String token = userFacade.login(req);
        if (token == null || token.isEmpty()) {
            return ApiResponse.generateResp(Status.FORBIDDEN, "로그인 실패: 토큰이 생성되지 않았습니다.", null);
        }
        return ApiResponse.generateResp(Status.CREATE, "로그인 성공", token);
    }
    @Operation(summary = "로그아웃", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logoutUser(
            Authentication authentication, HttpServletRequest request
    ){
        userFacade.logout(authentication, request);
        return ApiResponse.generateResp(Status.SUCCESS, "로그아웃 되었습니다.", null);
    }


}

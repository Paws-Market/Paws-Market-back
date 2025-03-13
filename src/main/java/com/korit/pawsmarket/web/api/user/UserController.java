package com.korit.pawsmarket.web.api.user;

import com.korit.pawsmarket.domain.user.facade.UserFacade;
import com.korit.pawsmarket.web.api.user.req.UserCreateReqDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?>createUser(@Valid @RequestBody UserCreateReqDto req) {
        userFacade.createUser(req);
        return  ResponseEntity.ok().body("회원가입이 완료되었습니다.");
    }
}

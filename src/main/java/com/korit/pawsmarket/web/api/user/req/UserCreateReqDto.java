package com.korit.pawsmarket.web.api.user.req;

import com.korit.pawsmarket.domain.role.entity.Role;
import com.korit.pawsmarket.domain.role.enums.Roletype;
import com.korit.pawsmarket.domain.user.entity.User;
import com.korit.pawsmarket.domain.user.enums.AuthProvider;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
public record UserCreateReqDto(

        @Schema(example = "user1@naver.com")
        @NotBlank(message = "이메일은 필수 입력입니다.")
        @Email(message = "유효하지 않은 이메일 형식입니다.")
        String email,

        @Schema(example = "Qwer1234^^")
        @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하만 가능합니다.")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*()-+=]).{8,20}$",
                message = "비밀번호는 대/소문자, 숫자, 특수문자를 포함해야 합니다."
        )
        String password, // OAuth 회원가입 시에는 비밀번호 없이 가입할 수도 있음

        @Schema(example = "user1")
        @NotBlank(message = "닉네임은 필수 입력입니다.")
        @Size(min = 2, max = 12, message = "닉네임은 2자 이상 12자 이하만 가능합니다.")
        String nick,

        @Schema(example = "서울특별시 강남구")
        @NotBlank(message = "주소는 필수 입력입니다.")
        String address,

        @Schema(example = "1997-03-29")
        @NotNull(message = "생년월일은 필수 입력입니다.")
        LocalDate birth,

        @Schema(example = "https://aws.s3.com/default-profile.png")
        String profileImg,

        @Schema(example = "1") // 클라이언트가 Role의 ID만 보냄
        @NotNull(message = "권한 정보는 필수 입력입니다.")
        Roletype roletype,

        @Schema(example = "COMMON")
        @NotNull(message = "인증 방식은 필수 입력입니다.")
        AuthProvider authProvider
) {
     public static User of(UserCreateReqDto dto, Role role, String encodedPassword) {
             return User.builder()
                     .email(dto.email)
                     .password(dto.authProvider == AuthProvider.COMMON ? encodedPassword : null) //Oauth 회원은 비번 없음
                     .nick(dto.nick)
                     .address(dto.address)
                     .birth(dto.birth)
                     .profileImg(dto.profileImg)
                     .role(role)
                     .authProvider(dto.authProvider)
                     .build();
     }

}

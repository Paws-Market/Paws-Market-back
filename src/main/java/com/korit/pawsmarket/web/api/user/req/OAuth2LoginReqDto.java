package com.korit.pawsmarket.web.api.user.req;

import com.korit.pawsmarket.domain.user.enums.AuthProvider;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record OAuth2LoginReqDto(

        @Schema(example = "GOOGLE", description = "OAuth2 로그인 제공자")
        @NotBlank
        AuthProvider authProvider, // ENUM 사용

        @Schema(example = "123456789", description = "OAuth2 제공자가 부여한 사용자 ID")
        @NotBlank
        String oauthId,

        @Schema(example = "ya29.a0AfH6SM...", description = "OAuth2 Access Token")
        @NotBlank
        String accessToken // 추가된 필드

) {
}

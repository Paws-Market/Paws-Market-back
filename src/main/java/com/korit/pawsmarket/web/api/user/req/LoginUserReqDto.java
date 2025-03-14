package com.korit.pawsmarket.web.api.user.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LoginUserReqDto(
        @Schema(example = "user1@naver.com")
        @NotBlank
        String email,

        @Schema(example = "Qwer1234^^")
        @NotBlank
        String password
) {
}

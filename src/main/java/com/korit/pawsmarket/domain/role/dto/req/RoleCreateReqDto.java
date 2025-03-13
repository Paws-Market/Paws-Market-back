package com.korit.pawsmarket.domain.role.dto.req;

import com.korit.pawsmarket.domain.role.entity.Role;
import com.korit.pawsmarket.domain.role.enums.Roletype;
import jakarta.validation.constraints.NotNull;

public record RoleCreateReqDto(
        @NotNull(message = "롤 타입은 필수 입력입니다.")
        Roletype roleType
) {
    public Role toEntity() {
        return Role.builder()
                .roleType(this.roleType)
                .build();
    }
}

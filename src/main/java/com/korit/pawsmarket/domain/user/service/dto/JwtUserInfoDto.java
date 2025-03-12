package com.korit.pawsmarket.domain.user.service.dto;

import com.korit.pawsmarket.domain.role.entity.Role;
import com.korit.pawsmarket.domain.role.enums.Roletype;
import com.korit.pawsmarket.domain.user.entity.User;
import com.korit.pawsmarket.domain.user.enums.AuthProvider;
import lombok.Builder;

@Builder
public record JwtUserInfoDto(
        Long userId,
        String email,
        String nick,
        Roletype roleType,
        String password,
        AuthProvider authProvider
) {
    public static JwtUserInfoDto of(User user) {
        return JwtUserInfoDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .nick(user.getNick())
                .roleType(user.getRole().getRoleType())
                .password(user.getPassword())
                .authProvider(user.getAuthProvider())
                .build();
    }
}

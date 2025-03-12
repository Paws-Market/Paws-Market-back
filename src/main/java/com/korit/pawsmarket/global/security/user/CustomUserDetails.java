package com.korit.pawsmarket.global.security.user;

import com.korit.pawsmarket.domain.user.service.dto.JwtUserInfoDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final JwtUserInfoDto jwtUserInfoDto;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + jwtUserInfoDto.roleType().name()));
    }

    @Override
    public String getPassword() {
        return jwtUserInfoDto.password() != null ? jwtUserInfoDto.password() : "";
    }

    @Override
    public String getUsername() {
        return jwtUserInfoDto.email();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

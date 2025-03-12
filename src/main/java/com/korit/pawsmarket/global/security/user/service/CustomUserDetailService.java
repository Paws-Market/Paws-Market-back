package com.korit.pawsmarket.global.security.user.service;

import com.korit.pawsmarket.domain.user.entity.repository.UserRepository;
import com.korit.pawsmarket.global.security.user.CustomUserDetails;
import com.korit.pawsmarket.domain.user.entity.User;
import com.korit.pawsmarket.domain.user.service.dto.JwtUserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        throw new UnsupportedOperationException("이 서비스는 userId 기반으로 동작합니다.");
    }

    public UserDetails loadUserByUserId(Long userId) throws UsernameNotFoundException {
        User user = userRepository.findUserByUserIdQuery(userId);
        JwtUserInfoDto jwtUserInfoDto = JwtUserInfoDto.of(user);
        return new CustomUserDetails(jwtUserInfoDto);
    }
}

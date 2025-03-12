package com.korit.pawsmarket.domain.tokenBlacklist.service;

import com.korit.pawsmarket.domain.tokenBlacklist.entity.TokenBlacklist;
import com.korit.pawsmarket.domain.tokenBlacklist.entity.repository.TokenBlacklistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenBlackListService {

    private final TokenBlacklistRepository tokenBlacklistRepository;

    public void createTokenBlacklist(String token, LocalDateTime expTime) {
        TokenBlacklist tokenblacklist = new TokenBlacklist(token, expTime);
        tokenBlacklistRepository.save(tokenblacklist);
    }

    public boolean isBlacklisted(String token) {
        return tokenBlacklistRepository.existsByTokenId(token);
    }
}

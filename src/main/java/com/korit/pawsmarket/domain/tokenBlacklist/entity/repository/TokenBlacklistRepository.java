package com.korit.pawsmarket.domain.tokenBlacklist.entity.repository;

import com.korit.pawsmarket.domain.tokenBlacklist.entity.TokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, String> {
    boolean existsByTokenId(String tokenId);
}

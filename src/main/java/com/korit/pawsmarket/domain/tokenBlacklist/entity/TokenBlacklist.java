package com.korit.pawsmarket.domain.tokenBlacklist.entity;

import com.korit.pawsmarket.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "token_blacklist")
public class TokenBlacklist extends BaseEntity {

    @Id
    private String tokenId;

    private LocalDateTime expTime;
}

package com.korit.pawsmarket.domain.user.entity;

import com.korit.pawsmarket.domain.role.entity.Role;
import com.korit.pawsmarket.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import com.korit.pawsmarket.domain.user.enums.AuthProvider;

import java.time.LocalDate;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = true)
    private String password;

    @Column(unique = true, nullable = false)
    private String nick;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private LocalDate birth;

    @Column(nullable = true)
    private String profileImg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private AuthProvider authProvider = AuthProvider.COMMON;

    @Column(unique = true, nullable = true)
    private String oauthId;

    /**
     * 프로필 이미지 기본값 설정 (AWS S3 기본 이미지)
     */
    @PrePersist
    public void setDefaultProfileImg() {
        if (this.profileImg == null || this.profileImg.isBlank()) {
            this.profileImg = "https://aws.s3.com/default-profile.png"; // AWS 기본 이미지 URL
        }
    }
}

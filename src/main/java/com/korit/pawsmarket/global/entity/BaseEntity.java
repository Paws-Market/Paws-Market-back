package com.korit.pawsmarket.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_delete")
    private boolean isDelete = false;

    public void softDelete() {
        isDelete = true;
    }

    // 날짜 포맷팅 메서드 추가
    public String getFormattedCreatedAt() {
        return formatDate(createdAt);
    }

    public String getFormattedUpdatedAt() {
        return formatDate(updatedAt);
    }

    // 공통 날짜 포맷팅 로직
    private String formatDate(LocalDateTime dateTime) {
        if (dateTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return dateTime.format(formatter);
        }
        return null;
    }
}
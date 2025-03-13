package com.korit.pawsmarket.domain.role.entity.repository;

import com.korit.pawsmarket.domain.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    }

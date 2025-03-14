package com.korit.pawsmarket.domain.role.entity.repository;

import com.korit.pawsmarket.domain.role.entity.Role;
import com.korit.pawsmarket.domain.role.enums.Roletype;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleType(Roletype roleType);

}

package com.korit.pawsmarket.domain.role.facade;

import com.korit.pawsmarket.domain.role.dto.req.RoleCreateReqDto;
import com.korit.pawsmarket.domain.role.entity.Role;
import com.korit.pawsmarket.domain.role.entity.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Component
@RequiredArgsConstructor
public class RoleFacade {
    private final RoleRepository roleRepository;

    public Role createRole(RoleCreateReqDto req) {
      return roleRepository.save(req.toEntity());
    }
}

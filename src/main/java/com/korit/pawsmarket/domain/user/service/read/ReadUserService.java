package com.korit.pawsmarket.domain.user.service.read;

import com.korit.pawsmarket.domain.role.entity.Role;
import com.korit.pawsmarket.domain.role.entity.repository.RoleRepository;
import com.korit.pawsmarket.domain.role.enums.Roletype;
import com.korit.pawsmarket.domain.user.entity.User;
import com.korit.pawsmarket.domain.user.entity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReadUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public User findUserByEmail(String email){ return userRepository. findUserByEmail(email);}

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email); //이메일 존재 여부 체크
    }

    public boolean existByNick(String nick) {
        return userRepository.existsByNick(nick);
    }

    public Optional<Role> findByRoletype(Roletype roletype){
        return roleRepository.findByRoleType(roletype);
    }


}

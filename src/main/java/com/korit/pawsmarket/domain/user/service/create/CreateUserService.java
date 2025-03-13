package com.korit.pawsmarket.domain.user.service.create;

import com.korit.pawsmarket.domain.user.entity.User;
import com.korit.pawsmarket.domain.user.entity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateUserService {
    private final UserRepository userRepository;

    public void createUser(User user) {
         userRepository.save(user);
         log.info("***************************create user success");
    }
}

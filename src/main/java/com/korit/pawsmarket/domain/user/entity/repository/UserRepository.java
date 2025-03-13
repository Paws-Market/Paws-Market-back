package com.korit.pawsmarket.domain.user.entity.repository;


import com.korit.pawsmarket.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.userId = :userId AND u.isDelete = false")
    User findUserByUserIdQuery(@Param("userId") Long userId);

    boolean existsByEmail(String email);

    boolean existsByNick(String nick);

}

package com.example.myboard_jwt.repository;

import com.example.myboard_jwt.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    @EntityGraph(attributePaths = "authorities")
//    Optional<User> findOneWithAuthoritiesByUsername(String username);
    Optional<User> findByUsername(String username);

    Optional<User> findByNickname(String nickname);
}
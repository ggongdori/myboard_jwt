package com.example.myboard_jwt.repository;

import com.example.myboard_jwt.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findLikeByPost_PostIdAndUser_Username(Long postId, String username);

    boolean existsByPost_PostIdAndUser_Username(Long id, String username);
}
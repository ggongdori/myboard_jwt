package com.example.myboard_jwt.repository;

import com.example.myboard_jwt.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {
//    Optional<Likes> findLikeByPost_PostIdAndUser_Username(Long postId, String username);

//    boolean existsByPost_PostIdAndUser_Username(Long id, String username);

    @Query("select l from Likes l where l.user.id = :userId and l.post.id = :postId")
    Optional<Likes> isLikeByUser(@Param("postId") Long postId, @Param("userId") Long userId);
    @Query("select l from Likes l where l.user.id = :userId")
    List<Likes> findByUserIdWithPost(@Param("userId") Long userId);
}
package com.example.myboard_jwt.repository;

import com.example.myboard_jwt.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p ORDER BY p.id DESC")
    List<Post> findAllDesc();
    Optional<Post> findByPostId(Long postId);
    void deleteByPostId(Long postId);

}

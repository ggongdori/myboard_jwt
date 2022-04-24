package com.example.myboard_jwt.repository;

import com.example.myboard_jwt.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p join fetch p.user")
    List<Post> findAllWithUser();

    Optional<Post> findById(Long postId);

    @Query("select p from Post p join fetch p.user order by p.likeCount desc")
    Slice<Post> findAllByOrderByLikeCountDesc(Pageable pageable);

}

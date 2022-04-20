package com.example.myboard_jwt.service;

import com.example.myboard_jwt.dto.LikeRequestDto;
import com.example.myboard_jwt.entity.Like;
import com.example.myboard_jwt.entity.Post;
import com.example.myboard_jwt.entity.User;
import com.example.myboard_jwt.exception.RestException;
import com.example.myboard_jwt.repository.LikeRepository;
import com.example.myboard_jwt.repository.PostRepository;
import com.example.myboard_jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikeService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public void saveLike(LikeRequestDto requestDto, String username) {

        Post findPost = postRepository.findByPostId(requestDto.getPostId()).orElseThrow(
                () -> new RestException(HttpStatus.NOT_FOUND, "해당 postId가 존재하지 않습니다.")
        );
        Optional<Like> findLike = likeRepository.findLikeByPost_PostIdAndUser_Username(requestDto.getPostId(),
                username);
        if (findLike.isPresent()) {
            likeRepository.deleteById(findLike.get().getId());
        } else {
            Like like = Like.builder()
                    .post(findPost)
                    .build();
            likeRepository.save(like);
            findPost.addLike(like);
        }
    }
}
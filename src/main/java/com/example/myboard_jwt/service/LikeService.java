package com.example.myboard_jwt.service;

import com.example.myboard_jwt.entity.Likes;
import com.example.myboard_jwt.entity.Post;
import com.example.myboard_jwt.entity.User;
import com.example.myboard_jwt.exception.PblException;
import com.example.myboard_jwt.exception.RestException;
import com.example.myboard_jwt.repository.LikeRepository;
import com.example.myboard_jwt.repository.PostRepository;
import com.example.myboard_jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.myboard_jwt.exception.ErrorConstant.DEFAULT_ERROR;


@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LikeService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public void saveLike(Long postId, Long userId) {

        Post findPost = postRepository.findById(postId).orElseThrow(
                () -> new RestException(HttpStatus.NOT_FOUND, "해당 postId가 존재하지 않습니다.")
        );
        User findUser = userRepository.findById(userId).orElseThrow(
                () -> new RestException(HttpStatus.NOT_FOUND, "해당 userId가 존재하지 않습니다.")
        );
        likeRepository.isLikeByUser(postId, userId)
                .ifPresentOrElse(
                        (like) -> {
                            throw new RestException(HttpStatus.BAD_REQUEST, "이미 좋아요 한 글입니다");
                        },
                        () -> likeRepository.save(Likes.saveLike(findUser, findPost))
                );
    }

    @Transactional
    public void cancelLike(Long postId, Long userId) {

        Post post = getPostById(postId);
        likeRepository.isLikeByUser(postId, userId).ifPresentOrElse(
                (likes)->{post.cancelLike();
                likeRepository.delete(likes);
                },
                ()->{
                    throw new PblException("좋아요가 없습니다", DEFAULT_ERROR);
                }
        );

    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new RestException(HttpStatus.BAD_REQUEST, "없는 userId 입니다.")
        );
    }

    private Post getPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new RestException(HttpStatus.BAD_REQUEST, "없는 게시글입니다")
        );
    }


}
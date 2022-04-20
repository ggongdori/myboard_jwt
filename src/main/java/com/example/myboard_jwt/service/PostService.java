package com.example.myboard_jwt.service;

import com.example.myboard_jwt.dto.PostListResponseDto;
import com.example.myboard_jwt.dto.PostResponseDto;
import com.example.myboard_jwt.dto.PostSaveRequestDto;
import com.example.myboard_jwt.dto.PostUpdateRequestDto;
import com.example.myboard_jwt.entity.Post;
import com.example.myboard_jwt.entity.User;
import com.example.myboard_jwt.exception.RestException;
import com.example.myboard_jwt.repository.PostRepository;
import com.example.myboard_jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void save(PostSaveRequestDto requestDto, String username) {
        User result = userRepository.findByUsername(username).orElseThrow(
                () -> new RestException(HttpStatus.NOT_FOUND, "해당 username이 존재하지 않습니다.")
        );
        Post post = Post.builder()
                .content(requestDto.getContent())
                .image(requestDto.getImage())
                .build();
        postRepository.save(post);

    }
    @Transactional
    public Long update(Long id, PostUpdateRequestDto requestDto, String username) {
        Post post = postRepository.findByPostId(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다. id=" + id));

        post.update(requestDto.getImage(), requestDto.getContent());

        return id;
    }

    @Transactional
    public void delete(Long id) {
        Post post = postRepository.findByPostId(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다. id=" + id));

        postRepository.delete(post);
    }

    @Transactional
    public PostResponseDto findById(Long id) {
        Post entity = postRepository.findByPostId(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다. id=" + id));
        return new PostResponseDto(entity);
    }

    @Transactional
    public List<PostListResponseDto> findAllDesc() {
        return postRepository.findAllDesc().stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());
    }
}

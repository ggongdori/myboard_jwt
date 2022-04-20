package com.example.myboard_jwt.controller;

import com.example.myboard_jwt.dto.PostListResponseDto;
import com.example.myboard_jwt.dto.PostResponseDto;
import com.example.myboard_jwt.dto.PostSaveRequestDto;
import com.example.myboard_jwt.dto.PostUpdateRequestDto;
import com.example.myboard_jwt.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public Long save(@RequestBody PostSaveRequestDto requestDto) {
        return postService.save(requestDto);
    }

    @PutMapping("/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostUpdateRequestDto requestDto) {
        return postService.update(id, requestDto);
    }

    @DeleteMapping("/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postService.delete(id);
        return id;
    }

    @GetMapping("/posts/{id}")
    public PostResponseDto findById(@PathVariable Long id) {
        return postService.findById(id);
    }

    @GetMapping("/api/v1/posts/list")
    public List<PostListResponseDto> findAll() {
        return postService.findAllDesc();
    }
}
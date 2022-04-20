package com.example.myboard_jwt.controller;

import com.example.myboard_jwt.dto.PostListResponseDto;
import com.example.myboard_jwt.dto.PostResponseDto;
import com.example.myboard_jwt.dto.PostSaveRequestDto;
import com.example.myboard_jwt.dto.PostUpdateRequestDto;
import com.example.myboard_jwt.exception.RestException;
import com.example.myboard_jwt.handler.Success;
import com.example.myboard_jwt.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

//    @PostMapping("/posts")
//    public Long save(@RequestBody PostSaveRequestDto requestDto, @AuthenticationPrincipal User user) {
//        return postService.save(requestDto, user.getUsername());
//    }
    @PostMapping("/posts")
    public ResponseEntity<Success> savePost(@RequestBody @Valid PostSaveRequestDto requestDto,
                                            Principal principal, Errors errors) {
    if (errors.hasErrors()) {
        for (FieldError error : errors.getFieldErrors()) {
            throw new RestException(HttpStatus.BAD_REQUEST, error.getDefaultMessage());
        }
    }
    postService.save(requestDto, principal.getName());
    return new ResponseEntity<>(new Success(true, "게시글 저장 성공"), HttpStatus.OK);
}

    @PatchMapping("/posts/{id}")
    public ResponseEntity<Success> update(@PathVariable Long id, @RequestBody PostUpdateRequestDto requestDto,
                                          Principal principal, Errors errors) {
        if (errors.hasErrors()) {
            for (FieldError error : errors.getFieldErrors()) {
                throw new RestException(HttpStatus.BAD_REQUEST, error.getDefaultMessage());
            }
        }
        postService.update(id, requestDto, principal.getName());
        return new ResponseEntity<>(new Success(true, "게시글 수정 성공"), HttpStatus.OK);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Success> deletePost(@PathVariable Long id) {

        postService.delete(id);
        return new ResponseEntity<>(new Success(true, "게시글 삭제 성공"), HttpStatus.OK);
    }


    @GetMapping("/posts/{id}")
    public PostResponseDto findById(@PathVariable Long id, Principal principal) {
        return postService.findById(id);
    }

    @GetMapping("/posts")
    public List<PostListResponseDto> findAll() {
        return postService.findAllDesc();
    }
}
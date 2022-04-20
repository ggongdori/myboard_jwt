package com.example.myboard_jwt.controller;

import com.example.myboard_jwt.dto.LikeRequestDto;
import com.example.myboard_jwt.handler.Success;
import com.example.myboard_jwt.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/api/posts/likes")
    public ResponseEntity<Success> postLike(@RequestBody LikeRequestDto requestDto,
                                            Principal principal) {
//        if (principal == null) {
//            return new ResponseEntity<>(new Success(true, "로그인이 필요합니다"), HttpStatus.BAD_REQUEST);
//        }
        likeService.saveLike(requestDto, principal.getName());
        return new ResponseEntity<>(new Success(true, "좋아요 성공"), HttpStatus.OK);
    }
}
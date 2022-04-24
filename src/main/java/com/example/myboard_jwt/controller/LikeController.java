package com.example.myboard_jwt.controller;

import com.example.myboard_jwt.exception.ResultMsg;
import com.example.myboard_jwt.jwt.PrincipalDetails;
import com.example.myboard_jwt.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {

    private final LikeService likeService;


    @PostMapping("/posts/{postId}/like")
    public ResultMsg createLove(
            @PathVariable("postId") Long postId,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        likeService.saveLike(postId, principalDetails.getMemberSession().getId());
        return new ResultMsg("좋아요 성공");
    }

    @PostMapping("/posts/{postId}/like/delete")
    public ResultMsg deleteLove(
            @PathVariable("postId") Long postId,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        likeService.cancelLike(postId, principalDetails.getMemberSession().getId());

        return new ResultMsg("좋아요 취소 성공");
    }

    /*
    aws에서 delete method 대신 post로로
     */
//    @PostMapping("/posts/{postId}/likes/undo")
//    public ResultMsg undoLike(
//            @PathVariable("postId") Long postId,
//            @AuthenticationPrincipal PrincipalDetails principalDetails) {
//        likeService.cancelLike(postId, principalDetails.getMemberSession().getId());
//
//        return new ResultMsg("좋아요 취소 성공");
//    }
}


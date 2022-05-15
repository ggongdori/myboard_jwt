package com.example.myboard_jwt.controller;

import com.example.myboard_jwt.dto.*;
import com.example.myboard_jwt.exception.ResultMsg;
import com.example.myboard_jwt.jwt.PrincipalDetails;
import com.example.myboard_jwt.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    /**
     * Board 추가 API
     */
    @PostMapping("/board")
    public PostResponseDto createBoard(
            PostDto.FileReq fileReq,
            @AuthenticationPrincipal PrincipalDetails principal) throws Exception {

        return postService.createPost(fileReq, principal.getMemberSession().getId());
//        return new PostResponseDto(newPost, );
    }

    /**
     * BoardList 전부 조회
     * 인증없이 API도달 가능이므로 princiaplDetails null checking필요
     */
    @GetMapping(value = "/board")
    public PostDto.PostResList getBoardList(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            Pageable pageable
    ) {
        Long memberId = principalDetails != null ? principalDetails.getMemberSession().getId() : null;
        return postService.getBoardList(memberId, pageable);
    }

    /**
     * Board 조회
     * 인증없이 API도달 가능이므로 princiaplDetails null checking필요
     */
    @GetMapping(value = "/board/{boardId}")
    public PostDto.PostRes getBoard(
            @PathVariable("boardId") Long postId,
            @AuthenticationPrincipal PrincipalDetails principalDetails) throws IOException {
        Long userId = principalDetails != null ? principalDetails.getMemberSession().getId() : null;
        return postService.getPosts(postId, userId);
    }

    /**
     * Board 수정
     */
    @PatchMapping("/board/{boardId}")
    public PostResponseDto patchBoard(
            @PathVariable("boardId") Long boardId,
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            PostDto.FileReq fileReq) {
        return postService.updatePost(boardId, principalDetails.getMemberSession().getId(), fileReq);

    }

    /**
     * Board 삭제
     */
    @PostMapping("/board/{boardId}/delete")
    public ResultMsg removePost(
            @PathVariable("boardId") Long boardId,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {

        postService.removePost(boardId, principalDetails.getMemberSession().getId());
        return new ResultMsg("success");
    }
}
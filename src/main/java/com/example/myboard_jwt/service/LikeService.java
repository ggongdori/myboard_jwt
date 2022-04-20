//package com.example.myboard_jwt.service;
//
//import com.example.myboard_jwt.entity.Like;
//import com.example.myboard_jwt.entity.Post;
//import com.example.myboard_jwt.repository.LikeRepository;
//import com.example.myboard_jwt.repository.PostRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Transactional // DB에 반영되야 한다~~~ 라고 알려줌
//@RequiredArgsConstructor // final 로 선언된 녀석의 생성자를 자동으로 만들어준다.
//@Service // 얘는 서비스다~~
//public class LikeService {
//
//    private final LikeRepository isLikeRepository;
//    private final PostRepository postRepository;
//
//    // 좋아요 & 좋아요 취소
//    public void plusLike(Integer postNo, Integer userNo) {
//        // 해당 게시글이 유효한 게시글인지 조회
//        Post post = postRepository.findById(postNo).orElseThrow(
//                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
//        );
//
//        // 좋아요 테이블에 해당 포스트 번호 & 좋아요 누른 유저로 이루어진 데이터가 없으면 (좋아요 처리)
//        if (isLikeRepository.findByPostNoAndUserNo(postNo, userNo) == null) {
//            // 좋아요 테이블에 해당 포스트 번호 & 좋아요 누른 유저 번호로 1행 추가
//            Like isLike = new Like(postNo, userNo);
//            isLikeRepository.save(isLike);
//            // 좋아요 눌린 해당 포스트의 좋아요 컬럼에 +1
//            post.setLikes(post.getLikes() + 1);
//
//            // 좋아요 테이블에 해당 포스트 번호 & 좋아요 누른 유저로 이루어진 데이터가 있으면 (좋아요 취소처리)
//        } else {
//            // 좋아요 테이블에 해당 포스트 번호 & 좋아요 누른 유저 번호로 생성된 행 삭제
//            isLikeRepository.deleteByPostNoAndUserNo(postNo, userNo);
//            // 좋아요 눌린 해당 포스트의 좋아요 컬럼에 -1
//            post.setLikes(post.getLikes() - 1);
//        }
//    }
//}
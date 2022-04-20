//package com.example.myboard_jwt.entity;
//
//import com.example.myboard_jwt.handler.Timestamped;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@NoArgsConstructor // 기본생성자를 만듭니다.
//@Getter
//@Entity // 테이블과 연계됨을 스프링에게 알려줍니다.
//public class Like extends Timestamped { // 생성,수정 시간을 자동으로 만들어줍니다.
//
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Id
//    @Column(name = "like_no")
//    private Integer likeNo;
//
//    @Column(nullable = false, name = "post_no")
//    private Integer postNo;
//
//    @Column(nullable = false, name = "user_no")
//    private Integer userNo;
//
//    public Like(Integer postNo, Integer userNo) {
//        this.postNo = postNo;
//        this.userNo = userNo;
//    }
//
//    public Like(LikeRequestDto requestDto) {
//        this.postNo = requestDto.getPostNo();
//        this.userNo = requestDto.getUserNo();
//    }
//}
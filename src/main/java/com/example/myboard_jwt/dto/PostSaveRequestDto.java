//package com.example.myboard_jwt.dto;
//
//import com.example.myboard_jwt.entity.Post;
//import com.fasterxml.jackson.annotation.JsonFormat;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//@Getter
//@NoArgsConstructor
//public class PostSaveRequestDto {
//    private String picture;
//    private String content;
//
////    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
////    private LocalDateTime createdAt;
////
////    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
////    private LocalDateTime modifiedAt;
//
//
//    @Builder
//    public PostSaveRequestDto(String picture, String content) {
//        this.picture = picture;
//        this.content = content;
//    }
//
//    public Post toEntity() {
//        return Post.builder()
//                .picture(picture)
//                .content(content)
//                .build();
//    }
//
//}
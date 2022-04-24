//package com.example.myboard_jwt.dto;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//
//@Getter
//@NoArgsConstructor
//public class PostUpdateRequestDto {
//    private String picture;
//    private String content;
////    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
////    private LocalDateTime createdAt;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime modifiedAt;
//    @Builder
//    public PostUpdateRequestDto(String picture, String content) {
//        this.picture = picture;
//        this.content = content;
//    }
//}
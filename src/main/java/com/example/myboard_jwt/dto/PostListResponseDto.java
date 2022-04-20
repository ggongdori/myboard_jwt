package com.example.myboard_jwt.dto;

import com.example.myboard_jwt.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostListResponseDto {
    private Long id;
    private String image;
    private String user;
    private LocalDateTime modifiedAt;

    public PostListResponseDto(Post entity) {
        this.id = entity.getPostId();
        this.image = entity.getImage();
//        this.user = entity.getUser().getUsername();
        this.modifiedAt = entity.getModifiedAt();
    }
}
package com.example.myboard_jwt.dto;

import com.example.myboard_jwt.entity.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {

    private Long id;
    private String picture;
    private String content;
    private String user;

    public PostResponseDto(Post entity) {
        this.id = entity.getPostId();
        this.picture = entity.getPicture();
        this.content = entity.getContent();
//        this.user = entity.getUser().getUsername();
    }
}
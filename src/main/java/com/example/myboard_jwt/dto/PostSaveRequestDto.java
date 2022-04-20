package com.example.myboard_jwt.dto;

import com.example.myboard_jwt.entity.Post;
import com.example.myboard_jwt.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostSaveRequestDto {
    private String image;
    private String content;


    @Builder
    public PostSaveRequestDto(String image, String content) {
        this.image = image;
        this.content = content;
    }

    public Post toEntity() {
        return Post.builder()
                .image(image)
                .content(content)
                .build();
    }

}
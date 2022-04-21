package com.example.myboard_jwt.dto;

import com.example.myboard_jwt.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostSaveRequestDto {
    private String picture;
    private String content;


    @Builder
    public PostSaveRequestDto(String picture, String content) {
        this.picture = picture;
        this.content = content;
    }

    public Post toEntity() {
        return Post.builder()
                .picture(picture)
                .content(content)
                .build();
    }

}
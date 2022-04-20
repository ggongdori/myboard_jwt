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
    private User user;

    @Builder
    public PostSaveRequestDto(String image, String content, User user) {
        this.image = image;
        this.content = content;
        this.user = user;
    }

    public Post toEntity() {
        return Post.builder()
                .image(image)
                .content(content)
                .user(user)
                .build();
    }

}
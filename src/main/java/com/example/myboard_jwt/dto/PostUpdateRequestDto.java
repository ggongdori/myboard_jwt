package com.example.myboard_jwt.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateRequestDto {
    private String image;
    private String content;

    @Builder
    public PostUpdateRequestDto(String image, String content) {
        this.image = image;
        this.content = content;
    }
}
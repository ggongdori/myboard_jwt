package com.example.myboard_jwt.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateRequestDto {
    private String picture;
    private String content;

    @Builder
    public PostUpdateRequestDto(String picture, String content) {
        this.picture = picture;
        this.content = content;
    }
}
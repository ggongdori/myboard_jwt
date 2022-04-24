package com.example.myboard_jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostListResponseDto {
    private Slice<PostListResponseDto> posts;
}
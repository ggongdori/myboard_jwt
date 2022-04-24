package com.example.myboard_jwt.dto;

import com.example.myboard_jwt.entity.Post;
import com.example.myboard_jwt.service.FileHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostListResponseDto {
    private Slice<PostListResponseDto> posts;
}
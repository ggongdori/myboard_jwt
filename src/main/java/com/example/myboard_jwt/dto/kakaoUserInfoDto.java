package com.example.myboard_jwt.dto;

import lombok.AllArgsConstructor;
import org.hibernate.internal.build.AllowPrintStacktrace;

@AllArgsConstructor
public class kakaoUserInfoDto {
    private Long id;
    private String nickname;
    private String email;
}

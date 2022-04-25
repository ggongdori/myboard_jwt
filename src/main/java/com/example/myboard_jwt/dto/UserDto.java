package com.example.myboard_jwt.dto;

import com.example.myboard_jwt.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


public class UserDto {

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class Register{
        @NotBlank(message="아이디입력")
        @Pattern(regexp = "[a-zA-Z0-9]{3,20}", message = "아이디는 최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성하기")
        private String username;

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Size(min = 4, max = 20, message = "비밀번호는 최소 4자 이상 20자 이하")
        private String pw;

        @NotBlank(message = "이름을 입력해주세요.")
        private String nickname;

    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class Login {
        //추후 보안 강화 => " fsd" 이런 문자열 들어올 수 있음
        @NotBlank
        private String username;
        @NotBlank
        private String pw;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Session {
        private Long id;
        private String username;
        private String password;

        public Session(Login loginMember) {
            this.username = loginMember.username;
            this.password = loginMember.getPw();
        }
    }

    public boolean passwordCheck(String password, String username) {
        return password.contains(username);
    }

    public boolean isPasswordEqual(String password, String passwordCheck) {
        return password.equals(passwordCheck);
    }
}
package com.example.myboard_jwt.dto;

import com.example.myboard_jwt.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotBlank(message="아이디입력")
    @Pattern(regexp = "[a-zA-Z0-9]{3,20}", message = "아이디는 최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성하기")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 4, max = 20, message = "비밀번호는 최소 4자 이상 20자 이하")
    private String pw;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "비밀번호 확인을 입력해주세요.")
    private String pwCheck;

    @NotBlank(message = "이름을 입력해주세요.")
    private String nickname;

    private List<String> roles;
//    private Set<AuthorityDto> authorityDtoSet;

    public static UserDto from(User user) {
        if(user == null) return null;

        return UserDto.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
//                .authorityDtoSet(user.getAuthorities().stream()
//                        .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
//                        .collect(Collectors.toSet()))
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }

    public boolean passwordCheck(String password, String username) {
        return password.contains(username);
    }

    public boolean isPasswordEqual(String password, String passwordCheck) {
        return password.equals(passwordCheck);
    }
}
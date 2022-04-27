package com.example.myboard_jwt.entity;

import com.example.myboard_jwt.dto.UserDto;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.persistence.*;
//import java.security.Provider;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.myboard_jwt.entity.Provider.KAKAO;

@Setter
@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

//    @Column(name = "username", length = 50, unique = true)
    private String username;

//    @Column(name = "password", length = 100)
    private String pw;

//    @Column(name = "nickname", length = 50, unique = true)
    private String nickname;

//    private String role;

//    private String provider;

    //    private String kakaoId;
    private Provider provider;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likesList = new ArrayList<>();

    @Builder
    public User(String username,String pw, String nickname, Provider provider) {
        this.username = username;
        this.pw = pw;
        this.nickname = nickname;
        this.provider = provider;
//        this.kakaoId = kakaoId;
    }


    public static User kakaoUser(OAuth2User oAuth2User) {
        Map<String, String> kakao_account = oAuth2User.getAttribute("kakao_account");
        Map<String, String> properties = oAuth2User.getAttribute("properties");
        String email = String.valueOf(kakao_account.get("email"));
        String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());
        String nickname = properties.get("nickname");
        return User.builder()
                .username(email)
                .pw(password)
                .nickname(nickname)
                .provider(KAKAO)
                .build();
    }

    public static User createUser(String username, String pw, String nickname, Provider provider) {
        return new User(username, pw, nickname, provider);
    }

    public static User createUserByRegister(UserDto.Register reg) {
        return new User(reg.getUsername(), reg.getPw(), reg.getNickname(), reg.getProvider());
    }

}
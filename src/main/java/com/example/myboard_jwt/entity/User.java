package com.example.myboard_jwt.entity;

import com.example.myboard_jwt.dto.UserDto;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


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


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likesList = new ArrayList<>();

    private User(String username, String pw, String nickname) {
        this.username = username;
        this.pw = pw;
        this.nickname = nickname;
    }

    public static User createUser(String username, String pw, String nickname) {
        return new User(username, pw, nickname);
    }

    public static User createUserByRegister(UserDto.Register reg) {
        return new User(reg.getUsername(), reg.getPw(), reg.getNickname());
    }

}
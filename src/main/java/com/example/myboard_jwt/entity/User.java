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


//    @Column(name = "activated")
//    private boolean activated;

//    @ElementCollection(fetch = FetchType.EAGER)
//    @Column
//    private List<String> roles = new ArrayList<>();

//    @Builder
//    public User(String username, String password, String nickname) {
//        this.username = username;
//        this.password = password;
//        this.nickname = nickname;
//    }

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
//    public void addPost(Post post) {
//        post.setUser(this);
//        this.postList.add(post);
//    }
//
//    public void addLike(Likes likes) {
//        this.likesList.add(likes);
//        likes.setUser(this);
//    }

//    @ManyToMany
//    @JoinTable(
//            name = "user_authority",
//            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
//            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
//    private Set<Authority> authorities;

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return this.roles.stream()
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }

}
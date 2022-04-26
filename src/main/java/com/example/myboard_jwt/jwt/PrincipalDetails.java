package com.example.myboard_jwt.jwt;

import com.example.myboard_jwt.dto.UserDto;
import com.example.myboard_jwt.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

//시큐리티가 login 주소요청이 오면 낚아채서 로그인을 진행시킨다
// 로그인이 완료 되면 시큐리티 세션을 만들어준다(Security context holder)
// 오브젝트 타입 => Authentication 객체
// Authentication 안에 User 정보가 있어야됨
// User오브젝트 타입 => UserDetails 타입 객체

//Security Session =? Authentication => UserDetails(PrincipalDetails)

@Setter
@Getter
public class PrincipalDetails implements UserDetails {
    private UserDto.Session memberSession;
    private Map<String, Object> attributes;
    private User user;

    //일반 로그인
    public PrincipalDetails(Long id, String username, String password) {
        this.memberSession = new UserDto.Session(id, username, password);
    }
//    //OAuth 로그인
//    //Overloading
//    public PrincipalDetails(User user, Map<String, Object> attributes) {
//        this.user = user;
//        this.attributes = attributes;
//    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.memberSession.getPassword();
    }

    @Override
    public String getUsername() {
        return this.memberSession.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

//    @Override
//    public Map<String, Object> getAttributes() {
//        return attributes;
//    }
//
//    @Override
//    public String getName() {
//        return null;
//    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
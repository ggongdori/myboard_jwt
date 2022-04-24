package com.example.myboard_jwt.jwt;

import com.example.myboard_jwt.dto.UserDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Setter
@Getter
public class PrincipalDetails implements UserDetails {
    private UserDto.Session memberSession;

    public PrincipalDetails(Long id, String username, String password) {
        this.memberSession = new UserDto.Session(id, username, password);
    }

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

    @Override
    public boolean isEnabled() {
        return true;
    }
}
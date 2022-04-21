package com.example.myboard_jwt.service;

import java.util.Collections;

import com.example.myboard_jwt.dto.UserDto;
//import com.example.myboard_jwt.entity.Authority;
import com.example.myboard_jwt.entity.User;
import com.example.myboard_jwt.exception.DuplicateMemberException;
import com.example.myboard_jwt.jwt.TokenProvider;
import com.example.myboard_jwt.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Transactional
    public UserDto signup(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

//        Authority authority = Authority.builder()
//                .authorityName("ROLE_USER")
//                .build();

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPw()))
                .nickname(userDto.getNickname())
                .roles(Collections.singletonList("ROLE_USER"))
                .activated(true)
                .build();

        return UserDto.from(userRepository.save(user));
    }

    public void logout(HttpServletRequest request){
        String token = tokenProvider.resolveToken();
        SecurityContextHolder.clearContext();

    }

//    @Transactional(readOnly = true)
//    public UserDto getUserWithAuthorities(String username) {
//        return UserDto.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
//    }
//
//    @Transactional(readOnly = true)
//    public UserDto getMyUserWithAuthorities() {
//        return UserDto.from(SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername).orElse(null));
//    }

}
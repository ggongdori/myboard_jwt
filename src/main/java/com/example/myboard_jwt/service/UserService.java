package com.example.myboard_jwt.service;

import com.example.myboard_jwt.dto.UserDto;
import com.example.myboard_jwt.entity.User;
import com.example.myboard_jwt.exception.DuplicateMemberException;
import com.example.myboard_jwt.exception.RestException;
import com.example.myboard_jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;


@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(UserDto.Register userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        userDto.setPw(passwordEncoder.encode(userDto.getPw()));
        User user = User.createUserByRegister(userDto);
        userRepository.save(user);
    }

    private void isUsernameInPw(UserDto.Register userDto) {
        if (userDto.getPw().contains(userDto.getPw())) {
            throw new RestException(HttpStatus.BAD_REQUEST, "비밀번호 안에 아이디 포함 불가");
        }
    }

    private void isDuplicateUsernameAndNickname(UserDto.Register registerDto) {
        boolean isUsername = userRepository.findByUsername(registerDto.getUsername()).isPresent();
        boolean isNickname = userRepository.findByNickname(registerDto.getNickname()).isPresent();
        if (isUsername || isNickname){
            throw new RestException(HttpStatus.BAD_REQUEST, "중복되는 아이디 또는 닉네임이 존재합니다");
        }
    }

}
package com.example.myboard_jwt.service;

import com.example.myboard_jwt.entity.User;
import com.example.myboard_jwt.exception.RestException;
import com.example.myboard_jwt.repository.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    //한바퀴 돌면 초기화
    @After
    public void cleanup() {

        userRepository.deleteAll();
    }

    @Test
    public void createUser() throws Exception {
        //given
        User user1 = User.createUser("username1111", "12345", "nickname111111");
        System.out.println("---------------create-------------------");
        User user2 = User.createUser("username2222", "12345", "nickname22222");

        userRepository.save(user1);
        userRepository.save(user2);

        //when
        User findUser = userRepository.findById(user1.getId()).orElseThrow(
                () -> new RestException(HttpStatus.NOT_FOUND, "사용자 찾을 수 없음")
        );
        System.out.println("-----------------------------delete----------------");
        userRepository.deleteById(user2.getId());
        Optional<User> deletedUser = userRepository.findById(user2.getId());


        //then
        assertThat(findUser.getUsername()).isEqualTo(user1.getUsername());
        assertThat(findUser.getNickname()).isEqualTo(user1.getNickname());
        assertThat(findUser.getPw()).isEqualTo(user1.getPw());
    }
}
package com.example.myboard_jwt.entity;

import com.example.myboard_jwt.repository.PostRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/*
성공한듯
 */
public class UserTest {

    @Autowired
    PostRepository postRepository;
    @Test
    @DisplayName("usertest")
    public void createUser_Normal() {
        //given
        String username = "test1111111";
        String pw = "12345";
        String nickname = "test_nickname1111";

        //username이 pw 안에 있을 때
        String username2 = "test123";
        String pw2 = "test12345";
        String nickname2 = "test";


        //when
        User user = User.createUser(username, pw, nickname);
        User user2 = User.createUser(username2, pw2, nickname2);

        //then

        assertThat(user.getUsername()).isEqualTo(username);
        assertThat(user.getPw()).isEqualTo(pw);
        assertThat(user.getNickname()).isEqualTo(nickname);

        /////test22222222
        System.out.println("-----------------test222-----------");
        assertThat(user.getUsername()).isEqualTo(username);
        assertThat(user.getPw()).isEqualTo(pw);
        assertThat(user.getNickname()).isEqualTo(nickname);



    }
}
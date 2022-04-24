package com.example.myboard_jwt.service;

import com.example.myboard_jwt.entity.Post;
import com.example.myboard_jwt.entity.User;
import com.example.myboard_jwt.repository.LikeRepository;
import com.example.myboard_jwt.repository.PostRepository;
import com.example.myboard_jwt.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;


class LikeServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    LikeRepository likeRepository;
    @Autowired
    PostRepository postRepository;


    private User createUser() {
        return User.createUser("user111", "pw", "nickname111");
    }
    private Post createPost() {
        return Post.createPost("content1111", "picture1111", User.createUser("user111", "pw", "nickname111"));
    }
    @Test
    public void addLike_Normal() {
        //given
        User user = createUser();
        Post post = createPost();

        //when
        //then
    }
}
package com.example.myboard_jwt.service;

import com.example.myboard_jwt.entity.Post;
import com.example.myboard_jwt.entity.User;
import com.example.myboard_jwt.exception.RestException;
import com.example.myboard_jwt.repository.PostRepository;
import com.example.myboard_jwt.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class PostServiceTest {

    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;

    private User createUser() {
        return User.createUser("user1", "12345", "usernick1");
    }

    private Post createPost() {
        return Post.createPost("test1111", "picture111", createUser());
    }

    @Test
    public void createPost_Normal() {
        //given
        User user = createUser();
        Post post = createPost();
        //when
        Optional<Post> findPost = postRepository.findById(post.getId());
        //then
//        assertThat(findPost.map(Post::get))
    }

    @Test
    public void deletePost_Normal() {
        //given
        Post post = createPost();
        postRepository.deleteById(post.getId());

        Optional<Post> findPost = postRepository.findById(post.getId());
        //then
//        assertThatThrownBy(() -> findPost.orElseThrow(RestException::new)).isInstanceOf(RestException.class);
    }
}
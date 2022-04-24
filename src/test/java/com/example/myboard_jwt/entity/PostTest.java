package com.example.myboard_jwt.entity;

import com.example.myboard_jwt.repository.PostRepository;
import com.example.myboard_jwt.repository.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PostTest {

    @Autowired
    PostRepository postRepository;
    UserRepository userRepository;

    //한바퀴 돌면 다 지우기
    @After
    public void cleanup() {
        postRepository.deleteAll();
    }


    @Test
    @DisplayName("정상 케이스")
    public void createPost_Normal() {

        //given
//        Long id = 100L;
        String content = "this is content111";
        String picture = "this is an image_url1111";
//        Long likeCount = 100L;
        User user = User.createUser("test_username", "12345", "test_nickname");
        //when
        Post post = Post.createPost(content, picture, user);
        postRepository.save(post);

        List<Post> postList = postRepository.findAll();
        //then
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getPicture()).isEqualTo(picture);
        assertThat(post.getUser().getUsername()).isEqualTo(user.getUsername());
        assertThat(post.getUser().getPw()).isEqualTo(user.getPw());
        assertThat(post.getUser().getNickname()).isEqualTo(user.getNickname());

    }

    @Test
    public void deletePost_Normal() throws Exception{
        //given
        Post post = Post.createPost("content1111111", "picture111111",
                User.createUser("test1111", "12345", "nick11111111"));
//        List<Post> postList = new ArrayList<>();

        postRepository.save(post);

        //when

        postRepository.deleteById(post.getId());
        Optional<Post> findPost = postRepository.findById(post.getId());
        //then
        assertThatThrownBy(() -> findPost.orElseThrow(IllegalArgumentException::new))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
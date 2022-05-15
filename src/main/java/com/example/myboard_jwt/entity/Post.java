package com.example.myboard_jwt.entity;

import com.example.myboard_jwt.handler.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
@Entity
public class Post extends Timestamped {
    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String content;
    private String picture;
    private Long likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Likes> likesList = new ArrayList<>();

    private Post(String content, String picture, User user) {
        this.content = content;
        this.picture = picture;
        this.likeCount = 0L;
        this.user = user;
    }

    public static Post createPost(String content, String picture, User user) {
        Post post = new Post(content, picture, user);
        post.user.getPostList().add(post);
        return post;
    }


    public void update(String content, String filePath) {
        this.content = content;
        this.picture = filePath;
    }

    public void addLike() {
        this.likeCount += 1;
    }

    public void cancelLike() {
        this.likeCount -= 1;
    }
}

package com.example.myboard_jwt.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
//클래스이름 Like로 하면 h2db에 테이블 안 만들어짐
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private Likes(User user, Post post) {
        this.user = user;
        this.post = post;
    }

    public static Likes saveLike(User user, Post post) {
        Likes like = new Likes(user, post);
        like.getUser().getLikesList().add(like);
        like.getPost().getLikesList().add(like);
        post.addLike();
        return like;
    }
}
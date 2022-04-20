package com.example.myboard_jwt.entity;

import com.example.myboard_jwt.handler.Timestamped;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "post")
@NoArgsConstructor
@Getter
@Entity
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String content;

//    @JsonIgnoreProperties({"postList"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Transient
    private long likeCount;

    @Transient
    private boolean liked;

    @Builder
    public Post(String image, String content, User user, long likeCount) {
        this.image = image;
        this.content = content;
        this.user = user;
        this.likeCount = likeCount;

    }

    public void update(String image, String content) {
        this.image = image;
        this.content = content;
    }

    public void updateLike(long likeCount) {
        this.likeCount = likeCount;
    }

    public void updateLiked(boolean liked) {
        this.liked = liked;
    }
}

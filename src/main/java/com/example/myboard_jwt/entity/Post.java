package com.example.myboard_jwt.entity;

import com.example.myboard_jwt.handler.Timestamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Table(name = "post")
@NoArgsConstructor
@Getter
@Entity
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false)
    private String picture;

    @Column(nullable = false)
    private String content;

//    @JsonIgnoreProperties({"postList"})
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Transient
    private long likeCount;

    @Transient
    private boolean liked;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 5)
    private List<Likes> likesList = new ArrayList<>();

    public void addLike(Likes likes) {
        this.likesList.add(likes);
        likes.setPost(this);
    }


    @Builder
    public Post(String picture, String content, User user, long likeCount) {
        this.picture = picture;
        this.content = content;
        this.user = user;
        this.likeCount = likeCount;

    }

    public void update(String picture, String content) {
        this.picture = picture;
        this.content = content;
    }

    public void updateLike(long likeCount) {
        this.likeCount = likeCount;
    }

    public void updateLiked(boolean liked) {
        this.liked = liked;
    }
}

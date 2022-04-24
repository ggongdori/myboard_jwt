package com.example.myboard_jwt.dto;

import com.example.myboard_jwt.entity.Post;
import com.example.myboard_jwt.repository.LikeRepository;
import com.example.myboard_jwt.service.FileHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private Long postId;
    private String nickname;
    private String content;
    private byte[] picture;
    private Long likeCount;
    private boolean Like;
    private LocalDateTime lastModifiedAt;
    private FileHandler fileHandler;



    public PostResponseDto(Post newPost, byte[] picture) {
        this.postId = newPost.getId();
        this.nickname = newPost.getUser().getNickname();
        this.content = newPost.getContent();
        this.lastModifiedAt = newPost.getModifiedAt();
        this.picture = picture;
        this.likeCount = newPost.getLikeCount();
        this.Like = isLike();

    }

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime createdAt;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime modifiedAt;

//    public PostResponseDto(Post entity) {
//        this.nickname = entity.getUser().getNickname();
//        this.content = entity.getContent();
//        this.picture = entity.
//        this.likeCount = entity.getLikeCount();
////        this.Like = entity.getUser().get
//        this.lastModifiedAt = entity.getModifiedAt();
////        this.user = entity.getUser().getUsername();
//    }
}
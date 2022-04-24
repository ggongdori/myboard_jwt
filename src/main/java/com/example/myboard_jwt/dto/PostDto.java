package com.example.myboard_jwt.dto;

import com.example.myboard_jwt.entity.Post;
import com.example.myboard_jwt.service.FileHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.File;
import java.time.LocalDateTime;

public class PostDto {

    public static String ABSOLUTE_PATH =
            new File("").getAbsolutePath() + File.separator + File.separator;
    @Data
    @AllArgsConstructor
    public static class PostIdRes {
        private Long postId;
    }

    @Data
//    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostRes {
        private Long postId;
        private String nickname;
        private String content;
        private byte[] picture;
        private Long likeCount;
        private boolean Like;
        private LocalDateTime lastModifiedAt;

//        public PostRes(Post newPost, FileHandler fileHandler) {
//            this.nickname = newPost.getUser().getNickname();
//            this.content = newPost.getContent();
//            this.lastModifiedAt = newPost.getModifiedAt();
//            this.picture = fileHandler.getFileToByte();
//            this.likeCount = newPost.getLikeCount();
//            this.Like = isLike();

        }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostResList {
        private Slice<PostRes> posts;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FileReq {
        @NotBlank
        private String content;
        private MultipartFile picture = null;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FileDto {
        private String originalFileName;
        private String filePath;
    }

}

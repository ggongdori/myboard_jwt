package com.example.myboard_jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Profile;

import java.util.Properties;

@Data
public class KakaoUserInfoDto {
    public Long id;
    public String connected_at;
    public Properties properties;
    public Kakao_account kakao_account;

    public KakaoUserInfoDto(Long id, String nickname, String email) {
        this.id = id;
        this.properties.nickname = nickname;
        this.kakao_account.email = email;

    }

    @Data
    class Properties {
        public String nickname;
        public String profile_image;
        public String thumbnail_image;
    }
    @Data
    class Kakao_account {
        public Boolean profile_nickname_needs_agreement;
        public Profile profile;
        public Boolean has_email;
        public Boolean email_needs_agreement;
        public Boolean is_email_valid;
        public Boolean is_email_verified;
        public String email;
        @Data
        class Profile {
            public String nickname;
            public String thumbnail_image_url;
            public String profile_image_url;
        }


    }


}

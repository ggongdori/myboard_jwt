package com.example.myboard_jwt.config.oauth;

import com.example.myboard_jwt.entity.User;
import com.example.myboard_jwt.dto.UserDto;
import com.example.myboard_jwt.jwt.JwtTokenUtils;
import com.example.myboard_jwt.jwt.PrincipalDetails;
import com.example.myboard_jwt.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.example.myboard_jwt.entity.Provider.KAKAO;

@RequiredArgsConstructor
@Service
public class OAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;

    @Transactional
    public KakaoUserInfoDto kakaoLogin(String code) throws JsonProcessingException {
        String accessToken = getAccessToken(code);
        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);
        String email = kakaoUserInfo.getEmail();
        String nickname = kakaoUserInfo.getNickname();
        Long id = kakaoUserInfo.getId();
        //여기서는 이메일을 username으로
        String username = email;
        // 임시 코드
        if (username == null) {
            username = kakaoUserInfo.getId() + "@momo.com";
        }
        //

        User kakaoUser = userRepository.findByUsername(username)
                .orElse(null);

        if (kakaoUser == null) {
            nickname = kakaoUserInfo.getNickname();
            String password = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(password);

            User user = User.builder()
                    .username(email)
                    .pw(encodedPassword)
                    .nickname(nickname)
                    .provider(KAKAO)
                    .build();
            kakaoUser = userRepository.save(user);
        }

//        if (kakaoUser.getProvider() == MOMO) {
//            throw new RestException(HttpStatus.BAD_REQUEST, "같은 이메일이 존재합니다.");

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, passwordEncoder);
        SecurityContextHolder.getContext().setAuthentication(authentication);

//        PrincipalDetails principalDetails = new PrincipalDetails(kakaoUser.getId(), kakaoUser.getPw(), kakaoUser.getNickname());
        String token = jwtTokenUtils.createToken(PrincipalDetails);
//        redisTemplate.opsForValue()
//                .set(authentication.getName(), tokenDto.getRefreshToken(), tokenProvider.getREFRESH_TOKEN_VALIDITY(), TimeUnit.MILLISECONDS);
//        ResponseCookie cookie = tokenUtils.createTokenCookie(tokenDto.getRefreshToken());

        return new KakaoUserInfoDto(id, nickname, username);
    }

    private String getAccessToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "2adbf21838760b4e80d709eeb37a8857");
        body.add("redirect_uri", "https://modumoyeo.com/login/oauth2/code/kakao");
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();

        String username = jsonNode.get("kakao_account").get("email").asText();

//        if (node == null) email = null;
//        else email = node.asText();

        return new KakaoUserInfoDto(id, nickname, username);
    }
}

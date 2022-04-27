package com.example.myboard_jwt.service;

import com.example.myboard_jwt.dto.UserDto;
import com.example.myboard_jwt.entity.User;
import com.example.myboard_jwt.exception.DuplicateMemberException;
import com.example.myboard_jwt.exception.RestException;
import com.example.myboard_jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    @Transactional
    public void register(UserDto.Register userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        userDto.setPw(passwordEncoder.encode(userDto.getPw()));
        User user = User.createUserByRegister(userDto);
        userRepository.save(user);
    }

//    @Transactional
//    public void kakaoRegister(User user) {
//        String rawPw = user.getPw();
//        String encPw = passwordEncoder.encode(rawPw);
//        user.setPw(encPw);
//        userRepository.save(user);
//    }

    private void isUsernameInPw(UserDto.Register userDto) {
        if (userDto.getPw().contains(userDto.getPw())) {
            throw new RestException(HttpStatus.BAD_REQUEST, "비밀번호 안에 아이디 포함 불가");
        }
    }

    private void isDuplicateUsernameAndNickname(UserDto.Register registerDto) {
        boolean isUsername = userRepository.findByUsername(registerDto.getUsername()).isPresent();
        boolean isNickname = userRepository.findByNickname(registerDto.getNickname()).isPresent();
        if (isUsername || isNickname){
            throw new RestException(HttpStatus.BAD_REQUEST, "중복되는 아이디 또는 닉네임이 존재합니다");
        }
    }


//    public String kakaoLogin(String token) {
//        // 카카오 OAuth2 를 통해 카카오 사용자 정보 조회
//        KakaoUserInfoDto userInfoDto = kakaoOAuth2.getUserInfo(token);
//        Long kakaoId = userInfo.getId();
//        String nickname = userInfo.getNickname();
//        String email = userInfo.getEmail();
//
//        // 카카오 로그인 토큰은 email 과 password 로 만들어줌
//        String username = email;
//        // 패스워드 = 카카오 Id + ADMIN TOKEN
//        String password = kakaoId + ADMIN_TOKEN;
//
//        // DB 에 중복된 Kakao Id 가 있는지 확인
//        User kakaoUser = userRepository.findByKakaoId(kakaoId)
//                .orElse(null);
//
//        // 카카오 정보로 회원가입
//        if (kakaoUser == null) {
//            // 패스워드 인코딩
//            String encodedPassword = passwordEncoder.encode(password);
//            // ROLE = 사용자
//
//
//            kakaoUser = new User(username, encodedPassword, nickname,  kakaoId);
//            userRepository.save(kakaoUser);
//        }
//
//        // 로그인 처리
//        Authentication kakaoUsernamePassword = new UsernamePasswordAuthenticationToken(username, password);
//        //  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 로 진행됨
//
//        Authentication authentication = authenticationManager.authenticate(kakaoUsernamePassword);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        return username;
//    }

//    public User findUser(String username) {
//        User user = userRepository.findByUsername(username).orElseGet(
//                ()-> {
//                    return new User();
//                }
//        );
//        return user;
//    }

//    public String getKakaoAuthCode(String code) throws JsonProcessingException {
//
//// 1. "인가 코드"로 "액세스 토큰" 요청
//// HTTP Header 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//// HTTP Body 생성
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//        body.add("grant_type", "authorization_code");
//        body.add("client_id", "본인의 REST API키");
//        body.add("redirect_uri", "http://localhost:8080/user/kakao/callback");
//        body.add("code", code);
//
//// HTTP 요청 보내기
//        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
//                new HttpEntity<>(body, headers);
//        RestTemplate rt = new RestTemplate();
//        ResponseEntity<String> response = rt.exchange(
//                "https://kauth.kakao.com/oauth/token",
//                HttpMethod.POST,
//                kakaoTokenRequest,
//                String.class
//        );
//
//// HTTP 응답 (JSON) -> 액세스 토큰 파싱
//        String responseBody = response.getBody();
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(responseBody);
//        String accessToken = jsonNode.get("access_token").asText();
//
//        return accessToken;
//    }
//
//
//    public String getUserInfoFromToken(String accessToken) throws JsonProcessingException {
//
//        // 2. 토큰으로 카카오 API 호출
//        // HTTP Header 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + accessToken);
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        // HTTP 요청 보내기
//        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
//        RestTemplate rt = new RestTemplate();
//
//        ResponseEntity<String> response2 = rt.exchange(
//                "https://kapi.kakao.com/v2/user/me",
//                HttpMethod.POST,
//                kakaoUserInfoRequest,
//                String.class
//        );
//        String responseBody = response2.getBody();
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(responseBody);
//        Long id = jsonNode.get("id").asLong();
//        String nickname = jsonNode.get("properties")
//                .get("nickname").asText();
//        //kakao 이메일을 username으로 db 저장
//        String email = jsonNode.get("kakao_account")
//                .get("email").asText();
//
//        String username = email;
//        String password = id + "9999";
//
//        User kakaoUser = userRepository.findByUsername(username).orElse(null);
//        if (kakaoUser == null) {
//            String encodedPassword = passwordEncoder.encode(password);
//            kakaoUser = new User(username, encodedPassword, nickname);
//            userRepository.save(kakaoUser);
//        }
//        Authentication usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
//        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
////        System.out.println("카카오 사용자 정보: " + id + ", " + nickname + ", " + email);
//        return username;
//    }
}
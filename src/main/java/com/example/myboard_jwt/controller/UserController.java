package com.example.myboard_jwt.controller;

import com.example.myboard_jwt.dto.UserDto;
import com.example.myboard_jwt.exception.ResultMsg;
import com.example.myboard_jwt.jwt.JwtTokenUtils;
import com.example.myboard_jwt.jwt.PrincipalDetailsService;
import com.example.myboard_jwt.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
//@RequestMapping("/api")
public class UserController {


//    private String cosKey = "cos1234";


    private final UserService userService;
    private AuthenticationManager authenticationManager;
    private PrincipalDetailsService principalDetailsService;
    private JwtTokenUtils jwtTokenUtils;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello");
    }

    @PostMapping("/test-redirect")
    public void testRedirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/api/user");
    }

    @PostMapping("/api/register")
    public ResultMsg register(@RequestBody @Valid UserDto.Register registerDto) {
        userService.register(registerDto);
        //MessageSoruce refactoring
        return new ResultMsg("회원가입이 완료되었습니다");
    }


//    public void kakaoLogin(String code) throws JsonProcessingException {
//        String accessToken = getAccessToken(code);
//        KakaoUserInfoDto kakaoUserInfoDto = getKakaoUserInfo(accessToken);
//
//    }

//    @GetMapping("/login/kakao/callback")
//    public String kakaoCallback(String code) throws JsonProcessingException {
//
//        //POST 방식으로 key: value 데이터를 요청(카카오쪽으로)
//        //Http header 오브젝트 생성
//        RestTemplate rt = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//
//        //내가 전달받을 데이터가 key: value 형식이라는 것을 알려줌
//        headers.add("Content-type", "application/x-www-form-urlencoded; charset = utf - 8");
//
//
//        //http body object created
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//        //원래는 변수화 시켜서 사용
//        body.add("grant_type", "authorization_code");
//        body.add("client_id", "6c59b4dda916bc7b5a99d658afc58a07");
//        body.add("redirect_uri", "http://localhost:8080/api/login/kakao/callback");
//        body.add("code", code);
//
//        //httpheader와 httpbody를 하나의 오브젝트에 담기
//        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
//
//        //http 요청하기-Post방식으로-그리고 response 변수와 응답받음
//        ResponseEntity<String> response = rt.exchange(
//                "https://kauth.kakao.com/oauth/token",
//                HttpMethod.POST,
//                kakaoTokenRequest,
//                String.class
//        );
//        String responseBody = response.getBody();
//        ObjectMapper objectMapper = new ObjectMapper();
//        OAuthToken oAuthToken = objectMapper.readValue(responseBody, OAuthToken.class);
//        JsonNode jsonNode = objectMapper.readTree(responseBody);
////        String accessToken = jsonNode.get("access_token").asText();
//////        System.out.println("----------- " + accessToken + " -----------");
////        return accessToken;
//
//
////        return accessToken;
////        userService.kakaoLogin(code);
//
//        // 2. 토큰으로 카카오 API 호출
//        // HTTP Header 생성
//        HttpHeaders headers2 = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        // HTTP 요청 보내기
//        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers2);
//        RestTemplate rt2 = new RestTemplate();
//
//        ResponseEntity<String> response2 = rt2.exchange(
//                "https://kapi.kakao.com/v2/user/me",
//                HttpMethod.POST,
//                kakaoUserInfoRequest,
//                String.class
//        );
//
//        String responseBody2 = response.getBody();
//        ObjectMapper objectMapper2 = new ObjectMapper();
//        KakaoUserInfoDto kakaoUserInfoDto = objectMapper.readValue(responseBody2, KakaoUserInfoDto.class);
//        JsonNode jsonNode2 = objectMapper2.readTree(responseBody2);
//        Long id = jsonNode2.get("id").asLong();
//        String nickname = jsonNode2.get("properties")
//                .get("nickname").asText();
//        String email = jsonNode2.get("kakao_account")
//                .get("email").asText();
//        //        UUID garbagePassword = UUID.randomUUID();
//
//        User kakaoUser = User.builder()
//                .username(email + "_" + id)
//                .nickname(nickname)
//                .build();
//
//        User originUser = userService.findUser(kakaoUser.getUsername());
//
//        if (originUser.getUsername() == null) {
//            System.out.println("기존 회원이 아닙니다ㅏㅏㅏㅏㅏ");
//            userService.kakaoRegister(kakaoUser);
//        }
//
//        //로그인처리
//        Authentication authentication = authenticationManager
//                .authenticate(new UsernamePasswordAuthenticationToken(
//                        kakaoUser.getUsername(),
//                        cosKey
//                ));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        //        System.out.println("카카오 사용자 정보: " + id + ", " + nickname);
//
//        //        return new KakaoUserInfoDto(id, nickname, email);
//        return "카카오 회원가입 및 로그인 완료";
//    }







    //    @PostMapping("/register")
//    public ResponseEntity<UserDto> signup(@Valid @RequestBody UserDto userDto) {
//        if (userDto.passwordCheck(userDto.getPassword(), userDto.getUsername())) {
//            throw new IllegalArgumentException("비밀번호 내에 아이디 포함 불가");
//        } else if (!userDto.isPasswordEqual(userDto.getPassword(), userDto.getPasswordCheck())) {
//            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
//        } else {
//            return ResponseEntity.ok(userService.signup(userDto));
//        }
//    }


//    @GetMapping("/user")
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
//    public ResponseEntity<UserDto> getMyUserInfo(HttpServletRequest request) {
//        return ResponseEntity.ok(userService.getMyUserWithAuthorities());
//    }
//
//    @GetMapping("/user/{username}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
//    public ResponseEntity<UserDto> getUserInfo(@PathVariable String username) {
//        return ResponseEntity.ok(userService.getUserWithAuthorities(username));
//    }
}
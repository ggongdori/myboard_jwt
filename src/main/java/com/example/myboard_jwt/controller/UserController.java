package com.example.myboard_jwt.controller;

import com.example.myboard_jwt.dto.UserDto;
import com.example.myboard_jwt.exception.ResultMsg;
import com.example.myboard_jwt.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

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

    @PostMapping("/register")
    public ResultMsg register(@RequestBody @Valid UserDto.Register registerDto) {
        userService.register(registerDto);
        //MessageSoruce refactoring

        return new ResultMsg("회원가입이 완료되었습니다");
    }

    @GetMapping("/login/kakao/callback")
    public ResultMsg kakaoLogin(@RequestParam String code) {

        //POST 방식으로 key: value 데이터를 요청(카카오쪽으로)
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        //내가 전달받을 데이터가 key: value 형식이라는 것을 알려줌
        headers.add("Content-type", "application/x-www-form-urlencoded; charset = utf - 8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        //원래는 변수화 시켜서 사용
        params.add("grant_type", "authorization_code");
        params.add("client_id", "6c59b4dda916bc7b5a99d658afc58a07");
        params.add("redirect_uri", "http://localhost:8080/api/login/kakao/callback");
        params.add("code", "code");

        userService.kakaoLogin(code);
        return new ResultMsg("카카오 로그인 성공");
    }


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
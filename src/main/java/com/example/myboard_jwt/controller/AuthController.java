//package com.example.myboard_jwt.controller;
//
//import com.example.myboard_jwt.dto.LoginDto;
//import com.example.myboard_jwt.dto.TokenDto;
//import com.example.myboard_jwt.handler.Success;
//import com.example.myboard_jwt.jwt.JwtAuthenticationFilter;
//import com.example.myboard_jwt.jwt.JwtTokenUtils;
//import com.example.myboard_jwt.service.UserService;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;
//
//@RestController
//@RequestMapping("/api")
//public class AuthController {
//    private final UserService userService;
//    private final JwtTokenUtils jwtTokenUtils;
//    private final AuthenticationManagerBuilder authenticationManagerBuilder;
//
//    public AuthController(UserService userService, JwtTokenUtils jwtTokenUtils, AuthenticationManagerBuilder authenticationManagerBuilder) {
//        this.userService = userService;
//        this.jwtTokenUtils = jwtTokenUtils;
//        this.authenticationManagerBuilder = authenticationManagerBuilder;
//    }
//
////    @PostMapping("/login")
//    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {
//
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPw());
//
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String jwt = jwtTokenUtils.createToken(authentication);
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(JwtAuthenticationFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
//
//        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
//    }


//    @PostMapping("/logout")
//    public ResponseEntity<Success> logout(HttpServletRequest request) {
//        userService.logout(request);
//        return new ResponseEntity<>(new Success(true, "로그아웃 성공"), HttpStatus.OK);
//    }
//}
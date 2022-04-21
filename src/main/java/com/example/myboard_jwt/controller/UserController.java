package com.example.myboard_jwt.controller;

import com.example.myboard_jwt.dto.UserDto;
import com.example.myboard_jwt.exception.RestException;
import com.example.myboard_jwt.handler.Success;
import com.example.myboard_jwt.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/register")
    public ResponseEntity<Success> registerUser(@Valid @RequestBody UserDto userDto, Errors errors) {
        if (errors.hasErrors()) {
            for (FieldError error : errors.getFieldErrors()) {
                throw new RestException(HttpStatus.BAD_REQUEST, error.getDefaultMessage());
            }
        }

        if (userDto.passwordCheck(userDto.getPw(), userDto.getUsername())) {
            throw new RestException(HttpStatus.BAD_REQUEST, "비밀번호 내에 아이디를 포함할 수 없습니다.");
        } else if (!userDto.isPasswordEqual(userDto.getPw(), userDto.getPwCheck())) {
            throw new RestException(HttpStatus.BAD_REQUEST, "비밀번호와 비밀번호확인이 일치하지 않습니다.");
        } else {
            userService.signup(userDto);
            return new ResponseEntity<>(new Success(true, "회원가입 성공"), HttpStatus.OK);
        }
    }

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
package com.example.myboard_jwt.jwt;

import com.example.myboard_jwt.exception.CustomAuthenticationException;
import com.example.myboard_jwt.exception.ErrorConstant;
import com.example.myboard_jwt.exception.ResultMsg;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final MessageSource messageSource;
    @Override
    @ExceptionHandler(BadCredentialsException.class)
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String message;
        String code;
        if (request.getAttribute("custom") != null){
            CustomAuthenticationException exception = (CustomAuthenticationException) request.getAttribute("custom");
            message = exception.getMessage();
            code = exception.getErrorCode();
        } else {
            Exception exception = (Exception) request.getAttribute("error");
            message = exception.toString();
            code = ErrorConstant.DEFAULT_ERROR;
        }

        log.info("CustomAuthenticationEntryPoint Error exception = {}", message);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        //front??? fail??? ??????
        ResultMsg msg = new ResultMsg("fail");

        try (OutputStream os = response.getOutputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(os, msg);
            os.flush();
        }
    }

}
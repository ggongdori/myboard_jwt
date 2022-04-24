package com.example.myboard_jwt.jwt;

import com.example.myboard_jwt.exception.CustomAuthenticationException;
import com.example.myboard_jwt.exception.ResultMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.example.myboard_jwt.exception.ErrorConstant.DEFAULT_ERROR;
import static com.example.myboard_jwt.exception.ExceptionUtil.makeResponseInFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler {

    private final MessageSource messageSource;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String message;
        String code;

        if (exception instanceof CustomAuthenticationException) {
            CustomAuthenticationException customException = (CustomAuthenticationException) exception;
            message = customException.getMessage();
            code = customException.getErrorCode();
        } else {
            message = exception.toString();
            code = DEFAULT_ERROR;
        }
        log.warn("onAuthenticationFailure Error exception = {}", message);


        //front-end 에서 default error만 요청
        ResultMsg error = new ResultMsg("fail");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        makeResponseInFilter(response, error);
    }

}
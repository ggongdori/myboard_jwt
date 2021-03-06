package com.example.myboard_jwt.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.myboard_jwt.exception.ErrorConstant.DEFAULT_ERROR;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ErrorControllerAdvice {

    private final MessageSource messageSource;

    /**
     * @Valid 처리 ExceptionHandler
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultMsg validtionException(MethodArgumentNotValidException e) {
        log.info("error message = {}",e.toString());
        log.info("error field = {}", e.getFieldError().getField());

        String message = messageSource.getMessage(DEFAULT_ERROR, null, null);
        return new ResultMsg(message);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResultMsg illegalArgumentException(IllegalArgumentException e) {
        log.info("error = {}",e.toString());

        String message = messageSource.getMessage(DEFAULT_ERROR, null, null);
        return new ResultMsg(message);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PblException.class)
    public ResultMsg PblException(PblException e) {
        log.info(e.getErrorLog());
        String message = messageSource.getMessage(e.getErrorLog(), null, null);

        return new ResultMsg(message);
    }

    @ExceptionHandler(Exception.class)
    public ResultMsg exception(Exception e) {
        log.info("error = {}",e.toString());

        String message = messageSource.getMessage(DEFAULT_ERROR, null, null);
        return new ResultMsg(message);
    }
}
//package com.example.myboard_jwt.handler;
//
//import com.example.myboard_jwt.dto.ErrorDTO;
//import com.example.myboard_jwt.exception.DuplicateMemberException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import static org.springframework.http.HttpStatus.CONFLICT;
//
//@ControllerAdvice
//public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
//
//    @ResponseStatus(CONFLICT)
//    @ExceptionHandler(value = { DuplicateMemberException.class })
//    @ResponseBody
//    protected ErrorDTO badRequest(RuntimeException ex, WebRequest request) {
//        return new ErrorDTO(CONFLICT.value(), ex.getMessage());
//    }
//}
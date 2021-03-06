package com.example.myboard_jwt.exception;

import lombok.Getter;

@Getter
public class PblException extends RuntimeException{
    private String errorLog;
    private String errorCode;

    public PblException(String errorLog, String errorCode) {
        this.errorLog = errorLog;
        this.errorCode = errorCode;
    }

    public PblException() {
    }
}
package com.example.myboard_jwt.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Success {
    private boolean success;
    private String msg;
}
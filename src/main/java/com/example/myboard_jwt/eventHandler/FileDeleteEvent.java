package com.example.myboard_jwt.eventHandler;

import lombok.Getter;

@Getter
public class FileDeleteEvent {

    private String filePath;
    public FileDeleteEvent(String filePath) {
        this.filePath = filePath;
    }
}
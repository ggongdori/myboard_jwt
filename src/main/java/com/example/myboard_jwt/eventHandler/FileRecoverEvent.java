package com.example.myboard_jwt.eventHandler;

import lombok.Getter;

@Getter
public class FileRecoverEvent {
    private String filePath;
    private byte[] imageByteArray;

    public FileRecoverEvent(String filePath, byte[] imageByteArray) {
        this.filePath = filePath;
        this.imageByteArray = imageByteArray;
    }
}
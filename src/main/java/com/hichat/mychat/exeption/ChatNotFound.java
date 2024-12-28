package com.hichat.mychat.exeption;

public class ChatNotFound extends RuntimeException {
    public ChatNotFound(String message) {
        super(message);
    }
}

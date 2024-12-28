package com.hichat.mychat.exeption;

public class UserNeedsToRegisterWithThisEmail extends RuntimeException {
    public UserNeedsToRegisterWithThisEmail(String message) {
        super(message);
    }
}

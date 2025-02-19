package com.hichat.mychat.exeption;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String connectedUserNotFound) {
        super(connectedUserNotFound);
    }
}

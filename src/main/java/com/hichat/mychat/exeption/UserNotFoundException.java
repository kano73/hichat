package com.hichat.mychat.exeption;

public class UserNotFoundException extends Throwable {
    public UserNotFoundException(String connectedUserNotFound) {
        super(connectedUserNotFound);
    }
}

package com.hichat.mychat.exeption;

public class RequestNotFound extends RuntimeException {
    public RequestNotFound(String message) {
        super(message);
    }
}

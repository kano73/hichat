package com.hichat.mychat.exeption;

public class EmailNotVerified extends RuntimeException {
    public EmailNotVerified(String message) {
        super(message);
    }
}

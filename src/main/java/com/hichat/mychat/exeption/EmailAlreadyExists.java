package com.hichat.mychat.exeption;

public class EmailAlreadyExists extends Exception {
    public EmailAlreadyExists(String message) {
        super(message);
    }
}

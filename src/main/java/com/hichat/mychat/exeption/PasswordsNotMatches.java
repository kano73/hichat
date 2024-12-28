package com.hichat.mychat.exeption;

public class PasswordsNotMatches extends RuntimeException {
    public PasswordsNotMatches(String message) {
        super(message);
    }
}

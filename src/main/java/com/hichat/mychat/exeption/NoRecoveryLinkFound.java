package com.hichat.mychat.exeption;

public class NoRecoveryLinkFound extends RuntimeException {
    public NoRecoveryLinkFound(String message) {
        super(message);
    }
}

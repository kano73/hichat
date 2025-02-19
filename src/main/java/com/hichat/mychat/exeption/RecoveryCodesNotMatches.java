package com.hichat.mychat.exeption;

public class RecoveryCodesNotMatches extends RuntimeException {
    public RecoveryCodesNotMatches(String message) {
        super(message);
    }
}

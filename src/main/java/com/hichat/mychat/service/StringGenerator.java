package com.hichat.mychat.service;

import org.apache.commons.lang3.RandomStringUtils;

public class StringGenerator {
    public String generateSecretCode12Chars() {
        return RandomStringUtils.randomAlphanumeric(12);
    }
}
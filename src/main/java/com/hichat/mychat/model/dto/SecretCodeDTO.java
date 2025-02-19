package com.hichat.mychat.model.dto;

import jakarta.validation.constraints.Size;

public class SecretCodeDTO {
    @Size(min = 12, max = 12)
    private String secretCode;

    public String getSecretCode() {
        return secretCode;
    }

    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }
}

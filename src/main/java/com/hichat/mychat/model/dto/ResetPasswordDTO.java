package com.hichat.mychat.model.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordDTO {
    @Size(min = 12, max = 12)
    private String secretCode;

    @Size(min = 4, max = 200)
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecretCode() {
        return secretCode;
    }

    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }

    @Override
    public String toString() {
        return "ResetPasswordDTO{" +
                "secretCode='" + secretCode + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

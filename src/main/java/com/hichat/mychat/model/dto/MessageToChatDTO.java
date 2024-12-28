package com.hichat.mychat.model.dto;

import jakarta.validation.constraints.*;

public class MessageToChatDTO {

    @NotNull
    @Size(min = 1, max = 1000)
    private String message;

    @NotNull
    private Integer idOfReceiver;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getIdOfReceiver() {
        return idOfReceiver;
    }

    public void setIdOfReceiver(Integer idOfReceiver) {
        this.idOfReceiver = idOfReceiver;
    }
}

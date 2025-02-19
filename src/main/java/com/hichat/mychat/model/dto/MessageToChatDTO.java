package com.hichat.mychat.model.dto;

import com.hichat.mychat.model.enumclass.DataType;
import jakarta.validation.constraints.*;

public class MessageToChatDTO {

    @NotNull
    private DataType contentType;

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

    public DataType getContentType() {
        return contentType;
    }

    public void setContentType(DataType contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return "MessageToChatDTO{" +
                "message='" + message + '\'' +
                ", idOfReceiver=" + idOfReceiver +
                '}';
    }
}

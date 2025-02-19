package com.hichat.mychat.model.response;

import com.hichat.mychat.model.entitie.MyUser;
import com.hichat.mychat.model.enumclass.DataType;

public class MessageResponse {
    private MyUser sender;
    private DataType contentType;
    private String messageText;

    public MyUser getSender() {
        return sender;
    }

    public void setSender(MyUser sender) {
        this.sender = sender;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public DataType getContentType() {
        return contentType;
    }

    public void setContentType(DataType contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return "MessageResponse{" +
                "sender=" + sender +
                ", messageText='" + messageText + '\'' +
                '}';
    }
}

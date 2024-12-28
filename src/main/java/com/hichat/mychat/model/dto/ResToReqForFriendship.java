package com.hichat.mychat.model.dto;

import com.hichat.mychat.model.enumclass.ResponseStatusEnum;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ResToReqForFriendship {
    private int idOfUser;
    private ResponseStatusEnum responseStatus;

    public int getIdOfUser() {
        return idOfUser;
    }

    public void setIdOfUser(int idOfUser) {
        this.idOfUser = idOfUser;
    }

    public ResponseStatusEnum getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatusEnum responseStatus) {
        this.responseStatus = responseStatus;
    }
}

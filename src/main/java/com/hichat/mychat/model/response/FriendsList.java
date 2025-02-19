package com.hichat.mychat.model.response;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FriendsList {
    private Integer id;
    private String publicName;

    public FriendsList(Integer id, String publicName) {
        this.id = id;
        this.publicName = publicName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPublicName() {
        return publicName;
    }

    public void setPublicName(String publicName) {
        this.publicName = publicName;
    }
}

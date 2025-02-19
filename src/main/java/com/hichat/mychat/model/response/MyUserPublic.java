package com.hichat.mychat.model.response;

import com.hichat.mychat.model.entitie.MyUser;

import java.time.LocalDateTime;
import java.util.Objects;

public class MyUserPublic {
    private Integer id;

    private String publicName;

    private Integer age;

    private String description;

    private String usersPhotoUrl;

    private Boolean isOnline;

    private LocalDateTime lastVisit;

    public MyUserPublic() {
    }

    public MyUserPublic(Integer id, String publicName, Integer age, String description, String usersPhotoUrl, Boolean isOnline, LocalDateTime lastVisit) {
        this.id = id;
        this.publicName = publicName;
        this.age = age;
        this.description = description;
        this.usersPhotoUrl = usersPhotoUrl;
        this.isOnline = isOnline;
        this.lastVisit = lastVisit;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsersPhotoUrl() {
        return usersPhotoUrl;
    }

    public void setUsersPhotoUrl(String usersPhotoUrl) {
        this.usersPhotoUrl = usersPhotoUrl;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public LocalDateTime getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(LocalDateTime lastVisit) {
        this.lastVisit = lastVisit;
    }

    public static MyUserPublic fromUserToPublic(MyUser myUser){
        return new MyUserPublic(myUser.getId(), myUser.getPublicName(), myUser.getAge(), myUser.getDescription(), myUser.getUsersPhotoUrl(), myUser.getIsOnline(), myUser.getLastVisit());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MyUserPublic that = (MyUserPublic) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

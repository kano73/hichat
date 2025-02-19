package com.hichat.mychat.model.entitie;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="my_user")
public class MyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="public_name", nullable = false, unique = true)
    private String publicName;

    @Column(name="username", nullable = false, unique = true)
    private String username;

    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="age")
    private Integer age;

    @Column(name="description")
    private String description;

    @Column(name="photo_url")
    private String usersPhotoUrl;

    @Column(name="is_online")
    private Boolean isOnline = false;

    @Column(name="last_visit")
    private LocalDateTime lastVisit;

    @Column(name="is_email_verified")
    private boolean isEmailVerified = false;

    public boolean getEmailVerified() {
        return isEmailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        isEmailVerified = emailVerified;
    }

    public Boolean getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Boolean online) {
        isOnline = online;
    }

    public LocalDateTime getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(LocalDateTime lastVisit) {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public String toString() {
        return "MyUser{" +
                "id=" + id +
                ", publicName='" + publicName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", description='" + description + '\'' +
                ", usersPhotoUrl='" + usersPhotoUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MyUser myUser = (MyUser) o;
        return Objects.equals(id, myUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

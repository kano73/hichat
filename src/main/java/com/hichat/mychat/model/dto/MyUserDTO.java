package com.hichat.mychat.model.dto;

import com.hichat.mychat.model.entitie.MyUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
public class MyUserDTO {
    @Size(min = 3, max = 200)
    private String username;

    @Size(min = 4, max = 200)
    private String password;

    @Size(min = 3, max = 200)
    private String publicName;

    @Email
    @Size(min = 3, max = 200)
    private String email;

    @Min(18)
    @Max(150)
    private Integer age;

    @Size(max = 2000)
    private String description;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPublicName() {
        return publicName;
    }

    public void setPublicName(String publicName) {
        this.publicName = publicName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void setDescription(String descrition) {
        this.description = descrition;
    }

    public MyUser convertToMyUser() {
        MyUser myUser = new MyUser();
        myUser.setUsername(username);
        myUser.setPassword(password);
        myUser.setPublicName(publicName);
        myUser.setEmail(email);
        myUser.setAge(age);
        myUser.setDescription(description);
        return myUser;
    }
}

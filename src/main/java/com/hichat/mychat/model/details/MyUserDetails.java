package com.hichat.mychat.model.details;

import com.hichat.mychat.model.entitie.MyUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails{

    private final MyUser myUser;

    public MyUserDetails(MyUser myUser) {
        this.myUser = myUser;
    }

    @Override
    public String getPassword() {
        return myUser.getPassword();
    }

    @Override
    public String getUsername() {
        return myUser.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public MyUser getMyUser() {
        return myUser;
    }
}

package com.hichat.mychat.service;

import com.hichat.mychat.model.details.MyUserDetails;
import com.hichat.mychat.model.entitie.MyUser;
import com.hichat.mychat.repository.MyUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailsService implements UserDetailsService {

    private final MyUserRepository myUserRepository;

    public MyUserDetailsService(MyUserRepository myUserRepository) {
        this.myUserRepository = myUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser = myUserRepository.findByUsernameIgnoreCase(username).orElseThrow(
                                                    () -> new UsernameNotFoundException("no user found with such username")
                );
        return new MyUserDetails(myUser);
    }
}


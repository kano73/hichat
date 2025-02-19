package com.hichat.mychat.service;

import com.hichat.mychat.exeption.EmailNotVerified;
import com.hichat.mychat.model.details.MyUserDetails;
import com.hichat.mychat.model.entitie.MyUser;
import com.hichat.mychat.repository.MyUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
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
        if(!myUser.getEmailVerified()){
            throw new EmailNotVerified("email not verified");
        }

        return new MyUserDetails(myUser);
    }
}


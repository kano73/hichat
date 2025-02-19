package com.hichat.mychat.service.validation;

import com.hichat.mychat.exeption.*;
import com.hichat.mychat.model.entitie.MyUser;
import com.hichat.mychat.repository.MyUserRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class UserValidationService {

    private final MyUserRepository myUserRepository;

    public UserValidationService(MyUserRepository myUserRepository) {
        this.myUserRepository = myUserRepository;
    }

    public boolean isCredentialsUnique(MyUser user) throws EmailAlreadyExists, UsernameAlreadyExists {
        if(myUserRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExists("this email belongs to another user");
        }
        if(myUserRepository.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExists("this username already exists");
        }

        return true;
    }
    
    public void isEmailValid(OAuth2User oAuth2User){
        String email = (String) oAuth2User.getAttributes().get("email");

        if (email == null) {
            throw new UserNeedsToRegisterWithThisEmail("");
        }

        MyUser myUser = myUserRepository.findByEmail(email).orElseThrow(()->new UserNeedsToRegisterWithThisEmail(email));
        if(!myUser.getEmailVerified()){
            throw new EmailNotVerified("email not verified");
        }
    }
}

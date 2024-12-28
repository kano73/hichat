package com.hichat.mychat.config.security;

import com.hichat.mychat.exeption.UserNotFoundException;
import com.hichat.mychat.model.details.MyUserDetails;
import com.hichat.mychat.model.entitie.MyUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedMyUserService {

    public MyUser getCurrentUserAuthenticated() throws UserNotFoundException, AuthenticationException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();

        if(userDetails == null) {
            throw new UserNotFoundException("connected user not found");
        }

        return userDetails.getMyUser();
    }
}

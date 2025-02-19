package com.hichat.mychat.config.security;

import com.hichat.mychat.exeption.UserNeedsToRegisterWithThisEmail;
import com.hichat.mychat.exeption.UserNotFoundException;
import com.hichat.mychat.model.details.MyUserDetails;
import com.hichat.mychat.model.entitie.MyUser;
import com.hichat.mychat.repository.MyUserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class AuthenticatedMyUserService {
    private final MyUserRepository myUserRepository;

    public AuthenticatedMyUserService(com.hichat.mychat.repository.MyUserRepository myUserRepository) {
        this.myUserRepository = myUserRepository;
    }

    public MyUser getCurrentUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.getPrincipal() instanceof MyUserDetails userDetails){
            return userDetails.getMyUser();
        } else if (authentication.getPrincipal() instanceof OAuth2User oAuth2User) {
            String email = (String) oAuth2User.getAttributes().get("email");
            return myUserRepository.findByEmail(email).orElseThrow(() -> new UserNeedsToRegisterWithThisEmail(email));
        }else{
            throw new UserNotFoundException("connected user not found");
        }
    }

    public MyUser getCurrUserByPrincipals(@NotNull Principal principal) throws AuthenticationException {
        if (principal instanceof UsernamePasswordAuthenticationToken authToken) {
            if (authToken.getPrincipal() instanceof MyUserDetails userDetails) {
                return userDetails.getMyUser();
            } else {
                throw new AuthenticationException("Principal is not an instance of MyUserDetails") {};
            }
        }else if (principal instanceof OAuth2AuthenticationToken oAuth2Token) {
            String email = oAuth2Token.getPrincipal().getAttribute("email");
            return myUserRepository.findByEmail(email)
                    .orElseThrow(() -> new AuthenticationException("User not found") {});
        }
        else {
            throw new AuthenticationException("Unsupported Principal type: " + principal.getClass().getName()) {};
        }
    }
}

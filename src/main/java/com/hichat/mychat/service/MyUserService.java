package com.hichat.mychat.service;

import com.hichat.mychat.config.security.AuthenticatedMyUserService;
import com.hichat.mychat.exeption.EmailAlreadyExists;
import com.hichat.mychat.exeption.PasswordsNotMatches;
import com.hichat.mychat.exeption.UserNotFoundException;
import com.hichat.mychat.exeption.UsernameAlreadyExists;
import com.hichat.mychat.model.dto.MyUserDTO;
import com.hichat.mychat.model.dto.SearchCriteria;
import com.hichat.mychat.model.entitie.MyUser;
import com.hichat.mychat.repository.MyUserRepository;
import com.hichat.mychat.repository.nativedb.MyUserJdbcRepository;
import com.hichat.mychat.service.validation.UserValidationService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserService {

    private final AuthenticatedMyUserService authService;
    private final PasswordEncoder passwordEncoder;
    private final MyUserRepository myUserRepository;
    private final UserValidationService userValidationService;
    private final MyUserJdbcRepository myUserJdbcRepository;

    public MyUserService(AuthenticatedMyUserService authService, PasswordEncoder passwordEncoder, MyUserRepository myUserRepository, UserValidationService userValidationService, MyUserJdbcRepository myUserJdbcRepository) {
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
        this.myUserRepository = myUserRepository;
        this.userValidationService = userValidationService;
        this.myUserJdbcRepository = myUserJdbcRepository;
    }

    public boolean register(@Valid MyUserDTO myUserDTO) throws UsernameAlreadyExists, EmailAlreadyExists {
        MyUser myUser = myUserDTO.convertToMyUser();
        myUser.setPassword(passwordEncoder.encode(myUserDTO.getPassword()));

        if(userValidationService.isCredentialsUnique(myUser)){
            myUserRepository.save(myUser);
            return true;
        }
        return false;
    }

    public List<MyUser> findUsersByCriteria(Integer page, SearchCriteria searchCriteria) {
        String publicName = searchCriteria.getPublicName();
        Integer ageAfter = searchCriteria.getAgeAfter();
        Integer ageBefore = searchCriteria.getAgeBefore();

        return myUserRepository.findByPublicNameStartingWithIgnoreCaseAndAgeBetween(publicName, ageBefore, ageAfter);
    }

    public boolean updateInfo(MyUserDTO userDTO) throws UserNotFoundException {
        MyUser authUser = authService.getCurrentUserAuthenticated();
        if(!passwordEncoder.matches(userDTO.getPassword(), authUser.getPassword())){
            throw new PasswordsNotMatches("password not correct");
        }
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        MyUser updatedUser = userDTO.convertToMyUser();
        updatedUser.setId(authUser.getId());

        return myUserJdbcRepository.updateUserInfoById(updatedUser);
    }

}

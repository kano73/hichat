package com.hichat.mychat.controller;

import com.hichat.mychat.exeption.EmailAlreadyExists;
import com.hichat.mychat.exeption.UserNotFoundException;
import com.hichat.mychat.exeption.UsernameAlreadyExists;
import com.hichat.mychat.model.dto.MyUserDTO;
import com.hichat.mychat.model.dto.ResetPasswordDTO;
import com.hichat.mychat.model.dto.SecretCodeDTO;
import com.hichat.mychat.model.entitie.MyUser;
import com.hichat.mychat.repository.MyUserRepository;
import com.hichat.mychat.service.MyUserService;
import com.hichat.mychat.service.PasswordRecoveryService;
import com.hichat.mychat.service.validation.MailValidationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class MyUserController {
    private final MyUserService myUserService;
    private final MyUserRepository myUserRepository;
    private final PasswordRecoveryService passwordRecoveryService;
    private final MailValidationService mailValidationService;

    public MyUserController(MyUserService myUserService, MyUserRepository myUserRepository, PasswordRecoveryService passwordRecoveryService, MailValidationService mailValidationService) {
        this.myUserService = myUserService;
        this.myUserRepository = myUserRepository;
        this.passwordRecoveryService = passwordRecoveryService;
        this.mailValidationService = mailValidationService;
    }

    @PostMapping("/register")
    public String resiter(@RequestBody @Valid MyUserDTO myUserDTO) throws UsernameAlreadyExists, EmailAlreadyExists {
        System.out.println(myUserDTO.getDescription());
        return myUserService.register(myUserDTO)  ? "successful registration" : "fail";
    }

    @PostMapping("/recovery")
    public String recoveryPassword(@RequestBody @NotNull MyUser sentData) throws UserNotFoundException {
        MyUser user = myUserRepository.findByEmail(sentData.getEmail()).orElseThrow(()->new UserNotFoundException("no user exists with this email"));
        return passwordRecoveryService.preparationForPasswordRecovery(user) ? "check your mail" : "an error accused((";
    }

    @PostMapping("/reset_password/{pathCode}")
    public String resetPassword(@PathVariable @Size(min=12, max=12) @Valid String pathCode, @RequestBody @Valid ResetPasswordDTO resetPasswordDTO, HttpServletRequest request){
        String currentPath = request.getRequestURI();
        return passwordRecoveryService.resetPassword(currentPath, resetPasswordDTO) ? "redirect:/logout" : "failed";
    }

    @PostMapping("/confirm_mail/{pathCode}")
    public String confirmMail(@PathVariable @Size(min=12, max=12) @Valid String pathCode, @RequestBody SecretCodeDTO secretCodeDTO, HttpServletRequest request){
        String currentPath = request.getRequestURI();
        return mailValidationService.confirmMail(currentPath,secretCodeDTO) ? "redirect:/logout" : "failed";
    }



}

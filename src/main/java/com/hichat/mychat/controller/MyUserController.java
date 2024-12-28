package com.hichat.mychat.controller;

import com.hichat.mychat.exeption.EmailAlreadyExists;
import com.hichat.mychat.exeption.UsernameAlreadyExists;
import com.hichat.mychat.model.dto.MyUserDTO;
import com.hichat.mychat.service.MyUserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyUserController {

    private final MyUserService myUserService;

    public MyUserController(MyUserService myUserService) {
        this.myUserService = myUserService;
    }

    @PostMapping("/register")
    public String resiter(@RequestBody @Valid MyUserDTO myUserDTO) throws UsernameAlreadyExists, EmailAlreadyExists {
        System.out.println(myUserDTO.getDescription());
        return myUserService.register(myUserDTO) ? "successful registration" : "fail";
    }
}

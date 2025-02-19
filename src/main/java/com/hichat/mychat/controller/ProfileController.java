package com.hichat.mychat.controller;

import com.hichat.mychat.exeption.UserNotFoundException;
import com.hichat.mychat.model.dto.MyUserDTO;
import com.hichat.mychat.service.MyUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class ProfileController {

    private final MyUserService myUserService;

    public ProfileController(MyUserService myUserService) {
        this.myUserService = myUserService;
    }

    @PutMapping("/update_profile")
    public @ResponseBody String updateProfile(@RequestBody MyUserDTO userDTO) throws UserNotFoundException {
        return myUserService.updateInfo(userDTO)  ? "info updated" : "info not updated";
    }

    @PostMapping("/hichat-users-photos/upload-profile-photo")
    public @ResponseBody String uploadFile(@RequestParam("file") MultipartFile file) throws IOException, UserNotFoundException {

        return myUserService.uploadProfilePhoto(file) ?
                "File uploaded successfully"
                : "File upload failed";
    }
}

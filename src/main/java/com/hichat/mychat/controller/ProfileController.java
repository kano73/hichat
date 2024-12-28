package com.hichat.mychat.controller;

import com.hichat.mychat.exeption.UserNotFoundException;
import com.hichat.mychat.exeption.UserOpenedHisPublicProfile;
import com.hichat.mychat.model.dto.MyUserDTO;
import com.hichat.mychat.service.MyUserService;
import com.hichat.mychat.service.PageBuilderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfileController {

    private final PageBuilderService pageBuilderService;
    private final MyUserService myUserService;

    public ProfileController(PageBuilderService pageBuilderService, MyUserService myUserService) {
        this.pageBuilderService = pageBuilderService;
        this.myUserService = myUserService;
    }

    @GetMapping("/public_profile")
    public String publicProfile(@RequestParam int id, Model model) throws UserNotFoundException, UserOpenedHisPublicProfile {
        model = pageBuilderService.buildPublicProfile(id,model);

        return "publicProfile";
    }

    @GetMapping("/profile")
    public String profile(Model model) throws UserNotFoundException {
        model = pageBuilderService.buildProfilePage(model);

        return "profile";
    }

    @GetMapping("/update_profile")
    public String updateProfile(Model model) throws UserNotFoundException {
        model = pageBuilderService.buildUpdateProfilePage(model);
        return "update_profile";
    }

    @PutMapping("/update_profile")
    public @ResponseBody String updateProfile(@RequestBody MyUserDTO userDTO) throws UserNotFoundException {
        return myUserService.updateInfo(userDTO)  ? "ok" : "fail";
    }
}

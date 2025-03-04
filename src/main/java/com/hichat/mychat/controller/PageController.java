package com.hichat.mychat.controller;

import com.hichat.mychat.exeption.UserNotFoundException;
import com.hichat.mychat.exeption.UserOpenedHisPublicProfile;
import com.hichat.mychat.model.dto.SearchCriteria;
import com.hichat.mychat.model.entitie.MyUser;
import com.hichat.mychat.service.MyUserService;
import com.hichat.mychat.service.PageBuilderService;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PageController {

    private final PageBuilderService pageBuilderService;
    private final MyUserService myUserService;

    public PageController(PageBuilderService pageBuilderService, MyUserService myUserService) {
        this.pageBuilderService = pageBuilderService;
        this.myUserService = myUserService;
    }

    @GetMapping("/main")
    public String getMain(){
        return "main";
    }

    @GetMapping("/mychats")
    public String mychats(Model model) throws UserNotFoundException{
        pageBuilderService.buildMyChatsPage(model);

        return "mychats";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/notfound")
    public String notfound() {
        return "notfound";
    }

    @GetMapping("/public_profile")
    public String publicProfile(@RequestParam int id, Model model) throws UserNotFoundException, UserOpenedHisPublicProfile {
        pageBuilderService.buildPublicProfile(id,model);

        return "public_profile";
    }

    @GetMapping("/profile")
    public String profile(Model model) throws UserNotFoundException {
        pageBuilderService.buildProfilePage(model);

        return "profile";
    }

    @GetMapping("/update_profile")
    public String updateProfile(Model model) throws UserNotFoundException {
        pageBuilderService.buildUpdateProfilePage(model);
        return "update_profile";
    }

    @GetMapping("/search")
    public String search() {
        return "search";
    }

    @PostMapping("/search")
    public @ResponseBody List<MyUser> search(@RequestParam @Nullable Integer page, @RequestBody SearchCriteria searchCriteria){
        if(page==null){
            page=0;
        }

        return myUserService.findUsersByCriteria(searchCriteria);
    }

    @GetMapping("/requests_to_friendship")
    public String requestsToMe(Model model) throws UserNotFoundException {
        pageBuilderService.buildRequestsForFriendShip(model);
        return "requests_to_friendship";
    }

    @GetMapping("/chatwith")
    public String chatwith(Model model, @RequestParam int id) throws UserNotFoundException {
        pageBuilderService.buildChatWithPage(model,id);

        return "chat_with";
    }

    @GetMapping("/recovery")
    public String recoveryPassword(){
        return "recovery";
    }

    @GetMapping("/reset_password")
    public String resetPassword(){
        return "reset_password";
    }

    @GetMapping("/confirm_mail")
    public String confirmMail(){
        return "confirm_mail";
    }

    @GetMapping("/near_by")
    public String nearBy(){
        return "near_by";
    }
}

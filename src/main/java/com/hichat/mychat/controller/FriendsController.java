package com.hichat.mychat.controller;

import com.hichat.mychat.exeption.InviteAlreadyExists;
import com.hichat.mychat.exeption.UserNotFoundException;
import com.hichat.mychat.model.dto.ResToReqForFriendship;
import com.hichat.mychat.service.FriendsService;
import org.springframework.web.bind.annotation.*;

@RestController
public class FriendsController {
    private final FriendsService friendsService;

    public FriendsController(FriendsService friendsService) {
        this.friendsService = friendsService;
    }

    @PostMapping("/public_profile")
    public String requestForFriendShip(@RequestBody int id) throws UserNotFoundException, InviteAlreadyExists {
        return friendsService.requestForFriendshipById(id) ? "success" : "fail";
    }

    @PostMapping("/requests_to_friendship")
    public String addFriend(@RequestBody ResToReqForFriendship response) throws UserNotFoundException {
        return friendsService.respondToRequest(response);
    }

    @DeleteMapping("/public_profile")
    public String removeFriend(@RequestBody int id) throws UserNotFoundException {
        System.out.println("yoooooooooo chell");
        return friendsService.removeFromFriends(id) ? "success" : "fail";
    }
}

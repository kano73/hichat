package com.hichat.mychat.controller;

import com.hichat.mychat.config.security.AuthenticatedMyUserService;
import com.hichat.mychat.config.storage.UsersAndLocationsStorage;
import com.hichat.mychat.model.dto.LocationDTO;
import com.hichat.mychat.model.entitie.MyUser;
import com.hichat.mychat.model.enumclass.OperationEnum;
import com.hichat.mychat.model.response.LocationResponse;
import com.hichat.mychat.model.response.MyUserPublic;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class LocationController {

//    todo: in general: is this approach ok? (saving locations in ram)

    private final AuthenticatedMyUserService authService;
    private final SimpMessagingTemplate messagingTemplate;
    private final UsersAndLocationsStorage usersAndLocationsStorage;

    public LocationController(AuthenticatedMyUserService authService, SimpMessagingTemplate messagingTemplate, UsersAndLocationsStorage usersAndLocationsStorage) {
        this.authService = authService;
        this.messagingTemplate = messagingTemplate;
        this.usersAndLocationsStorage = usersAndLocationsStorage;
    }

    @MessageMapping("/locations")
    @SendTo("/sp_send/locations")
    public LocationResponse forwardMessage(LocationDTO locationDTO, @NotNull Principal principal) {
        MyUser authUser = authService.getCurrUserByPrincipals(principal);
        LocationResponse locationResponse = LocationResponse.fromLocationDTO(authUser, locationDTO);

        usersAndLocationsStorage.addOrUpdateUser(locationResponse.getUser(), locationDTO);

        return locationResponse;
    }

    @PostMapping("/near_by")
    public List<LocationResponse> postLocation(@Valid @RequestBody LocationDTO locationDTO) {
        MyUser authUser = authService.getCurrentUserAuthenticated();
        LocationResponse locationResponse = LocationResponse.fromLocationDTO(authUser, locationDTO);

        List<LocationResponse> allLocations = usersAndLocationsStorage.getAllLocationResponse();

        usersAndLocationsStorage.addOrUpdateUser(locationResponse.getUser(), locationDTO);
        messagingTemplate.convertAndSend("/sp_send/locations", locationResponse);

        return allLocations;
    }

    public void deleteLocation(MyUser user){
        usersAndLocationsStorage.removeUser(user.getId());

        LocationResponse locationResponse = new LocationResponse();
        locationResponse.setOperation(OperationEnum.DELETE);
        locationResponse.setUser(MyUserPublic.fromUserToPublic(user));

        messagingTemplate.convertAndSend("/sp_send/locations", locationResponse);
    }
}

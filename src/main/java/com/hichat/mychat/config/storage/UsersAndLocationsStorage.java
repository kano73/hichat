package com.hichat.mychat.config.storage;

import com.hichat.mychat.model.dto.LocationDTO;
import com.hichat.mychat.model.response.LocationResponse;
import com.hichat.mychat.model.response.MyUserPublic;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@Component
public class UsersAndLocationsStorage {
    private static final ConcurrentHashMap<MyUserPublic, LocationDTO> usersAndLocations = new ConcurrentHashMap<>();

    public ConcurrentHashMap<MyUserPublic, LocationDTO> getAllUsersAndLocations() {
        return usersAndLocations;
    }

    public void addOrUpdateUser(MyUserPublic user, LocationDTO location) {
        usersAndLocations.put(user, location);
    }

    public void removeUser(Integer userId) {
        MyUserPublic user = new MyUserPublic();
        user.setId(userId);
        usersAndLocations.remove(user);
    }

    public List<LocationResponse> getAllLocationResponse(){
        return getAllUsersAndLocations().entrySet().stream()
                .map(entry -> {
                    LocationResponse locationResponse = new LocationResponse();
                    locationResponse.setUser(entry.getKey());
                    locationResponse.setLatAndLng(entry.getValue());
                    return locationResponse;
                })
                .collect(Collectors.toList());
    }
}

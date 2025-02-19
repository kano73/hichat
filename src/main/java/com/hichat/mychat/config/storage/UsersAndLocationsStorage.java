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
    private static ConcurrentHashMap<MyUserPublic, LocationDTO> usersAndLocations = new ConcurrentHashMap<>();

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

    private double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        final int R = 6371; // Радиус Земли в км
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}

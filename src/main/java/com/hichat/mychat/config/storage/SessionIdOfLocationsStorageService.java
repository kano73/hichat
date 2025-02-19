package com.hichat.mychat.config.storage;

import com.hichat.mychat.controller.LocationController;
import com.hichat.mychat.model.entitie.MyUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class SessionIdOfLocationsStorageService {
    private final List<String> sessionIdList = Collections.synchronizedList(new ArrayList<>());

    private final LocationController locationController;

    public SessionIdOfLocationsStorageService(LocationController locationController) {
        this.locationController = locationController;
    }

    public void saveUserLocationSession(SessionConnectEvent event, MyUser user){
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String endpoint = accessor.getFirstNativeHeader("endpoint");

        if (Objects.equals(endpoint, "/location")) {
            String sessionId = accessor.getSessionId();
            sessionIdList.add(sessionId);
        }
    }

    public void removeUserLocationSession(SessionDisconnectEvent event, MyUser user){
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();

        if (sessionIdList.remove(sessionId)) {
            locationController.deleteLocation(user);
        }
    }
}

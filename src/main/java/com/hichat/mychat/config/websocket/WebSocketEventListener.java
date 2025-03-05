package com.hichat.mychat.config.websocket;

import com.hichat.mychat.config.security.AuthenticatedMyUserService;
import com.hichat.mychat.config.storage.SessionIdOfLocationsStorageService;
import com.hichat.mychat.model.entitie.MyUser;
import com.hichat.mychat.service.MyUserService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    private final SessionIdOfLocationsStorageService sessionStorage;
    private final AuthenticatedMyUserService authenticatedMyUserService;
    private final MyUserService myUserService;

    public WebSocketEventListener(SessionIdOfLocationsStorageService sessionStorage, AuthenticatedMyUserService authenticatedMyUserService, MyUserService myUserService) {
        this.sessionStorage = sessionStorage;
        this.authenticatedMyUserService = authenticatedMyUserService;
        this.myUserService = myUserService;
    }

//    todo: online status impl, always we need to communicate with db

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        MyUser user = authenticatedMyUserService.getCurrUserByPrincipals(event.getUser());
        myUserService.setOnlineStatus(user.getId(), true);

        sessionStorage.saveUserLocationSession(event);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        MyUser user = authenticatedMyUserService.getCurrUserByPrincipals(event.getUser());
        myUserService.setOnlineStatus(user.getId(), false);

        sessionStorage.removeUserLocationSession(event,user);
    }
}

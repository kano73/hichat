package com.hichat.mychat.config.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer  {

   @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
       registry.addEndpoint("/location");
       registry.addEndpoint("/chat");
       registry.addEndpoint("/connection");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sp_send");
        config.setApplicationDestinationPrefixes("/sp_get");
    }
}
package com.polytech.greenhouse.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // The endpoint the frontend connects to (e.g., http://localhost:8083/ws-alerts)
        registry.addEndpoint("/ws-alerts")
                .setAllowedOriginPatterns("*") // Allow connections from any frontend (localhost:4200 etc.)
                .withSockJS(); // Enable SockJS fallback
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Enable a simple broker for the "/topic" prefix
        registry.enableSimpleBroker("/topic");
        // Prefix for messages bound for methods annotated with @MessageMapping
        registry.setApplicationDestinationPrefixes("/app");
    }
}
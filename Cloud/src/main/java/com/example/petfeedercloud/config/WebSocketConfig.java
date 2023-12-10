package com.example.petfeedercloud.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;



@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketHandler webSocketHandler;
    private final WebSocketPetFeeder webSocketPetFeeder;
    private final WebSocketNotification webSocketNotification;

    public WebSocketConfig(WebSocketHandler webSocketHandler, WebSocketPetFeeder webSocketPetFeeder, WebSocketNotification webSocketNotification) {
        this.webSocketHandler = webSocketHandler;
        this.webSocketPetFeeder = webSocketPetFeeder;
        this.webSocketNotification = webSocketNotification;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "").setAllowedOrigins("*");
        registry.addHandler(webSocketPetFeeder, "/petfeeder/info/{petFeederId}").setAllowedOrigins("*");
        registry.addHandler(webSocketNotification, "/notificationsWS/{petFeederId}").setAllowedOrigins("*");
    }
}
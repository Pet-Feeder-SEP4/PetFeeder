package com.example.petfeedercloud.config;

import com.example.petfeedercloud.models.PetFeeder;
import com.example.petfeedercloud.repositories.NotificationRepository;
import com.example.petfeedercloud.services.NotificationService;
import com.example.petfeedercloud.services.NotificationServiceImpl;
import com.example.petfeedercloud.services.PetFeederService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class WebSocketNotification extends TextWebSocketHandler {

    @Autowired
    private PetFeederService petFeederService;

    @Autowired
    private NotificationService notificationService;

    private static final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String petFeederId = extractPetFeederIdFromUrl(session);
        if (petFeederId != null) {
            session.getAttributes().put("petFeederId", petFeederId);
        }
        sessions.add(session);
        sendNotificationUpdateToSessions(Long.valueOf(petFeederId));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    public void sendNotificationUpdateToSessions(Long petFeederId) throws JsonProcessingException {
        for (WebSocketSession session : sessions) {
            Map<String, Object> attributes = session.getAttributes();
            Long sessionPetFeederId = Long.valueOf(String.valueOf(attributes.get("petFeederId")));
            if (petFeederId.equals(sessionPetFeederId)) {
                try {
                    if(notificationService.isNotificationActive(sessionPetFeederId)){
                        for (String alert: notificationService.sendAlert(sessionPetFeederId)) {
                            session.sendMessage(new TextMessage("Pet Feeder: "+sessionPetFeederId+" "+alert));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String extractPetFeederIdFromUrl(WebSocketSession session) {
        String url = session.getUri().toString();
        String petFeederId = null;
        int index = url.lastIndexOf("/");
        if (index != -1 && index < url.length() - 1) {
            petFeederId = url.substring(index + 1);
        }
        return petFeederId;
    }
}
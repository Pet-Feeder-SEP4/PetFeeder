package com.example.petfeedercloud.config;

import com.example.petfeedercloud.dtos.PetFeederDTO;
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
        String userId = extractUserIdFromUrl(session);
        if (userId != null) {
            session.getAttributes().put("userId", userId);
        }
        sessions.add(session);
        sendNotificationUpdateToSessions(Long.valueOf(userId));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    public void sendNotificationUpdateToSessions(Long userId) throws JsonProcessingException {
        for (WebSocketSession session : sessions) {
            Map<String, Object> attributes = session.getAttributes();
            Long sessionUserId = Long.valueOf(String.valueOf(attributes.get("userId")));
            List<PetFeederDTO> petFeeders = petFeederService.getAllPetFeedersByUser(sessionUserId);
            if (userId.equals(sessionUserId)) {
                try {
                    for (PetFeederDTO pf: petFeeders) {
                        if(notificationService.isNotificationActive(pf.getPetFeederId())){
                            for (String alert: notificationService.sendAlert(pf.getPetFeederId())) {
                                session.sendMessage(new TextMessage("Pet Feeder: "+pf.getPetFeederId()+" "+alert));
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String extractUserIdFromUrl(WebSocketSession session) {
        String url = session.getUri().toString();
        String petFeederId = null;
        int index = url.lastIndexOf("/");
        if (index != -1 && index < url.length() - 1) {
            petFeederId = url.substring(index + 1);
        }
        return petFeederId;
    }
}
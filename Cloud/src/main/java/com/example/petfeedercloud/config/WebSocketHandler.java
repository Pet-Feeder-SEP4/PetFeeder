package com.example.petfeedercloud.config;

import com.example.petfeedercloud.dtos.PetDTO;
import com.example.petfeedercloud.models.Pet;
import com.example.petfeedercloud.services.PetFeederService;
import com.example.petfeedercloud.services.PetService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
public class WebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private PetService petService;
    @Autowired
    private PetFeederService petFeederService;

    private static final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String petFeederId = extractPetFeederIdFromUrl(session);
        if (petFeederId != null) {
            session.getAttributes().put("petFeederId", petFeederId);
        }
        sessions.add(session);
        sendPetFeederUpdateToSessions(Long.valueOf(petFeederId));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }



    public void sendPetFeederUpdateToSessions(Long petFeederId) throws JsonProcessingException {
        PetDTO p = petService.getPetById(petFeederId);
        String petJson = "petService.convertPetToJson(p)";
        for (WebSocketSession session : sessions) {
            Map<String, Object> attributes = session.getAttributes();
            Long sessionPetFeederId = Long.valueOf(String.valueOf(attributes.get("petFeederId")));
            if (petFeederId.equals(sessionPetFeederId)) {
                try {
                    session.sendMessage(new TextMessage(petJson));
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

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        // Example: Check if the incoming message requests humidity data
        if ("getHumidity".equals(payload)) {
            Long petFeederId = Long.valueOf(String.valueOf(session.getAttributes().get("petFeederId")));
            // Assuming you have a method in PetFeederService to get humidity
            int humidity = petFeederService.getHumidityForPetFeeder(petFeederId);

            // Convert humidity to JSON or String
            String humidityJson = "{\"humidity\": " + humidity + "}";

            // Send the humidity data back to the WebSocket client
            try {
                session.sendMessage(new TextMessage(humidityJson));
            } catch (IOException e) {
                // Handle or log the IOException
            }
        } else {
            // Handle other message types or unrecognized messages
        }
    }

}
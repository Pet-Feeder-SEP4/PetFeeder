package com.example.petfeedercloud.config;

import com.example.petfeedercloud.dtos.PetDTO;
import com.example.petfeedercloud.models.Pet;
import com.example.petfeedercloud.services.PetService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
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

    private static final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    //Simply overridding the method that handles messages from the websockets
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException, IOException {
        //run trough all websockets sessions that are open
        for(WebSocketSession webSocketSession : sessions) {
            //get messages from webSocketSession and parse from json and put into string
            Map value = new Gson().fromJson(message.getPayload(), Map.class);
            //here its getting the attributes from the webSocketSession in this case getting the petFeederId that its set when he connects
            Map<String, Object> attributes = webSocketSession.getAttributes();
            //saving the petfeederid from this webSocketSession
            Long sessionPetFeederId = Long.valueOf(String.valueOf(attributes.get("petFeederId")));
            //prints value received from webSocketSession for example
            //if message sent from other side is "{"test":"test"}" this SOUT will return null
            //if message sent from other side is "{"name":"name"}" this SOUT will return "name"
            System.out.println(value.get("name"));
            System.out.println(value.get("humidity"));
            System.out.println(value.get("foodlevel"));
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //when client connects to websockets, will get the petfeederid from the url link using this function extractPetFeederIdFromUrl()
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
        //basically this function is to send updates to the sessions when something update, for example you can put this function in some endpoint, so when that endpoint is called
        //it will call this function and send the updates to the sessions listening to the websockets
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

}
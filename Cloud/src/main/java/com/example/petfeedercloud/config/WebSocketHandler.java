package com.example.petfeedercloud.config;

import com.example.petfeedercloud.dtos.PetDTO;
import com.example.petfeedercloud.dtos.PetFeederDTO;
import com.example.petfeedercloud.models.Pet;
import com.example.petfeedercloud.models.PetFeeder;
import com.example.petfeedercloud.services.PetFeederService;
import com.example.petfeedercloud.services.PetFeederServiceImpl;
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
    private PetFeederService petFeederService;

    @Autowired
    private WebSocketPetFeeder webSocketPetFeeder;

    private static final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException, IOException {
        for(WebSocketSession webSocketSession : sessions) {
            Map value = new Gson().fromJson(message.getPayload(), Map.class);
            Map<String, Object> attributes = webSocketSession.getAttributes();
            PetFeeder pf = new PetFeeder();
            if(attributes.get("petFeederId")==null || attributes.get("petFeederId").equals(""))
                webSocketSession.getAttributes().put("petFeederId",String.valueOf(value.get("petFeederId")));
            if(attributes.get("petFeederId")!=null || !attributes.get("petFeederId").equals(""))
                pf = petFeederService.getPetFeederById(Long.valueOf(String.valueOf(attributes.get("petFeederId"))));

            PetFeederDTO pfd = new PetFeederDTO();
            pfd.setUserId(pf.getUser().getUserId());
            pfd.setScheduleId(null);
            pfd.setPetId(pf.getPet().getPetId());
            pfd.setActive(pf.isActive());
            pfd.setFoodLevel(Integer.parseInt(String.valueOf(value.get("foodLevel"))));
            pfd.setFoodHumidity(Integer.parseInt(String.valueOf(value.get("foodHumidity"))));
            pfd.setWaterTemperture(Integer.parseInt(String.valueOf(value.get("waterTemperature"))));
            pfd.setWaterLevel(Integer.parseInt(String.valueOf(value.get("waterLevel"))));
            pfd.setPetFeederId(pf.getPetFeederId());
            pfd.setLowLevelFood(pfd.getLowLevelFood());
            pfd.setPetFeederLabel(pf.getPetFeederLabel());
            petFeederService.saveOrUpdatePetFeeder(pfd);
            webSocketPetFeeder.sendPetFeederUpdateToSessions(pfd.getPetFeederId());
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        /*String petFeederId = extractPetFeederIdFromUrl(session);
        if (petFeederId != null) {
            session.getAttributes().put("petFeederId", petFeederId);
        }
        sessions.add(session);
        sendPetFeederUpdateToSessions(Long.valueOf(petFeederId));*/
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    public void sendPortionToPetFeeder(Long petFeederId, String portion){
        for (WebSocketSession webSocketSession : sessions){
            Map<String, Object> attributes = webSocketSession.getAttributes();
            Long sessionPetFeederId = Long.valueOf(String.valueOf(attributes.get("petFeederId")));
            if(petFeederId == sessionPetFeederId){
                try{
                    System.out.println(String.valueOf(attributes.get("petFeederId"))+"test");
                    webSocketSession.sendMessage(new TextMessage("   DIS:"+portion));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
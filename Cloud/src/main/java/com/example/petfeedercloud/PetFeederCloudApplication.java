package com.example.petfeedercloud;

import com.example.petfeedercloud.config.WebSocketPetFeeder;
import com.example.petfeedercloud.dtos.PetFeederDTO;
import com.example.petfeedercloud.models.PetFeeder;
import com.example.petfeedercloud.models.Time;
import com.example.petfeedercloud.repositories.TimeRepository;
import com.example.petfeedercloud.services.PetFeederService;
import com.example.petfeedercloud.services.TimeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@SpringBootApplication
@EnableScheduling
@ComponentScan("com.example.petfeedercloud.services")
@ComponentScan("com.example.petfeedercloud.config")
public class PetFeederCloudApplication{

    @Autowired
    private static PetFeederService petFeederService;


    @Autowired
    private static WebSocketPetFeeder webSocketPetFeeder;

    private static final Map<Long,Socket> sessions = new ConcurrentHashMap<>();


    //schedule feedings

    public PetFeederCloudApplication(PetFeederService petFeederService, WebSocketPetFeeder webSocketPetFeeder) {
        this.petFeederService = petFeederService;
        this.webSocketPetFeeder = webSocketPetFeeder;
    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(PetFeederCloudApplication.class, args);
        petFeederService = context.getBean(PetFeederService.class);
        webSocketPetFeeder = context.getBean(WebSocketPetFeeder.class);
        PetFeederCloudApplication application = new PetFeederCloudApplication(petFeederService, webSocketPetFeeder);
        int port = 23; // Replace with the port number you want to use

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                //scheduled feeding
                application.executeScheduledTask();
                // Wait for a client to connect
                Socket clientSocket = serverSocket.accept();

                // Get PetfeederId1
                Long petFeederId = Long.valueOf(application.waitForSignal(clientSocket));
                System.out.println("PetFeeder: " + petFeederId + " connected.");

                // Store the client socket in the sessions map
                application.sessions.put(petFeederId, clientSocket);

                Thread receiveThread = new Thread(() -> application.receiveData(clientSocket, petFeederId));
                receiveThread.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    private void executeScheduledTask() {
        List<Object[]> activeTimes = petFeederService.findTimesAndPetFeederIdWherePetFeederIsActive();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date currentTime = new Date();
        System.out.println("Current time->"+currentTime);
        for (Object[] timeAndPetFeederId : activeTimes) {
            Time time = (Time) timeAndPetFeederId[0];
            Long petFeederId = (Long) timeAndPetFeederId[1];

            try {

                Date scheduledTime = sdf.parse(time.getTime());
                if (currentTime.after(scheduledTime)) {
                    sendPortionToPetFeeder(petFeederId, String.valueOf(time.getPortionSize()));
                }
            } catch (ParseException e) {
                e.printStackTrace();

            }
        }
    }
    private String waitForSignal(Socket clientSocket) {
        try {
            /*BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String signal = reader.readLine();
            System.out.println("Received signal from client: " + signal);*/
            InputStream inputStream = clientSocket.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);
            if (bytesRead != -1) {
                String receivedData = new String(buffer, 0, bytesRead);
                System.out.println("Received: " + receivedData);
                return receivedData;
            }
            return "";
        } catch (IOException e) {
            return ""+e;
        }
    }

    private void receiveData(Socket clientSocket, Long petFeederId) {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            clientSocket.getOutputStream().write("DIS:50".getBytes());
            while (true) {
                /*
                String receivedData = reader.readLine(); // Read a line of text
                if (receivedData != null) {
                    System.out.println("Received from PetFeeder " + petFeederId + ": " + receivedData);
                    processReceivedData(receivedData,petFeederId);
                 */
                byte[] buffer = new byte[1024];
                int bytesRead = inputStream.read(buffer);
                if (bytesRead != -1) {
                    String receivedData = new String(buffer, 0, bytesRead);
                    System.out.println("Received: " + receivedData);
                    processReceivedData(receivedData,petFeederId);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processReceivedData(String receivedData, Long petFeederId) throws JsonProcessingException {
        Map value = new Gson().fromJson(receivedData, Map.class);
        PetFeeder pf = petFeederService.getPetFeederById(petFeederId);
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
        pfd.setPetFeederLabel(pf.getPetFeederLabel());
        petFeederService.saveOrUpdatePetFeeder(pfd);
        webSocketPetFeeder.sendPetFeederUpdateToSessions(pfd.getPetFeederId());
    }

    public void sendPortionToPetFeeder(Long petFeederId, String portion){
        for (var session : sessions.entrySet()){
            if(petFeederId == session.getKey()){
                try{
                    OutputStream outputStream = session.getValue().getOutputStream();
                    outputStream.write(("DIS:"+portion).getBytes());
                    System.out.println("sent: DIS:"+portion+" to petfeeder:"+petFeederId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
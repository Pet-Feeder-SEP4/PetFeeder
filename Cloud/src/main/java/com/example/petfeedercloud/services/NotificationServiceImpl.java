package com.example.petfeedercloud.services;

import com.example.petfeedercloud.dtos.GetDTOs.GetNotificationDTO;
import com.example.petfeedercloud.dtos.NotificationDTO;
import com.example.petfeedercloud.models.Notification;
import com.example.petfeedercloud.models.PetFeeder;
import com.example.petfeedercloud.repositories.NotificationRepository;
import com.example.petfeedercloud.repositories.PetFeederRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    private final NotificationRepository notificationRepository;
    private final PetFeederRepository petFeederRepository;
    @Override
    public void createNotification(NotificationDTO notificationDTO) {
        try {
            notificationRepository.save(convertToEntity(notificationDTO));
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
    @Override
    public void updateNotification(Long notificationId, NotificationDTO notificationDTO) {
        try {
            Notification existingNotification = notificationRepository.findById(notificationId)
                    .orElseThrow(() -> new NotFoundException("Notification not found"));
            existingNotification.setFoodLevel(notificationDTO.getFoodLevel());
            existingNotification.setFoodHumidity(notificationDTO.getFoodHumidity());
            existingNotification.setWaterTemperture(notificationDTO.getWaterTemperture());
            existingNotification.setWaterLevel(notificationDTO.getWaterLevel());
            PetFeeder petFeeder = petFeederRepository.findByPetFeederId(notificationDTO.getPetFeeder());
            existingNotification.setPetFeeder(petFeeder);
            existingNotification.setActive(notificationDTO.isActive());
            notificationRepository.save(existingNotification);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void activateNotification(Long notificationId) {
        try {
            Notification existingNotification = notificationRepository.findById(notificationId)
                    .orElseThrow(() -> new NotFoundException("Notification not found"));
            existingNotification.setActive(true);
            notificationRepository.save(existingNotification);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public GetNotificationDTO getNotificationByPetFeederId(Long petFeederId) {
        try {
            Notification existingNotification = notificationRepository.getNotificationByPetFeederId(petFeederId);
            return convertToDTO(existingNotification);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deactivateNotification(Long notificationId) {
        try {
            Notification existingNotification = notificationRepository.findById(notificationId)
                    .orElseThrow(() -> new NotFoundException("Notification not found"));

            existingNotification.setActive(false);
            notificationRepository.save(existingNotification);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    //helpers
    public Notification convertToEntity(NotificationDTO notificationDTO){
        PetFeeder petFeeder = petFeederRepository.findByPetFeederId(notificationDTO.getPetFeeder());
        Notification notification = new Notification( notificationDTO.getFoodLevel(),notificationDTO.getFoodHumidity(), notificationDTO.getWaterTemperture(), notificationDTO.getWaterLevel(), petFeeder,notificationDTO.isActive());
        return notification;
    }
    public GetNotificationDTO convertToDTO(Notification notification){
        GetNotificationDTO notificationDTO = new GetNotificationDTO();
        notificationDTO.setNotificationId(notification.getNotificationId());
        notificationDTO.setActive(notification.isActive());
        notificationDTO.setFoodLevel(notification.getFoodLevel());
        notificationDTO.setFoodHumidity(notification.getFoodHumidity());
        notificationDTO.setWaterTemperture(notification.getWaterTemperture());
        notificationDTO.setWaterLevel(notification.getWaterLevel());
        notificationDTO.setPetFeeder(notification.getPetFeeder().getPetFeederId());

        return notificationDTO;
    }

    @Override
    public boolean isNotificationActive(Long petFeederId){
        Notification notification = notificationRepository.getNotificationByPetFeederId(petFeederId);
        return notification.isActive();
    }

    @Override
    public List<String> sendAlert(Long petFeederId) {
        List<String> alerts = new ArrayList<String>();
        Notification notification = notificationRepository.getNotificationByPetFeederId(petFeederId);
        PetFeeder petFeeder = petFeederRepository.findByPetFeederId(petFeederId);
        if(notification.getFoodHumidity()>=petFeeder.getFoodHumidity())
            alerts.add("food humidity is low");
        if(notification.getWaterTemperture()<=petFeeder.getWaterTemperture())
            alerts.add("water temperature is high");
        if(notification.getFoodLevel()>=petFeeder.getFoodLevel())
            alerts.add("food level is low");
        if(notification.getWaterLevel()>=petFeeder.getWaterLevel())
            alerts.add("water level is low");

        return alerts;
    }
}

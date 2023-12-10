package com.example.petfeedercloud.services;

import com.example.petfeedercloud.dtos.GetDTOs.GetNotificationDTO;
import com.example.petfeedercloud.dtos.NotificationDTO;

public interface NotificationService {
    void updateNotification(Long notificationId,NotificationDTO notificationDTO);
    void createNotification(NotificationDTO notificationDTO);
    void activateNotification(Long notificationId);
    GetNotificationDTO getNotificationByPetFeederId(Long petFeederId);
    void deactivateNotification(Long notificationId);
}

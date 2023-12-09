package com.example.petfeedercloud.services;

import com.example.petfeedercloud.dtos.NotificationDTO;
import com.example.petfeedercloud.repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    private final NotificationRepository notificationRepository;

    @Override
    public void createNotification(NotificationDTO notificationDTO) {
        try {

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
    @Override
    public void updateNotification(Long notificationId, NotificationDTO notificationDTO) {

    }
    @Override
    public void activateNotification(Long notificationId) {

    }
    @Override
    public void deactivateNotification(Long notificationId) {

    }
    //helpers
}

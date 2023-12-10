package com.example.petfeedercloud.controllers;

import com.example.petfeedercloud.dtos.GetDTOs.GetNotificationDTO;
import com.example.petfeedercloud.dtos.NotificationDTO;
import com.example.petfeedercloud.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@RestController
@RequestMapping(value = "/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;


    //FE SHOULD NOT HAVE ACCESS TO CREATE NOTIFICATION I SHOULD BE CREATED WITH DEFAULT VALUES WHEN PETFEEDER IS CREATED
    @PostMapping
    public ResponseEntity<Void> createNotification(@RequestBody NotificationDTO notificationDTO) {
        try {
            notificationService.createNotification(notificationDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{notificationId}")
    public ResponseEntity<Void> updateNotification(@PathVariable Long notificationId, @RequestBody NotificationDTO notificationDTO) {
        try {
            notificationService.updateNotification(notificationId, notificationDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/pet-feeder/{petFeederId}")
    public ResponseEntity<GetNotificationDTO> getNotificationByPetFeederId(@PathVariable Long petFeederId) {
        try {
            GetNotificationDTO GetNotificationDTO = notificationService.getNotificationByPetFeederId(petFeederId);
            if (GetNotificationDTO != null) {
                return new ResponseEntity<>(GetNotificationDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{notificationId}/activate")
    public ResponseEntity<Void> activateNotification(@PathVariable Long notificationId) {
        try {
            notificationService.activateNotification(notificationId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{notificationId}/deactivate")
    public ResponseEntity<Void> deactivateNotification(@PathVariable Long notificationId) {
        try {
            notificationService.deactivateNotification(notificationId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
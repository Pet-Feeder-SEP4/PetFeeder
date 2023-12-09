package com.example.petfeedercloud.repositories;

import com.example.petfeedercloud.models.Notification;
import com.example.petfeedercloud.models.PetFeederHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

}

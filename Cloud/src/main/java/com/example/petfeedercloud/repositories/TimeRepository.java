package com.example.petfeedercloud.repositories;

import com.example.petfeedercloud.models.Schedule;
import com.example.petfeedercloud.models.Time;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TimeRepository  extends JpaRepository<Time, Long> {

    @Query("SELECT p FROM Time p WHERE p.schedule.scheduleId = :scheduleId")
    List<Time> findByScheduleId(Long scheduleId);
}

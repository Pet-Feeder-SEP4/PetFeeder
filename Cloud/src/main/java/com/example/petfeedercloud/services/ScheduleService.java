package com.example.petfeedercloud.services;



import com.example.petfeedercloud.dtos.ScheduleDTO;
import com.example.petfeedercloud.models.Schedule;

import java.util.List;

public interface ScheduleService {
    List<Schedule> getScheduleByPetFeederId(Long petFeederId);
    Schedule activateSchedule(Long scheduleId);
    ScheduleDTO getScheduleById(Long scheduleId);
    List<Schedule> getScheduleByUserId(Long userId);
    void deleteSchedule(Long scheduleId);
    Schedule createSchedule(ScheduleDTO scheduleDTO);
    ScheduleDTO updateSchedule(Long scheduleId, ScheduleDTO scheduleDTO);
}
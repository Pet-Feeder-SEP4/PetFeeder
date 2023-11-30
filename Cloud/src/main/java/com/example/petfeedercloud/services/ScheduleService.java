package com.example.petfeedercloud.services;

import com.example.petfeedercloud.dtos.PetDTO;
import com.example.petfeedercloud.dtos.ScheduleDTO;

import java.util.List;

public interface ScheduleService {
    List<ScheduleDTO> getScheduleByPetFeederId(Long petFeederId);
    ScheduleDTO getScheduleById(Long scheduleId);
    List<ScheduleDTO> getScheduleByUserId(Long userId);
    void deleteSchedule(Long scheduleId);
    void createSchedule(ScheduleDTO scheduleDTO);
    ScheduleDTO updateSchedule(Long scheduleId, ScheduleDTO scheduleDTO);
}
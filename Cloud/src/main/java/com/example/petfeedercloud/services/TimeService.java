package com.example.petfeedercloud.services;

import com.example.petfeedercloud.dtos.ScheduleDTO;
import com.example.petfeedercloud.dtos.TimeDTO;

import java.util.List;

public interface TimeService {
    List<TimeDTO> getTimeByScheduleId(Long scheduleId);
    TimeDTO getTimeById(Long timeId);
    void deleteTime(Long timeId);
    void createTime(TimeDTO timeDTO);
    TimeDTO updateTime(Long timeId, TimeDTO timeDTO);
}

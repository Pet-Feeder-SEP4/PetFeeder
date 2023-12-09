package com.example.petfeedercloud.services;

import com.example.petfeedercloud.dtos.GetDTOs.GetTimeDTO;
import com.example.petfeedercloud.dtos.ScheduleDTO;
import com.example.petfeedercloud.dtos.TimeDTO;
import com.example.petfeedercloud.models.Time;

import java.util.List;

public interface TimeService {
    List<GetTimeDTO> getTimeByScheduleId(Long scheduleId);
    TimeDTO getTimeById(Long timeId);
    void deleteTime(Long timeId);
    GetTimeDTO createTime(TimeDTO timeDTO);
    TimeDTO updateTime(Long timeId, TimeDTO timeDTO);

    void createTimes(List<TimeDTO> timeDTOList);
}

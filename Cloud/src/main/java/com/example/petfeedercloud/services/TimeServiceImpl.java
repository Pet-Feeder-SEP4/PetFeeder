package com.example.petfeedercloud.services;

import com.example.petfeedercloud.dtos.GetDTOs.GetTimeDTO;
import com.example.petfeedercloud.dtos.TimeDTO;

import com.example.petfeedercloud.models.Schedule;
import com.example.petfeedercloud.models.Time;
import com.example.petfeedercloud.repositories.ScheduleRepository;
import com.example.petfeedercloud.repositories.TimeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimeServiceImpl implements TimeService{
    private final ScheduleRepository scheduleRepository;
    private final TimeRepository timeRepository;
    @Override
    public List<GetTimeDTO> getTimeByScheduleId(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException("Schedule with id " + scheduleId + " not found"));
        return timeRepository.findByScheduleId(schedule.getScheduleId()).stream()
                .map(this::mapToDTOTime)
                .collect(Collectors.toList());
    }

    @Override
    public TimeDTO getTimeById(Long timeId) {
        Time time = timeRepository.findById(timeId)
                .orElseThrow(() -> new EntityNotFoundException("Time with id " + timeId + " not found"));
        return mapToDTO(time);
    }

    @Override
    public void deleteTime(Long timeId) {
        timeRepository.deleteById(timeId);
    }
    @Override
    public GetTimeDTO createTime(TimeDTO timeDTO) {
        Schedule schedule = scheduleRepository.findById(timeDTO.getScheduleId())
                .orElseThrow(() -> new EntityNotFoundException("Schedule with id " + timeDTO.getScheduleId() + " not found"));

        Time time = new Time();
        time.setSchedule(schedule);
        if(time.getPortionSize()>0 ||time.getPortionSize()<1000 )
            time.setPortionSize(timeDTO.getPortionSize());
        time.setTimeLabel(timeDTO.getTimeLabel());
        time.setTime(timeDTO.getTime());

        // Save the time entity and convert to GetTimeDTO
        Time savedTime = timeRepository.save(time);
        return convertToGetTimeDTO(savedTime);
    }

    @Override
    public TimeDTO updateTime(Long timeId, TimeDTO timeDTO) {
        Time existingTime = timeRepository.findById(timeId)
                .orElseThrow(() -> new EntityNotFoundException("Time with id " + timeId + " not found"));
        Schedule schedule = scheduleRepository.findById(timeDTO.getScheduleId())
                .orElseThrow(() -> new EntityNotFoundException("Schedule with id " + timeDTO.getScheduleId() + " not found"));
        existingTime.setSchedule(schedule);
        if(timeDTO.getPortionSize()>0 ||timeDTO.getPortionSize()<1000 )
            existingTime.setPortionSize(timeDTO.getPortionSize());
        existingTime.setTimeLabel(timeDTO.getTimeLabel());
        existingTime.setTime(timeDTO.getTime());
        timeRepository.save(existingTime);
        return mapToDTO(existingTime);
    }

    public void createTimes(List<TimeDTO> timeDTOList) {
        // Your logic for creating multiple times
        List<Time> times = new ArrayList<>();

        for (TimeDTO timeDTO : timeDTOList) {
            // Convert TimeDTO to Time entity or use a constructor
            Time time = new Time();
            time.setSchedule(scheduleRepository.getById(timeDTO.getScheduleId()));
            time.setTimeLabel(timeDTO.getTimeLabel());
            if(timeDTO.getPortionSize()>0 ||timeDTO.getPortionSize()<1000 )
                time.setPortionSize(timeDTO.getPortionSize());
            time.setTime(timeDTO.getTime());
            times.add(time);
        }

        // Save the list of times
        timeRepository.saveAll(times);
    }

    //helpers

    private GetTimeDTO mapToDTOTime(Time time) {
        GetTimeDTO timeDTO = new GetTimeDTO();
        timeDTO.setScheduleId(time.getSchedule().getScheduleId());
        timeDTO.setTimeLabel(time.getTimeLabel());
        timeDTO.setTime(time.getTime());
        timeDTO.setTimeId(time.getTimeId());
        if (time.getPortionSize() > 0 && time.getPortionSize() < 1000)
            timeDTO.setPortionSize(time.getPortionSize());

        return timeDTO;
    }
    private TimeDTO mapToDTO(Time time) {
        TimeDTO timeDTO = new TimeDTO();
        timeDTO.setScheduleId(time.getSchedule().getScheduleId());
        timeDTO.setTimeLabel(time.getTimeLabel());
        timeDTO.setTime(time.getTime());
        if (time.getPortionSize() > 0 && time.getPortionSize() < 1000)
            timeDTO.setPortionSize(time.getPortionSize());

        return timeDTO;
    }
    private GetTimeDTO convertToGetTimeDTO(Time time) {
        return new GetTimeDTO(
                time.getTimeId(),
                time.getPortionSize(),
                time.getSchedule().getScheduleId(),
                time.getTimeLabel(),
                time.getTime()
        );
    }
}


package com.example.petfeedercloud.controllers;


import com.example.petfeedercloud.dtos.ScheduleDTO;
import com.example.petfeedercloud.dtos.TimeDTO;
import com.example.petfeedercloud.services.ScheduleService;
import com.example.petfeedercloud.services.TimeService;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping(value = "/time")
@RequiredArgsConstructor
public class TimeController {
    private final TimeService timeService;
    private final ScheduleService scheduleService;

    @GetMapping("/{timeId}")
    public ResponseEntity<?> getTimeById(@PathVariable Long timeId) {
        try {
            TimeDTO time = timeService.getTimeById(timeId);
            return ResponseEntity.ok(time);
        } catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred while fetching the time. "+ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<?> getTimeByScheduleId(@PathVariable Long scheduleId) {
        try {
            ScheduleDTO scheduleDTO = scheduleService.getScheduleById(scheduleId);
            if (scheduleDTO == null) {
                return new ResponseEntity<>("Schedule with id " + scheduleId + " does not exist.", HttpStatus.NOT_FOUND);
            }
            List<TimeDTO> times = timeService.getTimeByScheduleId(scheduleId);
            return ResponseEntity.ok(times);
        } catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred while fetching times. "+ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PostMapping("/{scheduleId}")
    public ResponseEntity<?> createTime( @RequestBody TimeDTO timeDTO) {
        try {
            // Validate the input
            validateCreateTimeDTO(timeDTO);
            timeService.createTime(timeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException | ConstraintViolationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred while creating the time. "+ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{timeId}")
    public ResponseEntity<?> updateTime(@PathVariable Long timeId, @RequestBody TimeDTO timeDTO) {
        try {
            validateCreateTimeDTO(timeDTO);
            TimeDTO updatedTime = timeService.updateTime(timeId, timeDTO);
            return ResponseEntity.ok(updatedTime);
        } catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred while updating the time. "+ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{timeId}")
    public ResponseEntity<?> deleteTime(@PathVariable Long timeId) {
        try {
            timeService.deleteTime(timeId);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred while deleting the time. "+ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void validateCreateTimeDTO(TimeDTO createTimeDTO) {
        if (createTimeDTO.getTimeLabel() == null || createTimeDTO.getTimeLabel().isEmpty()) {
            throw new IllegalArgumentException("Please fill out the time label.");
        }
        if (createTimeDTO.getScheduleId() == null) {
            throw new IllegalArgumentException("Schedule ID is required.");
        }
        //check if the time is in "HH:mm:ss" format
        try {
            LocalTime parsedTime = LocalTime.parse(createTimeDTO.getTime());
            // The time is in a valid format
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Invalid time format. Please use the format 'HH:mm:ss'.");
        }
        try {
            ScheduleDTO schedule = scheduleService.getScheduleById(createTimeDTO.getScheduleId());
            if (schedule == null) {
                throw new NotFoundException("Schedule not found with ID: " + createTimeDTO.getScheduleId());
            }
        } catch (NotFoundException ex) {
            throw new NotFoundException("Schedule not found with ID: " + createTimeDTO.getScheduleId());
        }

    }
}
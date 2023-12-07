package com.example.petfeedercloud.controllers;


import com.example.petfeedercloud.dtos.GetDTOs.GetTimeDTO;
import com.example.petfeedercloud.dtos.ScheduleDTO;
import com.example.petfeedercloud.dtos.TimeDTO;
import com.example.petfeedercloud.models.Time;
import com.example.petfeedercloud.services.ScheduleService;
import com.example.petfeedercloud.services.TimeService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Get time by id", description = "Returns the time by id")
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
    @Operation(summary = "Get time by schedule id", description = "Returns a list of time that are in the schedule")
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



    @Operation(summary = "Creates one time for a schedule", description = "Returns the time created")
    @PostMapping("/{timeId}")
    public ResponseEntity<?> createTime(@RequestBody TimeDTO timeDTO) {
        try {
            // Validate the input
            validateCreateTimeDTO(timeDTO);

            // Create the time and get the created GetTimeDTO
            GetTimeDTO createdTime = timeService.createTime(timeDTO);

            // Return the created GetTimeDTO in the response body
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTime);
        } catch (IllegalArgumentException | ConstraintViolationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred while creating the time. " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "Add times in bulk to a petfeeder", description = "Returns the times created")
    @PostMapping("/bulk")
    public ResponseEntity<?> createTimes(@RequestBody List<TimeDTO> timeDTOList) {
        try {
            for (TimeDTO timeDTO : timeDTOList) {
                validateCreateTimeDTO(timeDTO);
            }
            timeService.createTimes(timeDTOList);
            return new ResponseEntity<>(timeDTOList,HttpStatus.CREATED);
        } catch (IllegalArgumentException | ConstraintViolationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred while creating the times. " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "Update the time", description = "Returns the time updated")
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
    @Operation(summary = "Delete the time", description = "No return")
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
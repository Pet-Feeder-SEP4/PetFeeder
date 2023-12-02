package com.example.petfeedercloud.controllers;

import com.example.petfeedercloud.dtos.GetDTOs.GetScheduleDTO;
import com.example.petfeedercloud.dtos.ScheduleDTO;
import com.example.petfeedercloud.models.PetFeeder;
import com.example.petfeedercloud.models.Schedule;
import com.example.petfeedercloud.services.PetFeederService;
import com.example.petfeedercloud.services.ScheduleService;
import com.example.petfeedercloud.services.UserService;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final PetFeederService petFeederService;
    private final UserService userService;
    @GetMapping("/petFeeder/{petFeederId}")
    public ResponseEntity<?> getScheduleByPetFeederId(@PathVariable Long petFeederId) {
        if (petFeederId == null) {
            return new ResponseEntity<>("Pet Feeder ID is required", HttpStatus.BAD_REQUEST);
        }
        try {
            PetFeeder petFeeder = petFeederService.getPetFeederById(petFeederId);
            List<Schedule> schedules = scheduleService.getScheduleByPetFeederId(petFeederId);

            // Create a list of simplified DTOs
            List<GetScheduleDTO> simplifiedSchedules = new ArrayList<>();
            for (Schedule schedule : schedules) {
                simplifiedSchedules.add(new GetScheduleDTO(schedule.getScheduleId(), schedule.getScheduleLabel(), schedule.getUser().getUserId(),petFeederId));
            }

            return new ResponseEntity<>(simplifiedSchedules, HttpStatus.OK);
        } catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<?> getScheduleById(@PathVariable Long scheduleId) {
        if (scheduleId == null) {
            return new ResponseEntity<>("Schedule Id is required", HttpStatus.BAD_REQUEST);
        }
        try {
            ScheduleDTO schedule = scheduleService.getScheduleById(scheduleId);
            GetScheduleDTO simplifiedSchedule = new GetScheduleDTO(scheduleId, schedule.getScheduleLabel(), schedule.getUserId(),schedule.getPetFeederId());
            return new ResponseEntity<>(simplifiedSchedule, HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getScheduleByUserId(@PathVariable Long userId) {
        if (userId == null) {
            return new ResponseEntity<>("User ID is required", HttpStatus.BAD_REQUEST);
        }
        try {
            // Check if the user with the given ID exists
            if (userService.getUserById(userId) == null) {
                return new ResponseEntity<>("User not found with ID: " + userId, HttpStatus.NOT_FOUND);
            }

            List<Schedule> schedules = scheduleService.getScheduleByUserId(userId);

            // Create a list of simplified DTOs
            List<GetScheduleDTO> simplifiedSchedules = new ArrayList<>();
            for (Schedule schedule : schedules) {
                simplifiedSchedules.add(new GetScheduleDTO(schedule.getScheduleId(), schedule.getScheduleLabel(),schedule.getUser().getUserId(),schedule.getPetFeeder().getPetFeederId()));
            }

            return new ResponseEntity<>(simplifiedSchedules, HttpStatus.OK);
        } catch (RuntimeException ex) {
            // Handle the case where an unexpected error occurs
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //this is commented because if we delete the schedule we need to  delete the times
    // and if we delete the times we need to delete the portions
    //none of these are done
    /*
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    */
    @PostMapping
    public ResponseEntity<?> createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        try {
            validateScheduleDTO(scheduleDTO);
            Schedule createdSchedule = scheduleService.createSchedule(scheduleDTO);

            // Create a simplified DTO for the response
            GetScheduleDTO responseDTO = new GetScheduleDTO(
                    createdSchedule.getScheduleId(),
                    createdSchedule.getScheduleLabel(),
                    createdSchedule.getUser().getUserId(),
                    createdSchedule.getPetFeeder().getPetFeederId()
            );

            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (IllegalArgumentException | ConstraintViolationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred while creating the schedule.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{scheduleId}")
    public ResponseEntity<?> updateSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleDTO scheduleDTO) {
        try {
            if (scheduleService.getScheduleById(scheduleId) == null) {
                return new ResponseEntity<>("Schedule not found with ID: " + scheduleId, HttpStatus.NOT_FOUND);
            }

            ScheduleDTO updatedSchedule = scheduleService.updateSchedule(scheduleId, scheduleDTO);

            if (updatedSchedule != null) {
                return new ResponseEntity<>(updatedSchedule, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Schedule not found with ID: " + scheduleId, HttpStatus.NOT_FOUND);
            }
        } catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred while updating the schedule.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void validateScheduleDTO(ScheduleDTO scheduleDTO) {
        if (scheduleDTO.getScheduleLabel() == null || scheduleDTO.getScheduleLabel().isEmpty()) {
            throw new IllegalArgumentException("Please fill out the schedule label.");
        }
        if (scheduleDTO.getPetFeederId() == null) {
            throw new IllegalArgumentException("Please fill out the pet feeder ID.");
        }
        if (petFeederService.getPetFeederById(scheduleDTO.getPetFeederId())==null) {
            throw new IllegalArgumentException("Pet feeder not found with ID: " + scheduleDTO.getPetFeederId());
        }
        Long userId = scheduleDTO.getUserId();
        if (userId == null) {
            throw new IllegalArgumentException("Please fill out the user ID.");
        }
        if (userService.getUserById(userId)==null) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
    }
}
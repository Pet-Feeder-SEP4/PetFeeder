package com.example.petfeedercloud.controllers;

import com.example.petfeedercloud.dtos.GetDTOs.GetScheduleDTO;
import com.example.petfeedercloud.dtos.ScheduleDTO;
import com.example.petfeedercloud.models.PetFeeder;
import com.example.petfeedercloud.models.Schedule;
import com.example.petfeedercloud.services.PetFeederService;
import com.example.petfeedercloud.services.ScheduleService;
import com.example.petfeedercloud.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Get schedule by Pet Feeder", description = "Returns all the schedules that are in the pet feeder")
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
                simplifiedSchedules.add(new GetScheduleDTO(schedule.getScheduleId(), schedule.getScheduleLabel(), schedule.getUser().getUserId(),petFeederId,schedule.getActive()));
            }

            return new ResponseEntity<>(simplifiedSchedules, HttpStatus.OK);
        } catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Get schedule by id", description = "Returns the schedule")
    @GetMapping("/{scheduleId}")
    public ResponseEntity<?> getScheduleById(@PathVariable Long scheduleId) {
        if (scheduleId == null) {
            return new ResponseEntity<>("Schedule Id is required", HttpStatus.BAD_REQUEST);
        }
        try {
            ScheduleDTO schedule = scheduleService.getScheduleById(scheduleId);
            GetScheduleDTO simplifiedSchedule = new GetScheduleDTO(scheduleId, schedule.getScheduleLabel(), schedule.getUserId(),schedule.getPetFeederId(),schedule.getActive());
            return new ResponseEntity<>(simplifiedSchedule, HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Get schedule by  user id", description = "Returns all schedules from an user")
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
                simplifiedSchedules.add(new GetScheduleDTO(schedule.getScheduleId(), schedule.getScheduleLabel(),schedule.getUser().getUserId(),schedule.getPetFeeder().getPetFeederId(),schedule.getActive()));
            }

            return new ResponseEntity<>(simplifiedSchedules, HttpStatus.OK);
        } catch (RuntimeException ex) {
            // Handle the case where an unexpected error occurs
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Long scheduleId) {
        try {
            ScheduleDTO existingSchedule = scheduleService.getScheduleById(scheduleId);
            if (existingSchedule == null) {
                return new ResponseEntity<>("Schedule with id " + scheduleId + " not found.", HttpStatus.NOT_FOUND);
            }
            scheduleService.deleteSchedule(scheduleId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Schedule with id "+scheduleId+" deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting schedule");
        }
    }


    @Operation(summary = "Create schedulee", description = "Creates the schedule and return it")
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
                    createdSchedule.getPetFeeder().getPetFeederId(),
                    createdSchedule.getActive()
            );

            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (IllegalArgumentException | ConstraintViolationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred while creating the schedule.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "Activate schedule", description = "This will active the schedule inserted and deactivate all other schedules related to this PetFeeder")
    @PutMapping("/activate/{scheduleId}")
    public ResponseEntity<?> activateSchedule(@PathVariable Long scheduleId) {
        try {
            Schedule activatedSchedule = scheduleService.activateSchedule(scheduleId);
            GetScheduleDTO responseDTO = new GetScheduleDTO(
                    activatedSchedule.getScheduleId(),
                    activatedSchedule.getScheduleLabel(),
                    activatedSchedule.getUser().getUserId(),
                    activatedSchedule.getPetFeeder().getPetFeederId(),
                    activatedSchedule.getActive()
            );
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred while activating the schedule.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "Get schedule by id", description = "Returns the schedule")
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
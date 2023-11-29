package com.example.petfeedercloud.controllers;

import com.example.petfeedercloud.dtos.ScheduleDTO;
import com.example.petfeedercloud.services.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules") // Replace with your base path
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/petFeeder/{petFeederId}")
    public ResponseEntity<List<ScheduleDTO>> getScheduleByPetFeederId(@PathVariable Long petFeederId) {
        List<ScheduleDTO> schedules = scheduleService.getScheduleByPetFeederId(petFeederId);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleDTO> getScheduleById(@PathVariable Long scheduleId) {
        ScheduleDTO schedule = scheduleService.getScheduleById(scheduleId);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ScheduleDTO>> getScheduleByUserId(@PathVariable Long userId) {
        List<ScheduleDTO> schedules = scheduleService.getScheduleByUserId(userId);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
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
    public ResponseEntity<Void> createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        scheduleService.createSchedule(scheduleDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleDTO> updateSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleDTO scheduleDTO) {
        ScheduleDTO updatedSchedule = scheduleService.updateSchedule(scheduleId, scheduleDTO);
        return new ResponseEntity<>(updatedSchedule, HttpStatus.OK);
    }
}
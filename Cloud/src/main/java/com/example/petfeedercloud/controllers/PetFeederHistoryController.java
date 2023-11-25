package com.example.petfeedercloud.controllers;

import com.example.petfeedercloud.dtos.GetDTOs.GetDateIntervalDTO;
import com.example.petfeedercloud.dtos.GetDTOs.GetHistoryDTO;
import com.example.petfeedercloud.dtos.PetFeederHistoryDTO;
import com.example.petfeedercloud.services.PetFeederHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(value = "/petFeederHistory")
public class PetFeederHistoryController {
    @Autowired
    private PetFeederHistoryService petFeederService;

    @Operation(summary = "Get Pet feeder history by date interval", description = "This will return the history of the petfeeder stats for the selected date Interval")
    @PostMapping("/getHistoryByDateInterval")
    public ResponseEntity<?> getHistoryByDateInterval(@RequestBody GetDateIntervalDTO dateIntervalDTO){
        try {
            if (dateIntervalDTO.getStartDate() == null || !isValidDate(dateIntervalDTO.getStartDate())
                    || dateIntervalDTO.getEndDate() == null || !isValidDate(dateIntervalDTO.getEndDate())) {
                throw new Exception("Invalid dates!Dates should be in the format 'yyyy-MM-dd'");
            }
            if (dateIntervalDTO.getStartDate().isAfter(dateIntervalDTO.getEndDate())) {
                throw new Exception("Start date cannot be after end date");
            }
            List<PetFeederHistoryDTO> historyList = petFeederService.getHistoryByDateInterval(dateIntervalDTO);
            if (historyList == null|| historyList.isEmpty()) {
                throw new Exception("History list is empty! Please try other dates or petFeeder id!");
            }
            return new ResponseEntity<>(historyList, HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = "Failed to save PetFeederHistory. " + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get Pet feeder history by date", description = "This will return the history of the petfeeder stats for the selected date")
    @PostMapping("/history")
    public ResponseEntity<?> getPetFeederHistory(@RequestBody GetHistoryDTO historyDTO) {
        try {
            if (historyDTO.getDate() == null || !isValidDate(historyDTO.getDate())) {
                throw new Exception("Invalid date!Dates should be in the format 'yyyy-MM-dd'");
            }
            List<PetFeederHistoryDTO> history = petFeederService.getHistoryByDate(historyDTO.getPetFeederId(),historyDTO.getDate());
            if (history == null || history.isEmpty()) {
                throw new Exception("History list is empty! Please try another date or petFeeder id!");
            }
            return new ResponseEntity<>(history, HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = "Failed to save PetFeederHistory. " + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private boolean isValidDate(LocalDate date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            String formattedDate = date.format(dateFormatter);
            LocalDate parsedDate = LocalDate.parse(formattedDate, dateFormatter);
            return parsedDate.equals(date);
        } catch (Exception e) {
            return false;
        }
    }
}

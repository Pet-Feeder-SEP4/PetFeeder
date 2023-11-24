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
            List<PetFeederHistoryDTO> historyList = petFeederService.getHistoryByDateInterval(dateIntervalDTO);
            // Return the result as ResponseEntity
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
            List<PetFeederHistoryDTO> history = petFeederService.getHistoryByDate(historyDTO.getPetFeederId(),historyDTO.getDate());
            return new ResponseEntity<>(history, HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = "Failed to save PetFeederHistory. " + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

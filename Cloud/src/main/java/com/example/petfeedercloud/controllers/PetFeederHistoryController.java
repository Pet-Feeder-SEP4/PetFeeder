package com.example.petfeedercloud.controllers;

import com.example.petfeedercloud.dtos.PetFeederDTO;
import com.example.petfeedercloud.dtos.UserDTO;
import com.example.petfeedercloud.models.PetFeederHistory;
import com.example.petfeedercloud.services.PetFeederHistoryService;
import com.example.petfeedercloud.services.PetFeederService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.List;

@RestController
@RequestMapping(value = "/petFeederHistory")
public class PetFeederHistoryController {
    @Autowired
    private PetFeederHistoryService petFeederService;



    @PostMapping("/history")
    public ResponseEntity<?> getAllPetsByUser(@RequestBody PetFeederDTO petfeederId) {
        try {
            PetFeederHistory savedHistory = petFeederService.savePetFeederHistory(petfeederId);
            return new ResponseEntity<>(savedHistory, HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = "Failed to save PetFeederHistory. " + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

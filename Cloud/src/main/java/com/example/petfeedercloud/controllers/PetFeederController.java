package com.example.petfeedercloud.controllers;

import com.example.petfeedercloud.PetFeederCloudApplication;
import com.example.petfeedercloud.config.WebSocketHandler;
import com.example.petfeedercloud.dtos.PetFeederDTO;
import com.example.petfeedercloud.services.PetFeederService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.List;

@RestController
@RequestMapping(value = "/petfeeder")
public class PetFeederController {

    @Autowired
    private PetFeederService petFeederService;

    @Autowired
    private final PetFeederCloudApplication petFeederCloudApplication;

    public PetFeederController(PetFeederCloudApplication petFeederCloudApplication) {
        this.petFeederCloudApplication = petFeederCloudApplication;
    }

    @GetMapping("/")
    @Operation(summary = "Get all pet feeders", description = "Get all pet feeders in the database")
    public List<PetFeederDTO> getAllPetFeeders() {
        return petFeederService.getAllPetFeeders();
    }

    @PostMapping("/")
    @Operation(summary = "Create pet feeder", description = "Create new pet feeder using DTO")
    public ResponseEntity<String> createPetFeeder(@RequestBody PetFeederDTO petFeederDTO) {
        try {
            petFeederService.createPetFeeder(petFeederDTO);
            return ResponseEntity.ok("PetFeeder created successfully");
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
        }
    }

    @PutMapping("/{petfeederId}")
    @Operation(summary = "Update existing pet feeder", description = "Update existing pet by inserting its pet feeder id")
    public ResponseEntity<String> updatePet(@PathVariable Long petfeederId, @RequestBody PetFeederDTO petFeederDTO) {
        try {
            petFeederDTO.setPetId(petfeederId);
            petFeederService.saveOrUpdatePetFeeder(petFeederDTO);
            return ResponseEntity.ok("Pet feeder updated successfully");
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet feeder not found: " + ex.getMessage());
        } catch (IllegalArgumentException | ConstraintViolationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the petfeeder");
        }
    }
    @DeleteMapping("/{petfeederId}")
    @Operation(summary = "Delete pet feeder", description = "Delete pet Feeder by its id")
    public ResponseEntity<?> deletePet(@PathVariable Long petfeederId) {
        try {
            // Check if the pet feeder exists before attempting deletion
            if (petFeederService.getPetFeederById(petfeederId)!=null) {
                petFeederService.deletePetFeeder(petfeederId);
                return ResponseEntity.ok("Petfeeder deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Petfeeder not found");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the petfeeder");
        }
    }
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all pet feeders by an user", description = "Get all the pets from a user using user id")
    public List<PetFeederDTO> getAllPetsByUser(@PathVariable Long userId) {
        return petFeederService.getAllPetFeedersByUser(userId);
    }

    @GetMapping("/connected/{userId}")
    @Operation(summary = "Get all connected pet feeders by user", description = "Get all connected pet feeders from a user using user id")
    public List<PetFeederDTO> getAllActivePetFeedersByUser(@PathVariable Long userId) {
        return petFeederService.getAllConnectedPetFeedersByUser(userId);
    }

    @PostMapping("/{petfeederId}/connected")
    @Operation(summary = "Activate pet feeder", description = "Activate a pet feeder for a specific user and pet")
    public ResponseEntity<String> activatePetFeeder(
            @RequestParam(required = true) Long userId,
            @RequestParam Long petFeederId) {
        try {
            if (userId == null || userId <= 0) {
                throw new IllegalArgumentException("userId and petId are required and must be greater than 0");
            }

            petFeederService.setActivePetFeeder(userId, petFeederId);
            return ResponseEntity.ok("Pet feeder activated successfully");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PostMapping("/{petfeederId}/deactivate")
    @Operation(summary = "Deactivate pet feeder", description = "Deactivate a pet feeder for a specific user and pet")
    public ResponseEntity<String> deactivatePetFeeder(
            @RequestParam(required = true) Long userId,
            @RequestParam Long petFeederId) {
        try {
            if (userId == null || userId <= 0) {
                throw new IllegalArgumentException("userId and petId are required and must be greater than 0");
            }

            petFeederService.deactivatePetFeeder(userId, petFeederId);
            return ResponseEntity.ok("Pet feeder deactivated successfully");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PostMapping("/{petfeederId}/addPet/{petId}")
    @Operation(summary = "Add pet to pet feeder", description = "Add a pet to a specific pet feeder")
    public ResponseEntity<String> addPetToPetFeeder(@PathVariable Long petfeederId, @PathVariable Long petId) {
        try {
            petFeederService.addPetToPetFeeder(petfeederId, petId);
            return ResponseEntity.ok("Pet added to pet feeder successfully");
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
        }
    }
    @PostMapping("/sendPortion/{petFeederId}/{portion}")
    @Operation(summary = "Send portion to petfeeder", description = "Send portion(ggg) to a specific pet feeder")
    public ResponseEntity<String> sendPortionToPetFeeder(@PathVariable Long petFeederId, @PathVariable String portion){
        try{
            if(petFeederId == null || portion == null || portion.equals(""))
                throw new IllegalArgumentException("PetFeeder and Portion are required");
            petFeederCloudApplication.sendPortionToPetFeeder(petFeederId, portion);
            return ResponseEntity.ok("Portion "+portion+" sent to petfeeder:"+petFeederId);
        }catch (IllegalArgumentException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
}

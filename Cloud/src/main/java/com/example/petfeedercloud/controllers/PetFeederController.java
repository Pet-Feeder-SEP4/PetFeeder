package com.example.petfeedercloud.controllers;

import com.example.petfeedercloud.dtos.PetDTO;
import com.example.petfeedercloud.dtos.PetFeederDTO;
import com.example.petfeedercloud.services.PetFeederService;
import com.example.petfeedercloud.services.PetService;
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

    @GetMapping("/")
    public List<PetFeederDTO> getAllPetFeeders() {
        return petFeederService.getAllPetFeeders();
    }

    @PostMapping("/")
    public ResponseEntity<String> savePet(@RequestBody PetFeederDTO petFeederDTO) {
        try {
            petFeederService.saveOrUpdatePetFeeder(petFeederDTO);
            return ResponseEntity.ok("PetFeeder created successfully");
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + ex.getMessage());
        } catch (ConstraintViolationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PutMapping("/{petfeederId}")
    public ResponseEntity<String> updatePet(@PathVariable Long petfeederId, @RequestBody PetFeederDTO petFeederDTO) {
        try {
            petFeederDTO.setPetId(petfeederId);
            petFeederService.saveOrUpdatePetFeeder(petFeederDTO);
            return ResponseEntity.ok("Pet updated successfully");
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Petfeeder not found: " + ex.getMessage());
        } catch (IllegalArgumentException | ConstraintViolationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the petfeeder");
        }
    }
    @DeleteMapping("/{petfeederId}")
    public ResponseEntity<?> deletePet(@PathVariable Long petfeederId) {
        try {
            petFeederService.deletePetFeeder(petfeederId);
            return ResponseEntity.ok("Petfeeder deleted successfully");
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the petfeeder");
        }
    }
    @GetMapping("/user/{petfeederId}")
    public List<PetFeederDTO> getAllPetsByUser(@PathVariable Long petfeederId) {
        return petFeederService.getAllPetFeedersByUser(petfeederId);
    }
}
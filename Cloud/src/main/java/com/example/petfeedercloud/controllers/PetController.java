package com.example.petfeedercloud.controllers;

import com.example.petfeedercloud.dtos.PetDTO;
import com.example.petfeedercloud.services.PetService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.List;

@RestController
@RequestMapping(value = "/pets")
public class PetController {
    @Autowired
    private PetService petService;

    @GetMapping("/")
    public List<PetDTO> getAllPets() {
        return petService.getAllPets();
    }

    @GetMapping("/{petId}")
    public PetDTO getPetById(@PathVariable Long petId) {
        return petService.getPetById(petId);
    }

    @PostMapping("/")
    public ResponseEntity<String> savePet(@RequestBody PetDTO petDTO) {
        try {
            petService.saveOrUpdatePet(petDTO);
            return ResponseEntity.ok("Pet created successfully");
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + ex.getMessage());
        } catch (ConstraintViolationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the pet");
        }
    }

    @PutMapping("/{petId}")
    public ResponseEntity<String> updatePet(@PathVariable Long petId, @RequestBody PetDTO petDTO) {
        try {
            petDTO.setPetId(petId);
            petService.saveOrUpdatePet(petDTO);
            return ResponseEntity.ok("Pet updated successfully");
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + ex.getMessage());
        } catch (ConstraintViolationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the pet");
        }
    }

    @DeleteMapping("/{petId}")
    public void deletePet(@PathVariable Long petId) {
        petService.deletePet(petId);
    }

    @GetMapping("/user/{userId}")
    public List<PetDTO> getAllPetsByUser(@PathVariable Long userId) {
        return petService.getAllPetsByUser(userId);
    }
}

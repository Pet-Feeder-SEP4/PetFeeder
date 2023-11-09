package com.example.petfeedercloud.controllers;

import com.example.petfeedercloud.dtos.PetDTO;
import com.example.petfeedercloud.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public void savePet(@RequestBody PetDTO petDTO) {
        petService.saveOrUpdatePet(petDTO);
    }

    @PutMapping("/{petId}")
    public void updatePet(@PathVariable Long petId, @RequestBody PetDTO petDTO) {
        petDTO.setPetId(petId);
        petService.saveOrUpdatePet(petDTO);
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

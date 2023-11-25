package com.example.petfeedercloud.services;

import com.example.petfeedercloud.dtos.PetDTO;
import com.example.petfeedercloud.models.Pet;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface PetService {
    List<PetDTO> getAllPets();
    PetDTO getPetById(Long petId);
    void deletePet(Long petId);
    List<PetDTO> getAllPetsByUser(Long userId);


    PetDTO createPet(PetDTO petDTO);
    void updatePet(Long petId, PetDTO petDTO);
}

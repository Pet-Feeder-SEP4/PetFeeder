package com.example.petfeedercloud.services;

import com.example.petfeedercloud.dtos.PetDTO;

import java.util.List;

public interface PetService {
    List<PetDTO> getAllPets();
    PetDTO getPetById(Long petId);
    void saveOrUpdatePet(PetDTO petDTO);
    void deletePet(Long petId);
    List<PetDTO> getAllPetsByUser(Long userId);

}

package com.example.petfeedercloud.services;

import com.example.petfeedercloud.dtos.PetDTO;
import com.example.petfeedercloud.dtos.PetFeederDTO;
import com.example.petfeedercloud.models.PetFeeder;

import java.util.List;

public interface PetFeederService {
    List<PetFeederDTO> getAllPetFeeders();
    PetFeeder getPetFeederById(Long petFeederId);
    void saveOrUpdatePetFeeder(PetFeederDTO petFeeder);
    void deletePetFeeder(Long petFeederId);
    List<PetFeederDTO> getAllPetFeedersByUser(Long userId);
    void setActivePetFeeder(Long userId, Long petFeederId);
    void deactivatePetFeeder(Long userId, Long petFeederId);
    void createPetFeeder(PetFeederDTO petFeederDTO);
    List<PetFeederDTO> getAllConnectedPetFeedersByUser(Long userId);
    void addPetToPetFeeder(Long petFeederId, Long petId);
    PetDTO getPetForPetFeeder(Long petFeederId);
}

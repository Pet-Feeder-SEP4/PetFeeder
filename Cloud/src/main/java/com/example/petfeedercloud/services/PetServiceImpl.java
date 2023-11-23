package com.example.petfeedercloud.services;

import com.example.petfeedercloud.dtos.PetDTO;
import com.example.petfeedercloud.models.Pet;
import com.example.petfeedercloud.models.UserP;
import com.example.petfeedercloud.repositories.PetRepository;
import com.example.petfeedercloud.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {
    private static final Logger log = LoggerFactory.getLogger(PetServiceImpl.class);
    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserRepository userRepository;

    private ObjectMapper objectMapper;

    @Override
    public List<PetDTO> getAllPets() {
        return petRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PetDTO getPetById(Long petId) {
        return petRepository.findById(petId)
                .map(this::convertToDto)
                .orElseThrow(() -> new NotFoundException("Pet not found with ID: " + petId));
    }

    @Override
    public void saveOrUpdatePet(PetDTO petDTO) {
        try {
            if (petDTO.getName() == null || petDTO.getName().isEmpty()) {
                throw new IllegalArgumentException("Please fill out the pet name.");
            }

            Long petId = petDTO.getPetId();
            Optional<Pet> existingPetOptional = petRepository.findById(petId);

            if (existingPetOptional.isPresent()) {
                Pet existingPet = existingPetOptional.get();
                // Update the existing pet with new data
                existingPet.setName(petDTO.getName());
                existingPet.setBirthdate(petDTO.getBirthdate());
                existingPet.setWeight(petDTO.getWeight());
                existingPet.setBreed(petDTO.getBreed());

                Long userId = petDTO.getUserId();
                UserP user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
                existingPet.setUser(user);

                petRepository.save(existingPet); // Update the existing pet
            } else {
                petRepository.save(convertToEntity(petDTO));
            }
        } catch (IllegalArgumentException | ConstraintViolationException ex) {
            throw ex; // Let the controller handle these exceptions
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public void deletePet(Long petId) {
        Optional<Pet> petOptional = petRepository.findById(petId);
        if (petOptional.isPresent()) {
            petRepository.deleteById(petId);
        } else {
            throw new NotFoundException("Pet not found with ID: " + petId);
        }
    }
    @Override
    public List<PetDTO> getAllPetsByUser(Long userId) {
        return petRepository.findAllByUserUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    private PetDTO convertToDto(Pet pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setPetId(pet.getPetId());
        petDTO.setUserId(pet.getUser().getUserId());
        petDTO.setName(pet.getName());
        petDTO.setBirthdate(pet.getBirthdate());
        petDTO.setWeight(pet.getWeight());
        petDTO.setBreed(pet.getBreed());
        return petDTO;
    }

    private Pet convertToEntity(PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setPetId(petDTO.getPetId());
        pet.setName(petDTO.getName());
        pet.setBirthdate(petDTO.getBirthdate());
        pet.setWeight(petDTO.getWeight());
        pet.setBreed(petDTO.getBreed());
        return pet;
    }

    @Override
    public String convertPetToJson(PetDTO pet) throws JsonProcessingException {
        return objectMapper.writeValueAsString(pet);
    }
}

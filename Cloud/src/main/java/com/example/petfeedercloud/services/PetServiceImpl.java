package com.example.petfeedercloud.services;

import com.example.petfeedercloud.dtos.PetDTO;
import com.example.petfeedercloud.models.Pet;
import com.example.petfeedercloud.models.UserP;
import com.example.petfeedercloud.repositories.PetRepository;
import com.example.petfeedercloud.repositories.UserRepository;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {
    private static final Logger log = LoggerFactory.getLogger(PetServiceImpl.class);
    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserRepository userRepository;

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
                .orElse(null);
    }

    @Override
    public void saveOrUpdatePet(PetDTO petDTO) {
        try {
            if (petDTO.getName() == null || petDTO.getName().isEmpty()) {
                throw new IllegalArgumentException("Please fill out the pet name.");
            }

            Pet pet = convertToEntity(petDTO);
            Long userId = petDTO.getUserId();
            UserP user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
            pet.setUser(user);
            petRepository.save(pet);

            // No need to throw an Exception here
        } catch (IllegalArgumentException ex) {
            throw new ConstraintViolationException(ex.getMessage(), null);
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while creating the pet");
        }
    }

    @Override
    public void deletePet(Long petId) {
        petRepository.deleteById(petId);
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
}

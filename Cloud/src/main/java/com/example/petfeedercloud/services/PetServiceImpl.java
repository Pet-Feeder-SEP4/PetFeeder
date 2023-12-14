package com.example.petfeedercloud.services;

import com.example.petfeedercloud.dtos.PetDTO;
import com.example.petfeedercloud.jwt.serviceJWT.JwtServiceInterface;
import com.example.petfeedercloud.models.Pet;
import com.example.petfeedercloud.models.PetFeeder;
import com.example.petfeedercloud.models.Time;
import com.example.petfeedercloud.models.UserP;
import com.example.petfeedercloud.repositories.PetFeederRepository;
import com.example.petfeedercloud.repositories.PetRepository;
import com.example.petfeedercloud.repositories.TimeRepository;
import com.example.petfeedercloud.repositories.UserRepository;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private PetFeederRepository petFeederRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public List<PetDTO> getAllPets() {

        return petRepository.findAll().stream()
                .map(this::convertToDtoWithId)
                .collect(Collectors.toList());
    }

    @Override
    public PetDTO getPetById(Long petId) {
        return petRepository.findById(petId)
                .map(this::convertToDtoWithId)
                .orElseThrow(() -> new NotFoundException("Pet not found with ID: " + petId));
    }




    @Override
    public PetDTO createPet(PetDTO petDTO) {
        try {
            if (petDTO.getName() == null || petDTO.getName().isEmpty() || !petDTO.getName().matches(".*\\w.*")) {
                throw new IllegalArgumentException("Please provide a valid name for the pet. The name must contain at least one alphanumeric character.");
            }

            if (petDTO.getBirthdate() == null) {
                throw new IllegalArgumentException("Please provide the pet's birthdate.");
            }

            Pet pet = convertToEntity(petDTO);

            Long userId = petDTO.getUserId();
            UserP user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
            pet.setUser(user);

            petRepository.save(pet);
            return convertToDto(pet);
        } catch (IllegalArgumentException | ConstraintViolationException ex) {
            throw new IllegalArgumentException("An error occurred while creating the pet: " + ex.getMessage());
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while creating the pet: " + ex.getMessage());
        }
    }


    @Override
    public void updatePet(Long petId, PetDTO petDTO) {
        try {
            Optional<Pet> existingPetOptional = petRepository.findById(petId);

            if (existingPetOptional.isPresent()) {
                Pet existingPet = existingPetOptional.get();

                existingPet.setName(petDTO.getName());
                existingPet.setBirthdate(petDTO.getBirthdate());
                existingPet.setWeight(petDTO.getWeight());
                existingPet.setBreed(petDTO.getBreed());

                Long userId = petDTO.getUserId();
                UserP user = userRepository.findById(userId)
                        .orElseThrow(() -> new NotFoundException("User not found"));
                existingPet.setUser(user);

                petRepository.save(existingPet);
            } else {
                throw new NotFoundException("Pet not found with ID: " + petId);
            }
        } catch (NotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while updating the pet");
        }
    }



    @Override
    @Transactional
    public void deletePet(Long petId) {
        Optional<Pet> petOptional = petRepository.findById(petId);

        if (petOptional.isPresent()) {
            Pet pet = petOptional.get();

            // Disassociate the Pet from its associated PetFeeder entities
            List<PetFeeder> petFeeders = petFeederRepository.findByPet(pet);
            for (PetFeeder petFeeder : petFeeders) {
                petFeeder.setPet(null);
            }

            // Delete the Pet
            petRepository.deleteById(petId);
        } else {
            throw new NotFoundException("Pet not found with ID: " + petId);
        }
    }
    public List<PetDTO> getAllPetsByUser(Long userId) {
        List<Pet> pets = petRepository.findAllByUserUserId(userId);

        if (pets.isEmpty()) {
            throw new NotFoundException("No pets found for user ID: " + userId);
        }

        return pets.stream()
                .map(this::convertToDtoWithId)
                .collect(Collectors.toList());
    }


    private PetDTO convertToDto(Pet pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setUserId(pet.getUser().getUserId());
        petDTO.setName(pet.getName());
        petDTO.setBirthdate(pet.getBirthdate());
        petDTO.setWeight(pet.getWeight());
        petDTO.setBreed(pet.getBreed());
        return petDTO;
    }

    private PetDTO convertToDtoWithId(Pet pet) {
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
        pet.setName(petDTO.getName());
        pet.setBirthdate(petDTO.getBirthdate());
        pet.setWeight(petDTO.getWeight());
        pet.setBreed(petDTO.getBreed());
        return pet;
    }
}
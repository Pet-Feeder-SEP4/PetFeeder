package com.example.petfeedercloud.services;

import com.example.petfeedercloud.dtos.PetDTO;
import com.example.petfeedercloud.models.Pet;
import com.example.petfeedercloud.models.UserP;
import com.example.petfeedercloud.repositories.PetRepository;
import com.example.petfeedercloud.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {

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
        Pet pet = convertToEntity(petDTO);
        Long userId = petDTO.getUserId();
        UserP user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(null));
        pet.setUser(user);
        petRepository.save(pet);
    }

    @Override
    public void deletePet(Long petId) {
        petRepository.deleteById(petId);
    }
    @Override
    public List<PetDTO> getAllPetsByUser(Long userId) {
        return petRepository.findAllByUserId(userId).stream()
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

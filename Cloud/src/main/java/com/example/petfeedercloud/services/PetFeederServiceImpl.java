package com.example.petfeedercloud.services;

import com.example.petfeedercloud.dtos.PetDTO;
import com.example.petfeedercloud.dtos.PetFeederDTO;
import com.example.petfeedercloud.models.Pet;
import com.example.petfeedercloud.models.PetFeeder;
import com.example.petfeedercloud.models.UserP;
import com.example.petfeedercloud.repositories.PetFeederRepository;
import com.example.petfeedercloud.repositories.PetRepository;
import com.example.petfeedercloud.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetFeederServiceImpl implements PetFeederService{

    @Autowired
    private PetFeederRepository petFeederRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;

    private ObjectMapper objectMapper;

    @Override
    public List<PetFeederDTO> getAllPetFeeders() {
        return petFeederRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PetFeeder getPetFeederById(Long petFeederId) {
        return petFeederRepository.findById(petFeederId).orElseThrow(() -> new NotFoundException("Pet not found with ID: " + petFeederId));
    }

    @Override
    public void saveOrUpdatePetFeeder(PetFeederDTO petFeeder) {
        try {
            Long id = petFeeder.getPetFeederId();
            Optional<PetFeeder> pf = petFeederRepository.findById(id);

            if (pf.isPresent()) {
                PetFeeder existingPF = pf.get();
                // Update the existing pet with new data
                existingPF.setPetFeederLabel(petFeeder.getPetFeederLabel());
                existingPF.setFoodHumidity(petFeeder.getFoodHumidity());
                existingPF.setFoodLevel(petFeeder.getFoodLevel());
                existingPF.setWaterTemperture(petFeeder.getWaterTemperture());
                existingPF.setLowLevelFood(petFeeder.getLowLevelFood());

                Long userId = petFeeder.getUserId();
                Long scheduleId = petFeeder.getScheduleId();
                Long petId = petFeeder.getPetId();

                UserP user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
                System.out.println(user.getUserId());
                Pet pet = petRepository.findById(petId).orElseThrow(() -> new NotFoundException("Pet not found"));
                System.out.println(pet.getPetId());
                //When schedule is implement
                //Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new NotFoundException("Schedule not found"));

                existingPF.setUser(user);
                existingPF.setPet(pet);
                //When schedule is implemented change *null* to *schedule*
                existingPF.setSchedule(null);

                petFeederRepository.save(existingPF); // Update the existing pet
            } else {
                PetFeeder petF = new PetFeeder();
                petF.setUser(userRepository.findById(petFeeder.getUserId()).get());
                petF.setPet(petRepository.findById(petFeeder.getPetId()).get());
                //When schedule is implemented change null
                petF.setSchedule(null);
                petF.setLowLevelFood(petFeeder.getLowLevelFood());
                petF.setFoodLevel(petFeeder.getFoodLevel());
                petF.setFoodHumidity(petFeeder.getFoodHumidity());
                petF.setWaterTemperture(petFeeder.getWaterTemperture());
                petF.setPetFeederLabel(petFeeder.getPetFeederLabel());
                petFeederRepository.save(petF);
            }
        } catch (IllegalArgumentException | ConstraintViolationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public void deletePetFeeder(Long petFeederId) {
        petFeederRepository.deleteById(petFeederId);
    }

    @Override
    public List<PetFeederDTO> getAllPetFeedersByUser(Long userId) {
        return petFeederRepository.findAllByUserUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private PetFeederDTO convertToDto(PetFeeder pf) {
        PetFeederDTO pfDTO = new PetFeederDTO();
        pfDTO.setPetFeederLabel(pf.getPetFeederLabel());
        pfDTO.setFoodHumidity(pf.getFoodHumidity());
        pfDTO.setFoodLevel(pf.getFoodLevel());
        pfDTO.setWaterTemperture(pf.getWaterTemperture());
        pfDTO.setLowLevelFood(pf.getLowLevelFood());
        pfDTO.setPetId(pf.getPet().getPetId());
        pfDTO.setScheduleId(pf.getSchedule().getScheduleId());
        pfDTO.setUserId(pf.getUser().getUserId());
        return pfDTO;
    }
}

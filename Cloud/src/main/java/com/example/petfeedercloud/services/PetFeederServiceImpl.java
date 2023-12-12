package com.example.petfeedercloud.services;

import com.example.petfeedercloud.dtos.PetDTO;
import com.example.petfeedercloud.dtos.PetFeederDTO;
import com.example.petfeedercloud.models.Notification;
import com.example.petfeedercloud.models.Pet;
import com.example.petfeedercloud.models.PetFeeder;
import com.example.petfeedercloud.models.UserP;
import com.example.petfeedercloud.repositories.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class PetFeederServiceImpl implements PetFeederService{

    @Autowired
    private PetFeederRepository petFeederRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private TimeRepository timeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PetFeederHistoryRepository petFeederHistoryRepository;
    @Autowired
    private PetRepository petRepository;

    @Override
    public List<PetFeederDTO> getAllPetFeeders() {
        return petFeederRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PetFeeder getPetFeederById(Long petFeederId) {
        return petFeederRepository.findById(petFeederId).orElseThrow(() -> new NotFoundException("Pet Feeder not found with ID: " + petFeederId));
    }

    @Override
    public void createPetFeeder(PetFeederDTO petFeederDTO) {
        try {
            PetFeeder petFeeder = convertToEntity(petFeederDTO);

            Notification notification = new Notification(50,70,25,40,petFeeder,true);
            petFeederRepository.save(petFeeder);
            notificationRepository.save(notification);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
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
                existingPF.setWaterLevel(petFeeder.getWaterLevel());

                Long userId = petFeeder.getUserId();
                //Long scheduleId = petFeeder.getScheduleId();
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
                //existingPF.setSchedule(null);

                petFeederRepository.save(existingPF); // Update the existing pet
            } else {
                PetFeeder petF = new PetFeeder();
                petF.setUser(userRepository.findById(petFeeder.getUserId()).get());
                petF.setPet(petRepository.findById(petFeeder.getPetId()).get());
                //When schedule is implemented change null
               // petF.setSchedule(null);
                petF.setWaterLevel(petFeeder.getWaterLevel());
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
    public void addPetToPetFeeder(Long petFeederId, Long petId) {
        try {
            PetFeeder petFeeder = petFeederRepository.findById(petFeederId)
                    .orElseThrow(() -> new NotFoundException("Pet feeder not found"));

            Pet pet = petRepository.findById(petId)
                    .orElseThrow(() -> new NotFoundException("Pet not found"));

            petFeeder.setPet(pet);
            petFeederRepository.save(petFeeder);
        } catch (NotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while adding the pet to the pet feeder: " + ex.getMessage());
        }
    }

    @Override
    @Transactional
    public void deletePetFeeder(Long petFeederId) {
        try {
            notificationRepository.deleteByPetFeederId(petFeederId);
            timeRepository.deleteTimesByPetFeederId(petFeederId);
            scheduleRepository.deleteByPetFeederId(petFeederId);
            petFeederHistoryRepository.deleteByPetFeederId(petFeederId);
            petFeederRepository.deleteById(petFeederId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<PetFeederDTO> getAllPetFeedersByUser(Long userId) {
        return petFeederRepository.findAllByUserUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    @Override
    public void setActivePetFeeder(Long userId, Long petFeederId) {
        try {
            UserP user = userRepository.findById(userId)
                    .orElseThrow(() -> new NotFoundException("User not found"));

            PetFeeder petFeeder = petFeederRepository.findById(petFeederId)
                    .orElseThrow(() -> new NotFoundException("Pet feeder not found"));

            petFeeder.setUser(user);
            petFeeder.setActive(true);

            petFeederRepository.save(petFeeder);

            petFeeder.setConnected(true);
            petFeederRepository.save(petFeeder);
        } catch (NotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while setting the pet feeder as active: " + ex.getMessage());
        }
    }
    @Override
    public List<PetFeederDTO> getAllConnectedPetFeedersByUser(Long userId) {
        return petFeederRepository.findAllByUserUserIdAndConnectedTrue(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deactivatePetFeeder(Long userId, Long petFeederId) {
        Optional<PetFeeder> optionalPetFeeder = petFeederRepository.findById(petFeederId);

        if (optionalPetFeeder.isPresent()) {
            PetFeeder petFeeder = optionalPetFeeder.get();

            if (petFeeder.getUser().getUserId().equals(userId)) {
                petFeeder.setActive(false);
                petFeederRepository.save(petFeeder);
            } else {
                throw new NotFoundException("Pet feeder not found for the specified user and pet");
            }
        } else {
            throw new NotFoundException("Pet feeder not found with ID: " + petFeederId);
        }
    }

    private PetFeeder convertToEntity(PetFeederDTO petFeederDTO) {
        PetFeeder petFeeder = new PetFeeder();
        petFeeder.setPetFeederLabel(petFeederDTO.getPetFeederLabel());
        petFeeder.setFoodLevel(petFeederDTO.getFoodLevel());
        petFeeder.setFoodHumidity(petFeederDTO.getFoodHumidity());
        petFeeder.setWaterTemperture(petFeederDTO.getWaterTemperture());
        petFeeder.setActive(false);
        petFeeder.setConnected(false);

        petFeeder.setUser(null);
        petFeeder.setPet(null);

        return petFeeder;
    }

    private PetFeederDTO convertToDto(PetFeeder pf) {
        PetFeederDTO pfDTO = new PetFeederDTO();
        pfDTO.setPetFeederId(pf.getPetFeederId());
        pfDTO.setPetFeederLabel(pf.getPetFeederLabel());
        pfDTO.setWaterLevel(pf.getWaterLevel());
        pfDTO.setFoodHumidity(pf.getFoodHumidity());
        pfDTO.setFoodLevel(pf.getFoodLevel());
        pfDTO.setActive(pf.isActive());
        pfDTO.setConnected(pf.isConnected());
        pfDTO.setWaterTemperture(pf.getWaterTemperture());
        if(pf.getPet()!=null)
            pfDTO.setPetId(pf.getPet().getPetId());
        if(pf.getUser()!=null)
        pfDTO.setUserId(pf.getUser().getUserId());
        return pfDTO;
    }
}

package com.example.petfeedercloud.services;

import com.example.petfeedercloud.dtos.PetFeederDTO;
import com.example.petfeedercloud.dtos.PetFeederHistoryDTO;
import com.example.petfeedercloud.dtos.UserDTO;
import com.example.petfeedercloud.jwt.AuthenticationResponse;
import com.example.petfeedercloud.models.PetFeeder;
import com.example.petfeedercloud.models.PetFeederHistory;
import com.example.petfeedercloud.models.Role;
import com.example.petfeedercloud.models.UserP;
import com.example.petfeedercloud.repositories.PetFeederHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PetFeederHistoryServiceImpl implements PetFeederHistoryService{

    private PetFeederHistoryRepository petFeederHistoryRepository;
    private PetFeederService petFeederService;
    //This function is going to be executed at 1am, 7am, 1pm, 7pm
    @Override
    @Scheduled(cron = "0 16 19 * * ?")
    public void fetchDataAtScheduledTimes() {
        List<PetFeederDTO> petFeeders = petFeederService.getAllPetFeeders();
        for (PetFeederDTO petFeeder : petFeeders) {
            savePetFeederHistory(petFeeder);
        }
    }
    @Override
    public PetFeederHistory savePetFeederHistory(PetFeederDTO petFeederDTO) {
        try{
            // create a new history to save it to the db
            PetFeederHistory newHistory = new PetFeederHistory();
            //setters
            newHistory.setPetFeeder(petFeederService.getPetFeederById(petFeederDTO.getPetFeederId()));
            newHistory.setDate(LocalDate.now()); // Set current date
            newHistory.setTime(LocalTime.now());
            newHistory.setFoodLevel(petFeederDTO.getFoodLevel());
            newHistory.setFoodHumidity(petFeederDTO.getFoodHumidity());
            newHistory.setWaterTemperature(petFeederDTO.getWaterTemperture());
            //savee to db
            petFeederHistoryRepository.save(newHistory);
            return newHistory;
        }catch (Exception e){
                throw new RuntimeException(e);
        }
    }

}

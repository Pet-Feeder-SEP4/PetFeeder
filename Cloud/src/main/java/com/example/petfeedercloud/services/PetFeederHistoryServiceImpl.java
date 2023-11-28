package com.example.petfeedercloud.services;

import com.example.petfeedercloud.dtos.GetDTOs.GetDateIntervalDTO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetFeederHistoryServiceImpl implements PetFeederHistoryService{

    @Autowired
    private PetFeederHistoryRepository petFeederHistoryRepository;
    @Autowired
    private PetFeederService petFeederService;

    @Override
    public List<PetFeederHistoryDTO> getHistoryByDate(Long petFeederId, LocalDate date) {
        List<PetFeederHistory> historyList = petFeederHistoryRepository.findByPetFeederIdAndDate(petFeederId, date);
        List<PetFeederHistoryDTO> historyDTOList = historyList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return historyDTOList;
    }

    @Override
    public List<PetFeederHistoryDTO> getHistoryByDateInterval(GetDateIntervalDTO getDateIntervalDTO) {
        Long petFeederId = getDateIntervalDTO.getPetFeederId();
        LocalDate startDate = getDateIntervalDTO.getStartDate();
        LocalDate endDate = getDateIntervalDTO.getEndDate();

        List<PetFeederHistory> historyList = petFeederHistoryRepository.findByPetFeederIdAndDateInterval(petFeederId, startDate, endDate);

        // Convert to DTOs
        List<PetFeederHistoryDTO> historyDTOList = historyList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return historyDTOList;
    }

    //This function is going to be executed at 1am, 7am, 1pm, 7pm OR EVERY 4 HOURS
    //@Scheduled(fixedRate = 1000*60*60*4)
    private void scheduleFixedRateTask() {

        List<PetFeederDTO> petFeeders = petFeederService.getAllPetFeeders();
        System.out.println(
                "TESTING TEST - " +petFeeders.toString());
        for (PetFeederDTO petFeeder : petFeeders) {
            savePetFeederHistory(petFeeder);
        }
    }
    private PetFeederHistory savePetFeederHistory(PetFeederDTO petFeederDTO) {
        try{
            // create a new history to save it to the db
            PetFeederHistory newHistory = new PetFeederHistory();
            //setters
            newHistory.setPetFeeder(petFeederService.getPetFeederById(petFeederDTO.getPetFeederId()));
            newHistory.setDate(LocalDate.now());
            newHistory.setTime(LocalTime.now());
            newHistory.setFoodLevel(petFeederDTO.getFoodLevel());
            newHistory.setFoodHumidity(petFeederDTO.getFoodHumidity());
            newHistory.setWaterTemperature(petFeederDTO.getWaterTemperture());
            //Save to db
            petFeederHistoryRepository.save(newHistory);
            return newHistory;
        }catch (Exception e){
                throw new RuntimeException(e);
        }
    }
    //DTO CONVERTER
    private PetFeederHistoryDTO convertToDTO(PetFeederHistory petFeederHistory) {
        // Assuming you have a method to convert PetFeederHistory to PetFeederHistoryDTO
        // You need to implement this method based on your requirements
        PetFeederHistoryDTO historyDTO = new PetFeederHistoryDTO();
        // Set properties from petFeederHistory to historyDTO
        historyDTO.setPetFeederHistoryId(petFeederHistory.getPetFeederHistoryId());
        historyDTO.setPetFeeder(petFeederHistory.getPetFeeder().getPetFeederId());
        historyDTO.setDate(petFeederHistory.getDate());
        historyDTO.setTime(petFeederHistory.getTime());
        historyDTO.setFoodLevel(petFeederHistory.getFoodLevel());
        historyDTO.setFoodHumidity(petFeederHistory.getFoodHumidity());
        historyDTO.setWaterTemperature(petFeederHistory.getWaterTemperature());
        return historyDTO;
    }

}

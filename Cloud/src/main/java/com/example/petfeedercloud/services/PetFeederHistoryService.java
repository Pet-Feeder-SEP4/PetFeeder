package com.example.petfeedercloud.services;

import com.example.petfeedercloud.dtos.GetDTOs.GetDateIntervalDTO;
import com.example.petfeedercloud.dtos.PetFeederDTO;
import com.example.petfeedercloud.dtos.PetFeederHistoryDTO;
import com.example.petfeedercloud.dtos.UserDTO;
import com.example.petfeedercloud.jwt.AuthenticationResponse;
import com.example.petfeedercloud.models.PetFeederHistory;

import java.time.LocalDate;
import java.util.List;

public interface PetFeederHistoryService {
    List<PetFeederHistoryDTO> getHistoryByDate(Long petFeederId, LocalDate date);
    List<PetFeederHistoryDTO> getHistoryByDateInterval(GetDateIntervalDTO dateIntervalDTO);
}

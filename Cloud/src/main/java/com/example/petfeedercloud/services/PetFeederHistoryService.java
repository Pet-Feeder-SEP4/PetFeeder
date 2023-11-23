package com.example.petfeedercloud.services;

import com.example.petfeedercloud.dtos.PetFeederDTO;
import com.example.petfeedercloud.dtos.PetFeederHistoryDTO;
import com.example.petfeedercloud.dtos.UserDTO;
import com.example.petfeedercloud.jwt.AuthenticationResponse;
import com.example.petfeedercloud.models.PetFeederHistory;

public interface PetFeederHistoryService {
    void fetchDataAtScheduledTimes();
    PetFeederHistory savePetFeederHistory (PetFeederDTO petFeederDTO);
}

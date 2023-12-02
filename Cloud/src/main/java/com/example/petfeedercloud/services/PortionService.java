package com.example.petfeedercloud.services;

import com.example.petfeedercloud.dtos.PortionDTO;
import com.example.petfeedercloud.models.Portion;

import java.util.List;

public interface PortionService {
    Portion getPortionById(Long portionId);
    List<Portion> getPortionsByTime(Long timeId);
    Portion createPortion(PortionDTO portion);
    Portion updatePortion(Long portionId,PortionDTO portion);
    Portion deletePortion(Long portionId);

}

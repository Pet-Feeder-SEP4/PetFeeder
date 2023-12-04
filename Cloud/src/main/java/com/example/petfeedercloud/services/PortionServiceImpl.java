package com.example.petfeedercloud.services;

import com.example.petfeedercloud.dtos.PortionDTO;
import com.example.petfeedercloud.models.Portion;
import com.example.petfeedercloud.repositories.PortionRepository;
import com.example.petfeedercloud.repositories.TimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PortionServiceImpl implements PortionService{
    private final PortionRepository portionRepository;
    private final TimeRepository timeRepository;
    @Override
    public Portion getPortionById(Long portionId) {
        return portionRepository.findById(portionId)
                .orElseThrow(() -> new NotFoundException("Portion not found with id: " + portionId));
    }

    @Override
    public Portion getPortionsByTime(Long timeId) {
        // Implement logic to fetch portions by time
        return portionRepository.getPortionsByTimeId(timeId);
    }


    @Override
    public Portion createPortion(PortionDTO portionDTO) {
        Portion portion = new Portion();
        portion.setLabel(portionDTO.getLabel());
        portion.setPortionSize(portionDTO.getPortionSize());
        //check if time with this id exists??????
        portion.setTime(timeRepository.getReferenceById(portionDTO.getTimeId()));
        return portionRepository.save(portion);
    }

    @Override
    public Portion updatePortion(Long portionId, PortionDTO portionDTO) {
        Portion existingPortion = portionRepository.findById(portionId)
                .orElseThrow(() -> new NotFoundException("Portion not found with id: " + portionId));
        existingPortion.setLabel(portionDTO.getLabel());
        existingPortion.setPortionSize(portionDTO.getPortionSize());
        //check if time with this id exists??????
        existingPortion.setTime(timeRepository.getReferenceById(portionDTO.getTimeId()));
        return portionRepository.save(existingPortion);
    }

    @Override
    public Portion deletePortion(Long portionId) {
        Portion portionToDelete = portionRepository.findById(portionId)
                .orElseThrow(() -> new NotFoundException("Portion not found with id: " + portionId));

        portionRepository.delete(portionToDelete);
        return portionToDelete;
    }
}

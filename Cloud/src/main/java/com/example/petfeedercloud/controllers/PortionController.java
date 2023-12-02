package com.example.petfeedercloud.controllers;

import com.example.petfeedercloud.dtos.PortionDTO;
import com.example.petfeedercloud.models.Portion;
import com.example.petfeedercloud.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.List;

@RestController
@RequestMapping("/portion")
@RequiredArgsConstructor
public class PortionController {
    private final TimeService timeService;
    private final PortionService portionService;

    @GetMapping("/{portionId}")
    public ResponseEntity<Portion> getPortionById(@PathVariable Long portionId) {
        try {
            Portion portion = portionService.getPortionById(portionId);
            return ResponseEntity.ok(portion);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/times/{timeId}")
    public ResponseEntity<List<Portion>> getPortionsByTime(@PathVariable Long timeId) {
        // Error handling for TimeNotFoundException goes here, if needed
        List<Portion> portions = portionService.getPortionsByTime(timeId);
        return ResponseEntity.ok(portions);
    }

    @PostMapping
    public ResponseEntity<Void> createPortion(@RequestBody PortionDTO portionDTO) {
        try {
            portionService.createPortion(portionDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{portionId}")
    public ResponseEntity<Portion> updatePortion(@PathVariable Long portionId, @RequestBody PortionDTO portionDTO) {
        try {
            Portion updatedPortion = portionService.updatePortion(portionId, portionDTO);
            return ResponseEntity.ok(updatedPortion);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{portionId}")
    public ResponseEntity<Portion> deletePortion(@PathVariable Long portionId) {
        try {
            Portion deletedPortion = portionService.deletePortion(portionId);
            return ResponseEntity.ok(deletedPortion);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

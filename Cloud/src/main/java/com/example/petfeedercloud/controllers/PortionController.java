package com.example.petfeedercloud.controllers;

import com.example.petfeedercloud.dtos.GetDTOs.GetPortionDTO;
import com.example.petfeedercloud.dtos.PortionDTO;
import com.example.petfeedercloud.models.Portion;
import com.example.petfeedercloud.services.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import javax.sound.sampled.Port;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/portion")
@RequiredArgsConstructor
public class PortionController {
    private final TimeService timeService;
    private final PortionService portionService;

    @Operation(summary = "Get portions by id", description = "Returns portion by id")
    @GetMapping("/{portionId}")
    public ResponseEntity<?> getPortionById(@PathVariable Long portionId) {
        try {
            Portion portion = portionService.getPortionById(portionId);
            GetPortionDTO portionDTO = new GetPortionDTO(portionId,portion.getLabel(),portion.getPortionSize(),portion.getTime().getTimeId());
            return ResponseEntity.ok(portionDTO);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @Operation(summary = "Get portions by time", description = "Returns portion from the time")
    @GetMapping("/times/{timeId}")
    public ResponseEntity<?> getPortionByTime(@PathVariable Long timeId) {
        try {
            Portion portion = portionService.getPortionsByTime(timeId);
            GetPortionDTO portionDTO = new GetPortionDTO(portion.getPortionId(),portion.getLabel(),portion.getPortionSize(),timeId);
            return ResponseEntity.ok(portionDTO);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @Operation(summary = "Create portion", description = "Returns the portion created")
    @PostMapping
    public ResponseEntity<GetPortionDTO> createPortion(@RequestBody PortionDTO portionDTO) {
        try {
            Portion createdPortion = portionService.createPortion(portionDTO);

            GetPortionDTO createdPortionDTO = new GetPortionDTO(
                    createdPortion.getPortionId(),
                    createdPortion.getLabel(),
                    createdPortion.getPortionSize(),
                    createdPortion.getTime().getTimeId()
            );

            return new ResponseEntity<>(createdPortionDTO, HttpStatus.CREATED);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @Operation(summary = "Update portions", description = "Returns the new updated portion")
    @PutMapping("/{portionId}")
    public ResponseEntity<?> updatePortion(@PathVariable Long portionId, @RequestBody PortionDTO portionDTO) {
        try {
            Portion updatedPortion = portionService.updatePortion(portionId, portionDTO);
            GetPortionDTO portionDTOReturn = new GetPortionDTO(portionId,updatedPortion.getLabel(),updatedPortion.getPortionSize(),updatedPortion.getTime().getTimeId());
            return ResponseEntity.ok(portionDTOReturn);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Deletes the portion", description = "Returns a message of success")
    @DeleteMapping("/{portionId}")
    public ResponseEntity<?> deletePortion(@PathVariable Long portionId) {
        try {
            Portion deletedPortion = portionService.deletePortion(portionId);
            return new ResponseEntity<>("Portion with id "+portionId+" deleted! xD", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

package com.example.petfeedercloud;

import com.example.petfeedercloud.controllers.PetController;
import com.example.petfeedercloud.dtos.PetDTO;
import com.example.petfeedercloud.services.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PetControllerTest {

    @Mock
    private PetService petService;

    @InjectMocks
    private PetController petController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllPets() {
        // Mock behavior
        List<PetDTO> petList = new ArrayList<>();
        when(petService.getAllPets()).thenReturn(petList);

        // When
        List<PetDTO> result = petController.getAllPets();

        // Then
        assertEquals(petList, result);
    }

    @Test
    void testGetPetById_ValidId() {
        // Given
        long petId = 1L;
        PetDTO petDTO = new PetDTO();
        petDTO.setPetId(petId);

        // Mock behavior
        when(petService.getPetById(petId)).thenReturn(petDTO);

        // When
        ResponseEntity<?> response = petController.getPetById(petId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(petDTO, response.getBody());
    }

    @Test
    void testGetPetById_InvalidId() {
        // Given
        long petId = 99L;

        // Mock behavior
        when(petService.getPetById(petId)).thenThrow(new NotFoundException("Pet not found"));

        // When
        ResponseEntity<?> response = petController.getPetById(petId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Pet not found", response.getBody());
    }

    @Test
    void testSavePet() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, Calendar.NOVEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 29);

        Date date = calendar.getTime();
        // Given
        PetDTO petDTO = new PetDTO();
        petDTO.setUserId(1L);
        petDTO.setName("Fluffy");
        petDTO.setBirthdate(date);
        petDTO.setWeight(5.2);
        petDTO.setBreed("Labrador");

        // Mock behavior
        when(petService.createPet(petDTO)).thenReturn(petDTO);

        // When
        ResponseEntity<String> responseEntity = petController.savePet(petDTO);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            PetDTO createdPet = petService.createPet(petDTO);
            assertEquals(petDTO, createdPet);
        }
    }

    @Test
    void testUpdatePet() {
        // Given
        long petId = 1L;
        PetDTO petDTO = new PetDTO();
        petDTO.setName("Vigo");

        // Mock behavior
        doNothing().when(petService).updatePet(petId, petDTO);

        // When
        ResponseEntity<String> responseEntity = petController.updatePet(petId, petDTO);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Verify that the petService.updatePet method was called with the provided parameters
        verify(petService, times(1)).updatePet(petId, petDTO);
    }

    @Test
    void testDeletePet() {
        // Given
        long petId = 1L;

        // Mock behavior
        doNothing().when(petService).deletePet(petId);

        // When
        ResponseEntity<?> responseEntity = petController.deletePet(petId);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetAllPetsByUser() {
        // Given
        long userId = 1L;
        List<PetDTO> petList = new ArrayList<>();

        // Mock behavior
        when(petService.getAllPetsByUser(userId)).thenReturn(petList);

        // When
        List<PetDTO> result = petController.getAllPetsByUser(userId);

        // Then
        assertEquals(petList, result);
    }
}


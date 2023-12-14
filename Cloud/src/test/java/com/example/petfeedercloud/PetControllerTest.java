package com.example.petfeedercloud;

import com.example.petfeedercloud.controllers.PetController;
import com.example.petfeedercloud.dtos.PetDTO;
import com.example.petfeedercloud.services.PetService;
import jakarta.validation.ConstraintViolationException;
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
    public void testSavePet_MissingName() {
        PetDTO petDTO = new PetDTO();
        petDTO.setBirthdate(new Date());

        ResponseEntity<String> responseEntity = petController.savePet(petDTO);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Please provide a name for the pet.", responseEntity.getBody());
    }

    @Test
    public void testSavePet_MissingBirthdate() {
        PetDTO petDTO = new PetDTO();
        petDTO.setName("Fluffy");

        ResponseEntity<String> responseEntity = petController.savePet(petDTO);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Please provide the pet's birthdate.", responseEntity.getBody());
    }

    @Test
    void testGetAllPets() {
        List<PetDTO> petList = new ArrayList<>();
        when(petService.getAllPets()).thenReturn(petList);

        List<PetDTO> result = petController.getAllPets();

        assertEquals(petList, result);
    }

    @Test
    void testGetPetById_ValidId() {
        long petId = 1L;
        PetDTO petDTO = new PetDTO();
        petDTO.setPetId(petId);

        when(petService.getPetById(petId)).thenReturn(petDTO);

        ResponseEntity<?> response = petController.getPetById(petId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(petDTO, response.getBody());
    }

    @Test
    void testGetPetById_InvalidId() {
        long petId = 99L;

        when(petService.getPetById(petId)).thenThrow(new NotFoundException("Pet not found"));

        ResponseEntity<?> response = petController.getPetById(petId);

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
        PetDTO petDTO = new PetDTO();
        petDTO.setPetId(1L);
        petDTO.setUserId(1L);
        petDTO.setName("Fluffy");
        petDTO.setBirthdate(date);
        petDTO.setWeight(5.2);
        petDTO.setBreed("Labrador");

        when(petService.createPet(petDTO)).thenReturn(petDTO);

        ResponseEntity<String> responseEntity = petController.savePet(petDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            PetDTO createdPet = petService.createPet(petDTO);
            assertEquals(petDTO, createdPet);
        }
    }

    @Test
    void testUpdatePet() {
        long petId = 1L;
        PetDTO petDTO = new PetDTO();
        petDTO.setName("Vigo");

        doNothing().when(petService).updatePet(petId, petDTO);


        ResponseEntity<String> responseEntity = petController.updatePet(petId, petDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(petService, times(1)).updatePet(petId, petDTO);
    }
    @Test
    void testGetAllPetsByUser() {
        long userId = 1L;

        PetDTO petDTO = new PetDTO();
        petDTO.setPetId(1L);
        petDTO.setUserId(userId);
        petDTO.setName("Fluffy");

        List<PetDTO> petList = new ArrayList<>();
        petList.add(petDTO);

        when(petService.getAllPetsByUser(anyLong())).thenReturn(petList);

        List<PetDTO> result = petController.getAllPetsByUser(userId);

        assertEquals(petList, result);
    }
    @Test
    void testDeletePet() {
        long petId = 1L;

        doNothing().when(petService).deletePet(petId);

        ResponseEntity<?> responseEntity = petController.deletePet(petId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}


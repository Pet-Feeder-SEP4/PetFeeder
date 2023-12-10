package com.example.petfeedercloud;
import com.example.petfeedercloud.services.PetFeederService;
import com.example.petfeedercloud.controllers.PetFeederController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;

public class PetFeederControllerTest {

    @Mock
    private PetFeederService petFeederService;

    @InjectMocks
    private PetFeederController petFeederController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testActivatePetFeeder() {
        doNothing().when(petFeederService).setActivePetFeeder(anyLong(), anyLong());

        ResponseEntity<String> response = petFeederController.activatePetFeeder(1L, 2L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Pet feeder activated successfully", response.getBody());
    }

    @Test
    public void testDeactivatePetFeeder() {

        doNothing().when(petFeederService).deactivatePetFeeder(anyLong(), anyLong(), anyLong());

        ResponseEntity<String> response = petFeederController.deactivatePetFeeder(1L, 2L, 3L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Pet feeder deactivated successfully", response.getBody());
    }
}


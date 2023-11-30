package com.example.petfeedercloud;
import com.example.petfeedercloud.controllers.AuthenticationController;
import com.example.petfeedercloud.dtos.UserDTO;
import com.example.petfeedercloud.dtos.UserLoginDTO;
import com.example.petfeedercloud.jwt.AuthenticationResponse;
import com.example.petfeedercloud.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.naming.AuthenticationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AuthenticationControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser_InvalidEmail() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("invalidemail.com");
        userDTO.setPassword("validPassword");

        when(userService.saveUser(userDTO)).thenThrow(new IllegalArgumentException("Invalid email"));

        ResponseEntity<?> responseEntity = authenticationController.register(userDTO);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid email", responseEntity.getBody());
    }

    @Test
    public void testRegisterUser_InvalidPassword() {

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("validemail@example.com");
        userDTO.setPassword("short");

        when(userService.saveUser(userDTO)).thenThrow(new IllegalArgumentException("Invalid password"));

        ResponseEntity<?> responseEntity = authenticationController.register(userDTO);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid password", responseEntity.getBody());
    }
}


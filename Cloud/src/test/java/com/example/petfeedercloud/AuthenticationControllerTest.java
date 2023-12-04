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

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    public void testRegisterUser_ValidCredentials() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setEmail("valid@email.com");
        userDTO.setPassword("ValidPassword1");

        ResponseEntity<?> responseEntity = authenticationController.register(userDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
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

    @Test
    public void testAuthenticate_ValidCredentials() {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail("validemail@example.com");
        userLoginDTO.setPassword("validPassword");

        AuthenticationResponse authResponse = new AuthenticationResponse();
        when(userService.authenticateUser(userLoginDTO)).thenReturn(authResponse);

        ResponseEntity<?> responseEntity = authenticationController.authenticate(userLoginDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof AuthenticationResponse);
    }

    @Test
    public void testAuthenticate_EmailNotFound() {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail("nonexistent@email.com");
        userLoginDTO.setPassword("somePassword");

        // Mocking userService.authenticateUser to simulate email not found scenario
        when(userService.authenticateUser(userLoginDTO)).thenReturn(null);

        ResponseEntity<?> responseEntity = authenticationController.authenticate(userLoginDTO);

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals("Invalid Credentials", responseEntity.getBody());
    }

}


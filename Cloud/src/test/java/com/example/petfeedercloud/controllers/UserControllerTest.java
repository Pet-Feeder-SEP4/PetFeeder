package com.example.petfeedercloud.controllers;

import com.example.petfeedercloud.dtos.UserDTO;
import com.example.petfeedercloud.dtos.UserLoginDTO;
import com.example.petfeedercloud.models.UserP;
import com.example.petfeedercloud.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;
    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserService.class);
        userController = new UserController(userService);
    }


    @Test
    void testAuthenticateUser_Success() {
        // Mocking the UserService to return a user when authenticateUser is called
        UserLoginDTO userDTO = new UserLoginDTO();
        userDTO.setEmail("jack@jack.com");
        userDTO.setPassword("jack");

        Mockito.when(userService.authenticateUser(userDTO))
                .thenReturn(new UserP());

        ResponseEntity<String> response = userController.authenticateUser(userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Authentication successful", response.getBody());
    }
    @Test
    void testAuthenticateUser_Failure() {
        // Mocking the UserService to return null when authenticateUser is called
        UserLoginDTO userDTO = new UserLoginDTO();
        Mockito.when(userService.authenticateUser(any(UserLoginDTO.class)))
                .thenReturn(null);

        ResponseEntity<String> response = userController.authenticateUser(userDTO);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid credentials", response.getBody());
    }
    @Test
    void testRegisterUser_Success() {
        // Mocking the UserService to return null, indicating the user does not exist
        Mockito.when(userService.getUserByEmail(any(String.class)))
                .thenReturn(null);

        // Mocking the UserService to not throw an exception when saveUser is called
        Mockito.doNothing().when(userService).saveUser(any(UserP.class));

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password");
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");

        ResponseEntity<String> response = userController.registerUser(userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Registration successful", response.getBody());
    }
    @Test
    void testRegisterUser_UserAlreadyExists() {
        // Mocking the UserService to return a user, indicating the user already exists
        Mockito.when(userService.getUserByEmail(any(String.class)))
                .thenReturn(new UserP());

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password");
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");

        ResponseEntity<String> response = userController.registerUser(userDTO);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("User with this email already exists.", response.getBody());
    }

    @Test
    void testRegisterUser_InvalidInput() {
        // Testing with invalid input (null values)
        UserDTO userDTO = new UserDTO();

        ResponseEntity<String> response = userController.registerUser(userDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("All fields are required for registration.", response.getBody());
    }
}
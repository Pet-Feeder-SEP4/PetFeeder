package com.example.petfeedercloud.controllers;


import com.example.petfeedercloud.dtos.UserDTO;
import com.example.petfeedercloud.dtos.UserLoginDTO;
import com.example.petfeedercloud.models.UserP;
import com.example.petfeedercloud.services.UserService;
import com.example.petfeedercloud.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody UserLoginDTO userDTO) {
        UserP authenticatedUser = userService.authenticateUser(userDTO);
        if (authenticatedUser != null) {
            return ResponseEntity.ok("Authentication successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        //validate input
        if (userDTO.getEmail() == null || userDTO.getPassword() == null ||
                userDTO.getFirstName() == null || userDTO.getLastName() == null) {
            return ResponseEntity.badRequest().body("All fields are required for registration.");
        }
        //check if the user exists
        UserP existingUser = userService.getUserByEmail(userDTO.getEmail());
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this email already exists.");
        }
        //create a new user and save it to the db
        UserP newUser = new UserP();
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(userDTO.getPassword());
        userService.saveUser(newUser);

        return ResponseEntity.ok("Registration successful");
    }
}
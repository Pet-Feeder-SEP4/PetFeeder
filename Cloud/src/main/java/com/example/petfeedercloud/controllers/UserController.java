package com.example.petfeedercloud.controllers;


import com.example.petfeedercloud.dtos.UserDTO;
import com.example.petfeedercloud.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @Operation(summary = "Get alll users", description = "This will return all users in the system")
    @GetMapping("/all")
    public  ResponseEntity<List<UserDTO>> getAllUsers() {
        try {
            List<UserDTO> users = userService.getAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "Get user by id", description = "This will return the user by its id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            UserDTO user = userService.getUserById(id);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id " + id + " not found!!!");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching user!!");
        }
    }
    @Operation(summary = "Get user by email", description = "This will return the user by its email")
    @GetMapping("/email")
    public ResponseEntity<?> getUserByEmail(@RequestParam String email) {
        try {
            UserDTO user = userService.getUserByEmailDto(email);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with email " + email + " not found!!!");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching user!!");
        }
    }
}
package com.example.petfeedercloud.controllers;

import com.example.petfeedercloud.dtos.UserDTO;
import com.example.petfeedercloud.dtos.UserLoginDTO;
import com.example.petfeedercloud.jwt.AuthenticationResponse;
import com.example.petfeedercloud.jwt.serviceJWT.JwtService;
import com.example.petfeedercloud.models.UserP;
import com.example.petfeedercloud.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final JwtService jwtTokenProvider;

    @Operation(summary = "Register user", description = "This will return a token if the user is created")
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserDTO userDTO) {
            return ResponseEntity.ok(userService.saveUser(userDTO));
    }

    @Operation(summary = "Login", description = "This will return a token if the user is authenticate")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody UserLoginDTO userDTO) {
        return ResponseEntity.ok(userService.authenticateUser(userDTO));
    }

    @Operation(summary = "Get User ID by Token", description = "This will return the user ID based on the provided token")
    @GetMapping("/userId")
    public ResponseEntity<Long> getUserIdByToken(@RequestHeader("Authorization") String authorizationHeader) {
        // Extract the token from the Authorization header
        String token = authorizationHeader.substring("Bearer ".length());

        Long userId = jwtTokenProvider.extractUserId(token);

        // Return the user ID in the response
        return ResponseEntity.ok(userId);
    }
}

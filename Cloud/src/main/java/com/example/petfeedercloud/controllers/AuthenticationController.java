package com.example.petfeedercloud.controllers;

import com.example.petfeedercloud.dtos.UserDTO;
import com.example.petfeedercloud.dtos.UserLoginDTO;
import com.example.petfeedercloud.jwt.AuthenticationResponse;

import com.example.petfeedercloud.jwt.serviceJWT.JwtServiceInterface;
import com.example.petfeedercloud.models.UserP;
import com.example.petfeedercloud.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final JwtServiceInterface jwtTokenProvider;

    @Operation(summary = "Register user", description = "This will return a token if the user is created")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        try {
            AuthenticationResponse authenticationResponse = userService.saveUser(userDTO);
            return ResponseEntity.ok(authenticationResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Login", description = "This will return a token if the user is authenticate")
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody UserLoginDTO userDTO) {
        try{
            AuthenticationResponse authenticationResponse = userService.authenticateUser(userDTO);
            if (authenticationResponse == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
            }
            return ResponseEntity.ok(authenticationResponse);
        }catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
        }


    }

    @Operation(summary = "Get User Id by JWT", description = "This will get the user id of the user logged in!")
    @GetMapping("/user")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
        try{
            Long userId = jwtTokenProvider.extractUserId(request.getHeader("Authorization").substring(7));
            return ResponseEntity.ok(userId);
        }catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token not valid.");
        }
    }

    @Operation(summary = "Logout", description = "This will logout the user")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            // Blacklist the token
            jwtTokenProvider.blacklistToken(token);
            return ResponseEntity.ok("Logout successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing Authorization header");
        }
    }
}

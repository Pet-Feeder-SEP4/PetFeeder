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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final JwtServiceInterface jwtTokenProvider;

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

    @Operation(summary = "Get User Id by JWT", description = "This will get the user id of the user logged in!")
    @GetMapping("/user")
    public Long getUserInfo(HttpServletRequest request) {
        return jwtTokenProvider.extractUserId(request.getHeader("Authorization").substring(7));
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

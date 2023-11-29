package com.example.petfeedercloud.jwt.serviceJWT;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public interface JwtServiceInterface {
    Long extractUserId(String token);
    String generateeToken(Map<String, Object> extraClaims, UserDetails userDetails);
    String generateToken(UserDetails userDetails);
    boolean isTokenValid(String token,UserDetails userDetails);
    <T> T extractClaim(String token, Function<Claims,T> claimResolver);
    String extractUsername(String token);
    //logout
    boolean isTokenBlacklisted(String token);
    void blacklistToken(String token);
}

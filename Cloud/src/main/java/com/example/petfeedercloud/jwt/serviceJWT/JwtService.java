package com.example.petfeedercloud.jwt.serviceJWT;

import com.example.petfeedercloud.dtos.UserDTO;
import com.example.petfeedercloud.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class JwtService {
    //Custom because we are using our user object and not springsframework's one

    private UserService userService;
    private static final String SECRET_KEY ="IFVXybWvFdDIFVXybWvFdDt0X90pUjn9lw48H3grw1yIFVXybWvFdDt0X90pUjn9lw48H3grw1yIFVXybWvFdDt0X90pUjn9lw48H3grw1yIFVXybWvFdDt0X90pUjn9lw48H3grw1yIFVXybWvFdDt0X90pUjn9lw48H3grw1yIFVXybWvFdDt0X90pUjn9lw48H3grw1yIFVXybWvFdDt0X90pUjn9lw48H3grw1yIFVXybWvFdDt0X90pUjn9lw48H3grw1yIFVXybWvFdDt0X90pUjn9lw48H3grw1yIFVXybWvFdDt0X90pUjn9lw48H3grw1yIFVXybWvFdDt0X90pUjn9lw48H3grw1yIFVXybWvFdDt0X90pUjn9lw48H3grw1yIFVXybWvFdDt0X90pUjn9lw48H3grw1yIFVXybWvFdDt0X90pUjn9lw48H3grw1yIFVXybWvFdDt0X90pUjn9lw48H3grw1yt0X90pUjn9lw48H3grw1y";
    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }
    public <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return  claimResolver.apply(claims);
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username= extractUsername(token);
        return (username.equals(userDetails.getUsername()))&& !isTokenExpired(token);
    }
    private boolean isTokenExpired(String token){
        return  extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        //Long userId = userService.getIdByEmail(userDetails.getUsername());
        claims.put("userId", 21L);
        return generateeToken(claims, userDetails);
    }
    public String generateeToken(Map<String, Object> extraClaims,UserDetails userDetails
    ){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    private Claims extractAllClaims(String token){
       return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();

    }
    public Long extractUserId(String token) {
        Map<String, Object> claims = extractAllClaims(token);
        return (Long) claims.get("userId");
    }
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

package com.example.petfeedercloud.services;

import com.example.petfeedercloud.dtos.UserDTO;
import com.example.petfeedercloud.dtos.UserLoginDTO;
import com.example.petfeedercloud.jwt.AuthenticationResponse;
import com.example.petfeedercloud.jwt.serviceJWT.JwtService;
import com.example.petfeedercloud.models.Role;
import com.example.petfeedercloud.models.UserP;
import com.example.petfeedercloud.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;  // Inject BCryptPasswordEncoder
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String PWD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%*]).{8,24}$";
    //JWT RELATED



    @Override
    public AuthenticationResponse authenticateUser(UserLoginDTO userDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getEmail(),userDTO.getPassword()));
        var user = userRepository.findByEmail(userDTO.getEmail());
        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public UserP getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public AuthenticationResponse saveUser (UserDTO user) {
        if (!verifyValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("Invalid email address");
        }
        if (!verifyPasswordComplexity(user.getPassword())) {
            throw new IllegalArgumentException("Password does not meet complexity requirements");
        }

        // create a new user and save it to the db
        UserP newUser = new UserP();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setRole(Role.USER);
        // Encrypt the password before saving it to the database
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(newUser);
        var jwt = jwtService.generateToken(newUser);
        return AuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public UserDTO getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(this::convertToDto)
                .orElse(null);
    }

    private UserDTO convertToDto(UserP user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        return userDTO;
    }

    private static boolean verifyValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private static boolean verifyPasswordComplexity(String password) {
        Pattern pattern = Pattern.compile(PWD_REGEX);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
    //JWT RELATED

}

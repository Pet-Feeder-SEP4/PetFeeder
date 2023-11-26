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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;  // Inject BCryptPasswordEncoder
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String PWD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%*]).{8,24}$";

    //USED BY JWTT
    @Override
    public AuthenticationResponse authenticateUser(UserLoginDTO userDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getEmail(),userDTO.getPassword()));
        var user = userRepository.findByEmail(userDTO.getEmail());
        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwt).build();
    }
    //USED BY JWTT
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
    //NON JWT
    @Override
    public UserDTO getUserByEmailDto(String email) {
        UserP user = userRepository.findByEmail(email);
        return user != null ? convertToDto(user) : null;
    }

    @Override
    public UserDTO getUserById(Long userId)  {
        return userRepository.findById(userId)
                .map(this::convertToDto)
                .orElse(null);
    }
    @Override
    public List<UserDTO> getAllUsers() {
        List<UserP> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Long getIdByEmail(String email) {
      return 2L;
    }

    //HELPERS
    //=======================
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
    //convert User entity to UserDTO
    private UserDTO convertToUserDTO(UserP user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        //dont send user passsword
        return userDTO;
    }
}

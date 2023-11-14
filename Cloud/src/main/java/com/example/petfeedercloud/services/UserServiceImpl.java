package com.example.petfeedercloud.services;


import com.example.petfeedercloud.dtos.PetDTO;
import com.example.petfeedercloud.dtos.UserDTO;
import com.example.petfeedercloud.dtos.UserLoginDTO;
import com.example.petfeedercloud.models.Pet;
import com.example.petfeedercloud.models.UserP;
import com.example.petfeedercloud.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String PWD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{8,24}$";

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserP authenticateUser(UserLoginDTO userDTO) {
        UserP user = userRepository.findByEmail(userDTO.getEmail());
        if (user != null && user.getPassword().equals(userDTO.getPassword())) {
            return user;
        }
        return null;
    }
    @Override
    public UserP getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    @Override
    public void saveUser(UserP user) {
        if (!verifyValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("Invalid email address");
        }
        if (!verifyPasswordComplexity(user.getPassword())) {
            //at least one lowercase letter
            //at least one uppercase letter
            //at least one digit
            //at least one special character from the set !@#$%
            //total length between 8 and 24 characters
            throw new IllegalArgumentException("Password does not meet complexity requirements");
        }
        // if email and password are valid
        userRepository.save(user);
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

}
package com.example.petfeedercloud.services;

import com.example.petfeedercloud.dtos.UserDTO;
import com.example.petfeedercloud.dtos.UserLoginDTO;
import com.example.petfeedercloud.jwt.AuthenticationResponse;
import com.example.petfeedercloud.models.UserP;

public interface UserService {
    AuthenticationResponse authenticateUser(UserLoginDTO userDTO);
    UserP getUserByEmail(String email);

    AuthenticationResponse saveUser(UserDTO newUser);
    UserDTO getUserById(Long userId);
}

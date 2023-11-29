package com.example.petfeedercloud.services;

import com.example.petfeedercloud.dtos.UserDTO;
import com.example.petfeedercloud.dtos.UserLoginDTO;
import com.example.petfeedercloud.jwt.AuthenticationResponse;
import com.example.petfeedercloud.models.UserP;

import java.util.List;

public interface UserService {
    AuthenticationResponse authenticateUser(UserLoginDTO userDTO);
    UserDTO getUserByEmailDto(String email);
    AuthenticationResponse saveUser(UserDTO newUser);
    UserDTO getUserById(Long userId) ;
    List<UserDTO> getAllUsers();
    Long getIdByEmail(String email);
}

package com.example.petfeedercloud.services;

import com.example.petfeedercloud.dtos.UserDTO;
import com.example.petfeedercloud.dtos.UserLoginDTO;
import com.example.petfeedercloud.models.UserP;

public interface UserService {
    UserP authenticateUser(UserLoginDTO userDTO);
    UserP getUserByEmail(String email);

    void saveUser(UserP newUser);
}

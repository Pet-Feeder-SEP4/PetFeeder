package com.example.petfeedercloud.services;


import com.example.petfeedercloud.dtos.UserDTO;
import com.example.petfeedercloud.models.UserP;
import com.example.petfeedercloud.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public UserP authenticateUser(UserDTO userDTO) {
        UserP user = userRepository.findByEmail(userDTO.getEmail());
        if (user != null && user.getPassword().equals(userDTO.getPassword())) {
            return user;
        }
        return null;
    }
    public UserP getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public void saveUser(UserP user) {
        userRepository.save(user);
    }

}
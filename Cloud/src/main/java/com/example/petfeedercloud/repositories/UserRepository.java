package com.example.petfeedercloud.repositories;

import com.example.petfeedercloud.models.UserP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserP, Long> {
    UserP findByEmail(String email);
}
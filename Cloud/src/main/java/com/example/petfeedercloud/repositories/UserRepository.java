package com.example.petfeedercloud.repositories;

import com.example.petfeedercloud.models.UserP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserP, Long> {
    UserP findByEmail(String email);

    @Query("SELECT p FROM UserP p WHERE p.userId = :userId")
    UserP findByUserId(Long userId);
}
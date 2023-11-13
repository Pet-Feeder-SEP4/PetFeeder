package com.example.petfeedercloud.repositories;

import com.example.petfeedercloud.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findAllByUserId(Long userId);
}

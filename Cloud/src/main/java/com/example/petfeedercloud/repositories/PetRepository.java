package com.example.petfeedercloud.repositories;

import com.example.petfeedercloud.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findAllByUserUserId(Long userId);

}

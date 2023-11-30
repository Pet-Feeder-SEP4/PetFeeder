package com.example.petfeedercloud.repositories;

import com.example.petfeedercloud.models.Pet;
import com.example.petfeedercloud.models.PetFeeder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetFeederRepository extends JpaRepository<PetFeeder, Long> {
    List<PetFeeder> findAllByUserUserId(Long userId);

    @Query("SELECT p FROM PetFeeder p WHERE p.petFeederId = :petFeederId")
    PetFeeder findByPetFeederId(Long petFeederId);
}

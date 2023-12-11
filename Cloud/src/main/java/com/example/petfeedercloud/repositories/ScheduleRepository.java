package com.example.petfeedercloud.repositories;

import com.example.petfeedercloud.models.Pet;
import com.example.petfeedercloud.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT p FROM Schedule p WHERE p.petFeeder.petFeederId = :petFeederId")
    List<Schedule> findByPetFeederId(Long petFeederId);
    @Modifying
    @Query("DELETE FROM Schedule s WHERE s.petFeeder.petFeederId = :petFeederId")
    void deleteByPetFeederId(Long petFeederId);
}

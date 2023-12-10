package com.example.petfeedercloud.repositories;

import com.example.petfeedercloud.dtos.PetFeederHistoryDTO;
import com.example.petfeedercloud.models.PetFeederHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PetFeederHistoryRepository extends JpaRepository<PetFeederHistory, Long> {
    @Query("SELECT p FROM PetFeederHistory p WHERE p.petFeeder.petFeederId = :petFeederId AND p.date = :date")
    List<PetFeederHistory> findByPetFeederIdAndDate(
            @Param("petFeederId") Long petFeederId,
            @Param("date") LocalDate date
    );
    @Query("SELECT DISTINCT p FROM PetFeederHistory p WHERE p.petFeeder.petFeederId = :petFeederId AND p.date BETWEEN :startDate AND :endDate")
    List<PetFeederHistory> findByPetFeederIdAndDateInterval(
            @Param("petFeederId") Long petFeederId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Modifying
    @Query("DELETE FROM PetFeederHistory s WHERE s.petFeeder.petFeederId = :petFeederId")
    void deleteByPetFeederId(Long petFeederId);
}


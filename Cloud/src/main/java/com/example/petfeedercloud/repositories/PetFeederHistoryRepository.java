package com.example.petfeedercloud.repositories;

import com.example.petfeedercloud.models.PetFeederHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetFeederHistoryRepository extends JpaRepository<PetFeederHistory, Long> {

}


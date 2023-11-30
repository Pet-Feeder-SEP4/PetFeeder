package com.example.petfeedercloud.repositories;

import com.example.petfeedercloud.models.Portion;
import com.example.petfeedercloud.models.Time;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PortionRepository extends JpaRepository<Portion, Long> {


}
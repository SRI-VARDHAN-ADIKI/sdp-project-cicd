package com.rayyan.backend.repository;

import com.rayyan.backend.entity.ExerciseDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseDetailsRepository extends JpaRepository<ExerciseDetails, Long> {
    List<ExerciseDetails> findByNameContainingIgnoreCase(String name);
}

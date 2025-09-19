package com.rayyan.backend.repository;

import com.rayyan.backend.entity.User;
import com.rayyan.backend.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<Workout> findByUser(User user);

    @Query("SELECT w FROM Workout w JOIN w.exercises e WHERE w.user.id = :userId AND e.exerciseDetails.id = :exerciseDetailsId")
    List<Workout> findByUserIdAndExerciseDetailsId(
            @Param("userId") Long userId,
            @Param("exerciseDetailsId") Long exerciseDetailsId
    );
}

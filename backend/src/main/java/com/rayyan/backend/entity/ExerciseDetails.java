package com.rayyan.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "exercise_details")
public class ExerciseDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "exerciseDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutExercise> workoutExercises;

    @NotBlank(message = "Exercise name is a required field.")
    private String name;

    @NotBlank(message = "Exercise type is a required field.")
    private String type;

    @NotBlank(message = "Exercise description is a required field.")
    private String description;

    private int difficulty;
}

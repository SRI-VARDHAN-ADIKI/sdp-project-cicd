package com.rayyan.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rayyan.backend.entity.WorkoutExercise;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExerciseDetailsDTO {

    private Long id;
    private String name;
    private String type;
    private String description;
    private int difficulty;
    private List<WorkoutExerciseDTO> workoutExercises;
}

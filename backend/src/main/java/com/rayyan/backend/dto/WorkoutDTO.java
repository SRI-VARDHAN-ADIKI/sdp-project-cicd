package com.rayyan.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rayyan.backend.entity.WorkoutExercise;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkoutDTO {

    private Long id;
    private String name;
    private LocalDateTime date;
    private Long userId;
    private List<WorkoutExerciseDTO> exercises;
}

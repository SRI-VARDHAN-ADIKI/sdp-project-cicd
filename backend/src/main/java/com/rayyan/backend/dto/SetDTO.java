package com.rayyan.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.Duration;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SetDTO {

    private Long id;
    private int reps;
    private Duration duration;
    private double weight;
    private Long workoutExerciseId;
}

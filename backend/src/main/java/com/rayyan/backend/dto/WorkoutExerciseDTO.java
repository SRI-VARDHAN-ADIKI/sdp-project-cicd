package com.rayyan.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rayyan.backend.entity.Set;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkoutExerciseDTO {

    private Long id;
    private Long workoutId;
    private Long exerciseDetailsId;
    private String exerciseDetailsName;
    private List<SetDTO> sets;
}

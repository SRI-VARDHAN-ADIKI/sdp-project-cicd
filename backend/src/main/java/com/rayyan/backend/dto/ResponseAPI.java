package com.rayyan.backend.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseAPI {

    private int statusCode;
    private String message;

    private String token;
    private String role;
    private String expirationTime;
    private String bookingConfirmationCode;

    private UserDTO user;
    private SetDTO set;
    private WorkoutDTO workout;
    private WorkoutExerciseDTO workoutExercise;
    private ExerciseDetailsDTO exerciseDetails;

    private List<UserDTO> userList;
    private List<WorkoutDTO> workoutList;
    private List<SetDTO> setList;
    private List<WorkoutExerciseDTO> workoutExerciseList;
    private List<ExerciseDetailsDTO> exerciseDetailsList;

}

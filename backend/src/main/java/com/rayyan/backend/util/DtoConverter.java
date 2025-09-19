package com.rayyan.backend.util;

import com.rayyan.backend.dto.*;
import com.rayyan.backend.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DtoConverter {

    public static UserDTO toUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setRole(user.getRole());
        userDTO.setAge(user.getAge());
        userDTO.setWeight(user.getWeight());
        userDTO.setHeight(user.getHeight());

        if(user.getWorkoutHistory() != null){
            userDTO.setWorkoutHistory(user.getWorkoutHistory()
                    .stream().map(DtoConverter::toWorkoutDTO)
                    .collect(Collectors.toList()));
        }

        return userDTO;
    }

    public static User toUser(UserDTO userDTO){
        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setRole(userDTO.getRole());
        user.setAge(userDTO.getAge());
        user.setWeight(userDTO.getWeight());
        user.setHeight(userDTO.getHeight());

        if(userDTO.getWorkoutHistory() != null){
            user.setWorkoutHistory(userDTO.getWorkoutHistory()
                    .stream().map(DtoConverter::toWorkout)
                    .collect(Collectors.toList()));
        }

        return user;
    }

    public static WorkoutDTO toWorkoutDTO(Workout workout){
        WorkoutDTO workoutDTO = new WorkoutDTO();

        workoutDTO.setId(workout.getId());
        workoutDTO.setName(workout.getName());
        workoutDTO.setDate(workout.getDate());
        workoutDTO.setUserId(workout.getUser().getId());

        if(workout.getExercises() != null){
            workoutDTO.setExercises(workout.getExercises()
                    .stream().map(DtoConverter::toWorkoutExerciseDTO)
                    .collect(Collectors.toList()));
        }

        return workoutDTO;
    }

    public static Workout toWorkout(WorkoutDTO workoutDTO){
        Workout workout = new Workout();
        workout.setId(workoutDTO.getId());
        workout.setName(workoutDTO.getName());
        workout.setDate(workoutDTO.getDate());

        // Assuming User entity can be fetched by userId
        // You might need a service to fetch the User entity
        User user = new User();
        user.setId(workoutDTO.getUserId());
        workout.setUser(user);

        if(workoutDTO.getExercises() != null){
            workout.setExercises(workoutDTO.getExercises()
                    .stream().map(DtoConverter::toWorkoutExercise)
                    .collect(Collectors.toList()));
        }

        return workout;
    }

    public static WorkoutExerciseDTO toWorkoutExerciseDTO(WorkoutExercise workoutExercise){
        WorkoutExerciseDTO workoutExerciseDTO = new WorkoutExerciseDTO();

        workoutExerciseDTO.setId(workoutExercise.getId());
        workoutExerciseDTO.setWorkoutId(workoutExercise.getWorkout().getId());
        workoutExerciseDTO.setExerciseDetailsId(workoutExercise.getExerciseDetails().getId());
        workoutExerciseDTO.setExerciseDetailsName(workoutExercise.getExerciseDetails().getName());

        if(workoutExercise.getSets() != null){
            workoutExerciseDTO.setSets(workoutExercise.getSets().stream()
                    .map(DtoConverter::toSetDTO)
                    .collect(Collectors.toList()));
        }
        return workoutExerciseDTO;
    }

    public static WorkoutExercise toWorkoutExercise(WorkoutExerciseDTO workoutExerciseDTO){
        WorkoutExercise workoutExercise = new WorkoutExercise();
        workoutExercise.setId(workoutExerciseDTO.getId());

        // Assuming Workout and ExerciseDetails entities can be fetched by their ids
        // You might need services to fetch these entities
        Workout workout = new Workout();
        workout.setId(workoutExerciseDTO.getWorkoutId());
        workoutExercise.setWorkout(workout);

        ExerciseDetails exerciseDetails = new ExerciseDetails();
        exerciseDetails.setId(workoutExerciseDTO.getExerciseDetailsId());
        workoutExercise.setExerciseDetails(exerciseDetails);

        if(workoutExerciseDTO.getSets() != null){
            workoutExercise.setSets(workoutExerciseDTO.getSets().stream()
                    .map(DtoConverter::toSet)
                    .collect(Collectors.toList()));
        }
        return workoutExercise;
    }

    public static SetDTO toSetDTO(Set set){
        SetDTO setDTO = new SetDTO();

        setDTO.setId(set.getId());
        setDTO.setReps(set.getReps());
        setDTO.setDuration(set.getDuration());
        setDTO.setWeight(set.getWeight());
        setDTO.setWorkoutExerciseId(set.getWorkoutExercise().getId());

        return setDTO;
    }

    public static Set toSet(SetDTO setDTO){
        Set set = new Set();
        set.setId(setDTO.getId());
        set.setReps(setDTO.getReps());
        set.setDuration(setDTO.getDuration());
        set.setWeight(setDTO.getWeight());

        // Assuming WorkoutExercise entity can be fetched by its id
        // You might need a service to fetch the WorkoutExercise entity
        WorkoutExercise workoutExercise = new WorkoutExercise();
        workoutExercise.setId(setDTO.getWorkoutExerciseId());
        set.setWorkoutExercise(workoutExercise);

        return set;
    }

    public static ExerciseDetailsDTO toExerciseDetailsDTO(ExerciseDetails exerciseDetails){
        ExerciseDetailsDTO exerciseDetailsDTO = new ExerciseDetailsDTO();

        exerciseDetailsDTO.setId(exerciseDetails.getId());
        exerciseDetailsDTO.setName(exerciseDetails.getName());
        exerciseDetailsDTO.setType(exerciseDetails.getType());
        exerciseDetailsDTO.setDescription(exerciseDetails.getDescription());
        exerciseDetailsDTO.setDifficulty(exerciseDetails.getDifficulty());

        if(exerciseDetails.getWorkoutExercises() != null){
            exerciseDetailsDTO.setWorkoutExercises(exerciseDetails.getWorkoutExercises()
                    .stream().map(DtoConverter::toWorkoutExerciseDTO)
                    .collect(Collectors.toList()));
        }
        return exerciseDetailsDTO;
    }

    public static ExerciseDetails toExerciseDetails(ExerciseDetailsDTO exerciseDetailsDTO){
        ExerciseDetails exerciseDetails = new ExerciseDetails();
        exerciseDetails.setId(exerciseDetailsDTO.getId());
        exerciseDetails.setName(exerciseDetailsDTO.getName());
        exerciseDetails.setType(exerciseDetailsDTO.getType());
        exerciseDetails.setDescription(exerciseDetailsDTO.getDescription());
        exerciseDetails.setDifficulty(exerciseDetailsDTO.getDifficulty());

        if(exerciseDetailsDTO.getWorkoutExercises() != null){
            exerciseDetails.setWorkoutExercises(exerciseDetailsDTO.getWorkoutExercises()
                    .stream().map(DtoConverter::toWorkoutExercise)
                    .collect(Collectors.toList()));
        }
        return exerciseDetails;
    }

    public static List<ExerciseDetailsDTO> toExerciseDetailsDTOList(List<ExerciseDetails> exerciseDetailsList) {
        return exerciseDetailsList.stream()
                .map(DtoConverter::toExerciseDetailsDTO)
                .collect(Collectors.toList());
    }

    public static List<ExerciseDetails> toExerciseDetailsList(List<ExerciseDetailsDTO> exerciseDetailsDTOList) {
        return exerciseDetailsDTOList.stream()
                .map(DtoConverter::toExerciseDetails)
                .collect(Collectors.toList());
    }

    public static List<WorkoutExerciseDTO> toWorkoutExerciseDTOList(List<WorkoutExercise> workoutExerciseList) {
        return workoutExerciseList.stream()
                .map(DtoConverter::toWorkoutExerciseDTO)
                .collect(Collectors.toList());
    }

    public static List<WorkoutExercise> toWorkoutExerciseList(List<WorkoutExerciseDTO> workoutExerciseDTOList) {
        return workoutExerciseDTOList.stream()
                .map(DtoConverter::toWorkoutExercise)
                .collect(Collectors.toList());
    }

    public static List<SetDTO> toSetDTOList(List<Set> sets) {
        if (sets != null) {
            return sets.stream()
                    .map(DtoConverter::toSetDTO)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    public static List<Set> toSetList(List<SetDTO> setDTOList) {
        if (setDTOList != null) {
            return setDTOList.stream()
                    .map(DtoConverter::toSet)
                    .collect(Collectors.toList());
        }
        return List.of();
    }
}

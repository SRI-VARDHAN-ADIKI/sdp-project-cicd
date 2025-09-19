package com.rayyan.backend.service;

import com.rayyan.backend.dto.ResponseAPI;
import com.rayyan.backend.dto.SetDTO;
import com.rayyan.backend.dto.WorkoutDTO;
import com.rayyan.backend.dto.WorkoutExerciseDTO;
import com.rayyan.backend.entity.Set;
import com.rayyan.backend.entity.User;
import com.rayyan.backend.entity.Workout;
import com.rayyan.backend.entity.WorkoutExercise;
import com.rayyan.backend.exception.OurException;
import com.rayyan.backend.repository.ExerciseDetailsRepository;
import com.rayyan.backend.repository.UserRepository;
import com.rayyan.backend.repository.WorkoutRepository;
import com.rayyan.backend.util.DtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ExerciseDetailsRepository exerciseDetailsRepository;

    public ResponseAPI createWorkout(WorkoutDTO workoutDTO) {
        ResponseAPI responseAPI = new ResponseAPI();

        try {
            // Convert WorkoutDTO to Workout
            Workout workout = DtoConverter.toWorkout(workoutDTO);

            // Validate and associate user
            User user = userRepository.findById(workoutDTO.getUserId())
                    .orElseThrow(() -> new OurException("User not found with id: " + workoutDTO.getUserId()));
            workout.setUser(user);

            // Handle and associate WorkoutExercises
            List<WorkoutExercise> workoutExercises = workoutDTO.getExercises().stream()
                    .map(weDTO -> {
                        WorkoutExercise workoutExercise = DtoConverter.toWorkoutExercise(weDTO);
                        workoutExercise.setWorkout(workout); // Set the workout reference

                        // Handle and associate Sets
                        List<Set> sets = weDTO.getSets().stream()
                                .map(setDTO -> {
                                    Set set = DtoConverter.toSet(setDTO);
                                    set.setWorkoutExercise(workoutExercise); // Set the workoutExercise reference
                                    return set;
                                })
                                .collect(Collectors.toList());

                        workoutExercise.setSets(sets); // Set the list of sets to the workoutExercise
                        return workoutExercise;
                    })
                    .collect(Collectors.toList());

            workout.setExercises(workoutExercises); // Set the list of workoutExercises to the workout

            // Save workout
            Workout savedWorkout = workoutRepository.save(workout);
            WorkoutDTO savedWorkoutDTO = DtoConverter.toWorkoutDTO(savedWorkout);

            responseAPI.setStatusCode(201);
            responseAPI.setMessage("Workout created successfully");
            responseAPI.setWorkout(savedWorkoutDTO);

        } catch (OurException e) {
            responseAPI.setStatusCode(404);
            responseAPI.setMessage(e.getMessage());

        } catch (Exception e) {
            responseAPI.setStatusCode(500);
            responseAPI.setMessage("Error creating workout: " + e.getMessage());
        }

        return responseAPI;
    }



    public ResponseAPI getWorkout(Long workoutId) {
        ResponseAPI responseAPI = new ResponseAPI();

        try {
            Workout workout = workoutRepository.findById(workoutId)
                    .orElseThrow(() -> new OurException("Workout not found with id: " + workoutId));
            WorkoutDTO workoutDTO = DtoConverter.toWorkoutDTO(workout);

            responseAPI.setStatusCode(200);
            responseAPI.setMessage("Workout retrieved successfully");
            responseAPI.setWorkout(workoutDTO);

        } catch (OurException e) {
            responseAPI.setStatusCode(404);
            responseAPI.setMessage(e.getMessage());

        } catch (Exception e) {
            responseAPI.setStatusCode(500);
            responseAPI.setMessage("Error retrieving workout: " + e.getMessage());
        }

        return responseAPI;
    }

    public ResponseAPI updateWorkout(Long workoutId, WorkoutDTO workoutDTO) {
        ResponseAPI responseAPI = new ResponseAPI();

        try {
            // Fetch the existing workout
            Workout existingWorkout = workoutRepository.findById(workoutId)
                    .orElseThrow(() -> new OurException("Workout not found with id: " + workoutId));

            // Convert WorkoutDTO to Workout
            Workout updatedWorkout = DtoConverter.toWorkout(workoutDTO);
            updatedWorkout.setId(existingWorkout.getId()); // Ensure the ID remains the same

            // Validate and associate user
            User user = userRepository.findById(workoutDTO.getUserId())
                    .orElseThrow(() -> new OurException("User not found with id: " + workoutDTO.getUserId()));
            updatedWorkout.setUser(user);

            // Handle and associate WorkoutExercises
            List<WorkoutExercise> updatedWorkoutExercises = workoutDTO.getExercises().stream()
                    .map(weDTO -> {
                        WorkoutExercise workoutExercise = DtoConverter.toWorkoutExercise(weDTO);
                        workoutExercise.setWorkout(updatedWorkout); // Set the workout reference

                        // Handle and associate Sets
                        List<Set> sets = weDTO.getSets().stream()
                                .map(setDTO -> {
                                    Set set = DtoConverter.toSet(setDTO);
                                    set.setWorkoutExercise(workoutExercise); // Set the workoutExercise reference
                                    return set;
                                })
                                .collect(Collectors.toList());

                        workoutExercise.setSets(sets); // Set the list of sets to the workoutExercise
                        return workoutExercise;
                    })
                    .collect(Collectors.toList());

            updatedWorkout.setExercises(updatedWorkoutExercises); // Set the list of workoutExercises to the workout

            // Update workout
            Workout savedWorkout = workoutRepository.save(updatedWorkout);
            WorkoutDTO savedWorkoutDTO = DtoConverter.toWorkoutDTO(savedWorkout);

            responseAPI.setStatusCode(200);
            responseAPI.setMessage("Workout updated successfully");
            responseAPI.setWorkout(savedWorkoutDTO);

        } catch (OurException e) {
            responseAPI.setStatusCode(404);
            responseAPI.setMessage(e.getMessage());

        } catch (Exception e) {
            responseAPI.setStatusCode(500);
            responseAPI.setMessage("Error updating workout: " + e.getMessage());
        }

        return responseAPI;
    }


    public ResponseAPI deleteWorkout(Long workoutId) {
        ResponseAPI responseAPI = new ResponseAPI();

        try {
            Workout workout = workoutRepository.findById(workoutId)
                    .orElseThrow(() -> new OurException("Workout not found with id: " + workoutId));

            workoutRepository.delete(workout);

            responseAPI.setStatusCode(200);
            responseAPI.setMessage("Workout deleted successfully");

        } catch (OurException e) {
            responseAPI.setStatusCode(404);
            responseAPI.setMessage(e.getMessage());

        } catch (Exception e) {
            responseAPI.setStatusCode(500);
            responseAPI.setMessage("Error deleting workout: " + e.getMessage());
        }

        return responseAPI;
    }

    public ResponseAPI getWorkoutsByUserAndExercise(Long userId, Long exerciseDetailsId) {
        ResponseAPI responseAPI = new ResponseAPI();

        try {
            List<Workout> workouts = workoutRepository.findByUserIdAndExerciseDetailsId(userId, exerciseDetailsId);
            if (workouts.isEmpty()) {
                throw new OurException("No workouts found for user with id: " + userId + " and exercise with id: " + exerciseDetailsId);
            }

            List<WorkoutDTO> workoutDTOs = workouts.stream()
                    .map(DtoConverter::toWorkoutDTO)
                    .collect(Collectors.toList());

            responseAPI.setStatusCode(200);
            responseAPI.setMessage("Workouts retrieved successfully");
            responseAPI.setWorkoutList(workoutDTOs);

        } catch (OurException e) {
            responseAPI.setStatusCode(404);
            responseAPI.setMessage(e.getMessage());

        } catch (Exception e) {
            responseAPI.setStatusCode(500);
            responseAPI.setMessage("Error retrieving workouts: " + e.getMessage());
        }

        return responseAPI;
    }

    public ResponseAPI getWorkoutHistoryWithNames(Long userId) {
        ResponseAPI responseAPI = new ResponseAPI();

        try {
            // Fetch the user entity by userId
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new OurException("User not found with id: " + userId));

            // Fetch the workouts for the user
            List<Workout> workouts = workoutRepository.findByUser(user);

            // Convert workouts to DTOs
            List<WorkoutDTO> workoutDTOs = workouts.stream()
                    .map(workout -> {
                        WorkoutDTO workoutDTO = DtoConverter.toWorkoutDTO(workout);

                        // Convert WorkoutExercise entities to DTOs with names
                        List<WorkoutExerciseDTO> workoutExerciseDTOs = workout.getExercises().stream()
                                .map(DtoConverter::toWorkoutExerciseDTO)
                                .collect(Collectors.toList());

                        workoutDTO.setExercises(workoutExerciseDTOs);
                        return workoutDTO;
                    })
                    .collect(Collectors.toList());

            responseAPI.setStatusCode(200);
            responseAPI.setMessage("Workout history retrieved successfully");
            responseAPI.setWorkoutList(workoutDTOs);
        } catch (Exception e) {
            responseAPI.setStatusCode(500);
            responseAPI.setMessage("Error retrieving workout history: " + e.getMessage());
        }

        return responseAPI;
    }


}

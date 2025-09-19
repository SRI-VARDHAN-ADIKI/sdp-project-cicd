package com.rayyan.backend.service;

import com.rayyan.backend.dto.ResponseAPI;
import com.rayyan.backend.entity.ExerciseDetails;
import com.rayyan.backend.entity.Workout;
import com.rayyan.backend.entity.WorkoutExercise;
import com.rayyan.backend.exception.OurException;
import com.rayyan.backend.repository.ExerciseDetailsRepository;
import com.rayyan.backend.repository.WorkoutExerciseRepository;
import com.rayyan.backend.repository.WorkoutRepository;
import com.rayyan.backend.util.DtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutExerciseService {

    @Autowired
    private WorkoutExerciseRepository workoutExerciseRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private ExerciseDetailsRepository exerciseDetailsRepository;

    public ResponseAPI createWorkoutExercise(WorkoutExercise workoutExercise) {
        ResponseAPI responseAPI = new ResponseAPI();

        try {
            Workout workout = workoutRepository.findById(workoutExercise.getWorkout().getId())
                    .orElseThrow(() -> new OurException("Workout not found with id: " + workoutExercise.getWorkout().getId()));

            ExerciseDetails exerciseDetails = exerciseDetailsRepository.findById(workoutExercise.getExerciseDetails().getId())
                    .orElseThrow(() -> new OurException("ExerciseDetails not found with id: " + workoutExercise.getExerciseDetails().getId()));

            workoutExercise.setWorkout(workout);
            workoutExercise.setExerciseDetails(exerciseDetails);

            WorkoutExercise savedWorkoutExercise = workoutExerciseRepository.save(workoutExercise);
            responseAPI.setStatusCode(201);
            responseAPI.setMessage("WorkoutExercise created successfully");
            responseAPI.setWorkoutExercise(DtoConverter.toWorkoutExerciseDTO(savedWorkoutExercise));
        } catch (Exception e) {
            responseAPI.setStatusCode(500);
            responseAPI.setMessage("Error creating WorkoutExercise: " + e.getMessage());
        }

        return responseAPI;
    }

    public ResponseAPI getWorkoutExercise(Long id) {
        ResponseAPI responseAPI = new ResponseAPI();

        try {
            WorkoutExercise workoutExercise = workoutExerciseRepository.findById(id)
                    .orElseThrow(() -> new OurException("WorkoutExercise not found with id: " + id));
            responseAPI.setStatusCode(200);
            responseAPI.setMessage("WorkoutExercise retrieved successfully");
            responseAPI.setWorkoutExercise(DtoConverter.toWorkoutExerciseDTO(workoutExercise));
        } catch (OurException e) {
            responseAPI.setStatusCode(404);
            responseAPI.setMessage(e.getMessage());
        } catch (Exception e) {
            responseAPI.setStatusCode(500);
            responseAPI.setMessage("Error retrieving WorkoutExercise: " + e.getMessage());
        }

        return responseAPI;
    }

    public ResponseAPI updateWorkoutExercise(Long id, WorkoutExercise updatedWorkoutExercise) {
        ResponseAPI responseAPI = new ResponseAPI();

        try {
            WorkoutExercise workoutExercise = workoutExerciseRepository.findById(id)
                    .orElseThrow(() -> new OurException("WorkoutExercise not found with id: " + id));

            Workout workout = workoutRepository.findById(updatedWorkoutExercise.getWorkout().getId())
                    .orElseThrow(() -> new OurException("Workout not found with id: " + updatedWorkoutExercise.getWorkout().getId()));

            ExerciseDetails exerciseDetails = exerciseDetailsRepository.findById(updatedWorkoutExercise.getExerciseDetails().getId())
                    .orElseThrow(() -> new OurException("ExerciseDetails not found with id: " + updatedWorkoutExercise.getExerciseDetails().getId()));

            workoutExercise.setWorkout(workout);
            workoutExercise.setExerciseDetails(exerciseDetails);
            workoutExercise.setSets(updatedWorkoutExercise.getSets());

            WorkoutExercise savedWorkoutExercise = workoutExerciseRepository.save(workoutExercise);
            responseAPI.setStatusCode(200);
            responseAPI.setMessage("WorkoutExercise updated successfully");
            responseAPI.setWorkoutExercise(DtoConverter.toWorkoutExerciseDTO(savedWorkoutExercise));
        } catch (OurException e) {
            responseAPI.setStatusCode(404);
            responseAPI.setMessage(e.getMessage());
        } catch (Exception e) {
            responseAPI.setStatusCode(500);
            responseAPI.setMessage("Error updating WorkoutExercise: " + e.getMessage());
        }

        return responseAPI;
    }

    public ResponseAPI deleteWorkoutExercise(Long id) {
        ResponseAPI responseAPI = new ResponseAPI();

        try {
            WorkoutExercise workoutExercise = workoutExerciseRepository.findById(id)
                    .orElseThrow(() -> new OurException("WorkoutExercise not found with id: " + id));
            workoutExerciseRepository.delete(workoutExercise);
            responseAPI.setStatusCode(200);
            responseAPI.setMessage("WorkoutExercise deleted successfully");
        } catch (OurException e) {
            responseAPI.setStatusCode(404);
            responseAPI.setMessage(e.getMessage());
        } catch (Exception e) {
            responseAPI.setStatusCode(500);
            responseAPI.setMessage("Error deleting WorkoutExercise: " + e.getMessage());
        }

        return responseAPI;
    }

    public ResponseAPI getAllWorkoutExercises() {
        ResponseAPI responseAPI = new ResponseAPI();

        try {
            List<WorkoutExercise> workoutExerciseList = workoutExerciseRepository.findAll();
            responseAPI.setStatusCode(200);
            responseAPI.setMessage("WorkoutExercises retrieved successfully");
            responseAPI.setWorkoutExerciseList(DtoConverter.toWorkoutExerciseDTOList(workoutExerciseList));
        } catch (Exception e) {
            responseAPI.setStatusCode(500);
            responseAPI.setMessage("Error retrieving WorkoutExercises: " + e.getMessage());
        }

        return responseAPI;
    }
}

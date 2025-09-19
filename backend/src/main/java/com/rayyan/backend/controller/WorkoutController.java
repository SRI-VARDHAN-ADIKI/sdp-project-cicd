package com.rayyan.backend.controller;

import com.rayyan.backend.dto.ResponseAPI;
import com.rayyan.backend.dto.WorkoutDTO;
import com.rayyan.backend.entity.Workout;
import com.rayyan.backend.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workouts")
//@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
public class WorkoutController {

    @Autowired
    private WorkoutService workoutService;

    @PostMapping
    public ResponseEntity<ResponseAPI> createWorkout(@RequestBody WorkoutDTO workoutDTO) {
        ResponseAPI responseAPI = workoutService.createWorkout(workoutDTO);
        return new ResponseEntity<>(responseAPI, HttpStatus.valueOf(responseAPI.getStatusCode()));
    }

    @GetMapping("/{workoutId}")
    public ResponseEntity<ResponseAPI> getWorkout(@PathVariable Long workoutId) {
        ResponseAPI responseAPI = workoutService.getWorkout(workoutId);
        return new ResponseEntity<>(responseAPI, HttpStatus.valueOf(responseAPI.getStatusCode()));
    }

    @PutMapping("/update/{workoutId}")
    public ResponseEntity<ResponseAPI> updateWorkout(
            @PathVariable Long workoutId,
            @RequestBody WorkoutDTO workoutDTO) {

        ResponseAPI responseAPI = workoutService.updateWorkout(workoutId, workoutDTO);
        return new ResponseEntity<>(responseAPI, HttpStatus.valueOf(responseAPI.getStatusCode()));
    }

    @DeleteMapping("/{workoutId}")
    public ResponseEntity<ResponseAPI> deleteWorkout(@PathVariable Long workoutId) {
        ResponseAPI responseAPI = workoutService.deleteWorkout(workoutId);
        return new ResponseEntity<>(responseAPI, HttpStatus.valueOf(responseAPI.getStatusCode()));
    }

    @GetMapping("/user/{userId}/exercise/{exerciseDetailsId}")
    public ResponseEntity<ResponseAPI> getWorkoutsByUserAndExercise(
            @PathVariable Long userId,
            @PathVariable Long exerciseDetailsId) {

        ResponseAPI response = workoutService.getWorkoutsByUserAndExercise(userId, exerciseDetailsId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping("/users/{userId}/history-with-names")
    public ResponseEntity<ResponseAPI> getWorkoutHistoryWithNames(@PathVariable Long userId) {
        ResponseAPI response = workoutService.getWorkoutHistoryWithNames(userId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
}

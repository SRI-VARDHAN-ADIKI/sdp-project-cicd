package com.rayyan.backend.controller;

import com.rayyan.backend.dto.ResponseAPI;
import com.rayyan.backend.entity.WorkoutExercise;
import com.rayyan.backend.service.WorkoutExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workout-exercises")
public class WorkoutExerciseController {

    @Autowired
    private WorkoutExerciseService workoutExerciseService;

    @PostMapping
    public ResponseEntity<ResponseAPI> createWorkoutExercise(@RequestBody WorkoutExercise workoutExercise) {
        ResponseAPI responseAPI = workoutExerciseService.createWorkoutExercise(workoutExercise);
        return new ResponseEntity<>(responseAPI, HttpStatus.valueOf(responseAPI.getStatusCode()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseAPI> getWorkoutExercise(@PathVariable Long id) {
        ResponseAPI responseAPI = workoutExerciseService.getWorkoutExercise(id);
        return new ResponseEntity<>(responseAPI, HttpStatus.valueOf(responseAPI.getStatusCode()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseAPI> updateWorkoutExercise(@PathVariable Long id, @RequestBody WorkoutExercise updatedWorkoutExercise) {
        ResponseAPI responseAPI = workoutExerciseService.updateWorkoutExercise(id, updatedWorkoutExercise);
        return new ResponseEntity<>(responseAPI, HttpStatus.valueOf(responseAPI.getStatusCode()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseAPI> deleteWorkoutExercise(@PathVariable Long id) {
        ResponseAPI responseAPI = workoutExerciseService.deleteWorkoutExercise(id);
        return new ResponseEntity<>(responseAPI, HttpStatus.valueOf(responseAPI.getStatusCode()));
    }

    @GetMapping
    public ResponseEntity<ResponseAPI> getAllWorkoutExercises() {
        ResponseAPI responseAPI = workoutExerciseService.getAllWorkoutExercises();
        return new ResponseEntity<>(responseAPI, HttpStatus.valueOf(responseAPI.getStatusCode()));
    }
}
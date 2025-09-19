package com.rayyan.backend.controller;

import com.rayyan.backend.dto.ResponseAPI;
import com.rayyan.backend.entity.ExerciseDetails;
import com.rayyan.backend.service.ExerciseDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exercise-details")
public class ExerciseDetailsController {

    @Autowired
    private ExerciseDetailsService exerciseDetailsService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseAPI> createExerciseDetails(@RequestBody ExerciseDetails exerciseDetails) {
        ResponseAPI responseAPI = exerciseDetailsService.createExerciseDetails(exerciseDetails);
        return new ResponseEntity<>(responseAPI, HttpStatus.valueOf(responseAPI.getStatusCode()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseAPI> getExerciseDetails(@PathVariable Long id) {
        ResponseAPI responseAPI = exerciseDetailsService.getExerciseDetails(id);
        return new ResponseEntity<>(responseAPI, HttpStatus.valueOf(responseAPI.getStatusCode()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseAPI> updateExerciseDetails(@PathVariable Long id, @RequestBody ExerciseDetails updatedExerciseDetails) {
        ResponseAPI responseAPI = exerciseDetailsService.updateExerciseDetails(id, updatedExerciseDetails);
        return new ResponseEntity<>(responseAPI, HttpStatus.valueOf(responseAPI.getStatusCode()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseAPI> deleteExerciseDetails(@PathVariable Long id) {
        ResponseAPI responseAPI = exerciseDetailsService.deleteExerciseDetails(id);
        return new ResponseEntity<>(responseAPI, HttpStatus.valueOf(responseAPI.getStatusCode()));
    }

    @GetMapping
    public ResponseEntity<ResponseAPI> getAllExerciseDetails() {
        ResponseAPI responseAPI = exerciseDetailsService.getAllExerciseDetails();
        return new ResponseEntity<>(responseAPI, HttpStatus.valueOf(responseAPI.getStatusCode()));
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseAPI> searchExercises(@RequestParam String query) {
        ResponseAPI responseAPI = exerciseDetailsService.searchExercises(query);
        return new ResponseEntity<>(responseAPI, HttpStatus.valueOf(responseAPI.getStatusCode()));
    }

}
package com.rayyan.backend.controller;

import com.rayyan.backend.dto.ResponseAPI;
import com.rayyan.backend.entity.Set;
import com.rayyan.backend.service.SetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sets")
public class SetController {

    @Autowired
    private SetService setService;

    @PostMapping
    public ResponseAPI createSet(@RequestBody Set set) {
        return setService.createSet(set);
    }

    @GetMapping("/{id}")
    public ResponseAPI getSet(@PathVariable Long id) {
        return setService.getSet(id);
    }

    @PutMapping("/{id}")
    public ResponseAPI updateSet(@PathVariable Long id, @RequestBody Set set) {
        return setService.updateSet(id, set);
    }

    @DeleteMapping("/{id}")
    public ResponseAPI deleteSet(@PathVariable Long id) {
        return setService.deleteSet(id);
    }

    @GetMapping
    public ResponseAPI getAllSets() {
        return setService.getAllSets();
    }


    @GetMapping("/workout-exercise/{workoutExerciseId}")
    public ResponseAPI getSetsByWorkoutExerciseId(@PathVariable Long workoutExerciseId) {
        return setService.getSetsByWorkoutExerciseId(workoutExerciseId);
    }
}

package com.rayyan.backend.service;
import com.rayyan.backend.dto.ResponseAPI;
import com.rayyan.backend.dto.SetDTO;
import com.rayyan.backend.entity.Set;
import com.rayyan.backend.entity.WorkoutExercise;
import com.rayyan.backend.exception.OurException;
import com.rayyan.backend.repository.SetRepository;
import com.rayyan.backend.repository.WorkoutExerciseRepository;
import com.rayyan.backend.util.DtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetService {

    @Autowired
    private SetRepository setRepository;

    public ResponseAPI createSet(Set set) {
        ResponseAPI responseAPI = new ResponseAPI();

        try {
            Set savedSet = setRepository.save(set);
            responseAPI.setStatusCode(201);
            responseAPI.setMessage("Set created successfully");
            responseAPI.setSet(DtoConverter.toSetDTO(savedSet));
        } catch (Exception e) {
            responseAPI.setStatusCode(500);
            responseAPI.setMessage("Error creating set: " + e.getMessage());
        }

        return responseAPI;
    }

    public ResponseAPI getSet(Long id) {
        ResponseAPI responseAPI = new ResponseAPI();

        try {
            Set set = setRepository.findById(id)
                    .orElseThrow(() -> new OurException("Set not found with id: " + id));
            responseAPI.setStatusCode(200);
            responseAPI.setMessage("Set retrieved successfully");
            responseAPI.setSet(DtoConverter.toSetDTO(set));
        } catch (OurException e) {
            responseAPI.setStatusCode(404);
            responseAPI.setMessage(e.getMessage());
        } catch (Exception e) {
            responseAPI.setStatusCode(500);
            responseAPI.setMessage("Error retrieving set: " + e.getMessage());
        }

        return responseAPI;
    }

    public ResponseAPI updateSet(Long id, Set updatedSet) {
        ResponseAPI responseAPI = new ResponseAPI();

        try {
            Set set = setRepository.findById(id)
                    .orElseThrow(() -> new OurException("Set not found with id: " + id));

            set.setReps(updatedSet.getReps());
            set.setDuration(updatedSet.getDuration());
            set.setWeight(updatedSet.getWeight());

            Set savedSet = setRepository.save(set);
            responseAPI.setStatusCode(200);
            responseAPI.setMessage("Set updated successfully");
            responseAPI.setSet(DtoConverter.toSetDTO(savedSet));
        } catch (OurException e) {
            responseAPI.setStatusCode(404);
            responseAPI.setMessage(e.getMessage());
        } catch (Exception e) {
            responseAPI.setStatusCode(500);
            responseAPI.setMessage("Error updating set: " + e.getMessage());
        }

        return responseAPI;
    }

    public ResponseAPI deleteSet(Long id) {
        ResponseAPI responseAPI = new ResponseAPI();

        try {
            Set set = setRepository.findById(id)
                    .orElseThrow(() -> new OurException("Set not found with id: " + id));
            setRepository.delete(set);
            responseAPI.setStatusCode(200);
            responseAPI.setMessage("Set deleted successfully");
        } catch (OurException e) {
            responseAPI.setStatusCode(404);
            responseAPI.setMessage(e.getMessage());
        } catch (Exception e) {
            responseAPI.setStatusCode(500);
            responseAPI.setMessage("Error deleting set: " + e.getMessage());
        }

        return responseAPI;
    }

    public ResponseAPI getAllSets() {
        ResponseAPI responseAPI = new ResponseAPI();

        try {
            List<Set> sets = setRepository.findAll();
            responseAPI.setStatusCode(200);
            responseAPI.setMessage("Sets retrieved successfully");
            responseAPI.setSetList(DtoConverter.toSetDTOList(sets));
        } catch (Exception e) {
            responseAPI.setStatusCode(500);
            responseAPI.setMessage("Error retrieving sets: " + e.getMessage());
        }

        return responseAPI;
    }

    @Autowired
    private WorkoutExerciseRepository workoutExerciseRepository;

    public ResponseAPI getSetsByWorkoutExerciseId(Long workoutExerciseId) {
        ResponseAPI responseAPI = new ResponseAPI();

        try {
            WorkoutExercise workoutExercise = workoutExerciseRepository.findById(workoutExerciseId)
                    .orElseThrow(() -> new OurException("WorkoutExercise not found with id: " + workoutExerciseId));

            List<Set> sets = setRepository.findByWorkoutExercise(workoutExercise);

            responseAPI.setStatusCode(200);
            responseAPI.setMessage("Sets retrieved successfully");
            responseAPI.setSetList(DtoConverter.toSetDTOList(sets));

        } catch (OurException e) {
            responseAPI.setStatusCode(404);
            responseAPI.setMessage(e.getMessage());
        } catch (Exception e) {
            responseAPI.setStatusCode(500);
            responseAPI.setMessage("Error retrieving sets: " + e.getMessage());
        }

        return responseAPI;
    }
}

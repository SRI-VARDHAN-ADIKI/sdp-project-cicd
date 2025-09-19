package com.rayyan.backend.controller;

import com.rayyan.backend.dto.LoginRequest;
import com.rayyan.backend.dto.ResponseAPI;
import com.rayyan.backend.entity.User;
import com.rayyan.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<ResponseAPI> register(@RequestBody User user) {
        ResponseAPI response = userService.register(user);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    // Login a user
    @PostMapping("/login")
    public ResponseEntity<ResponseAPI> login(@RequestBody LoginRequest loginRequest) {
        ResponseAPI response = userService.login(loginRequest);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    // Get a user by ID
    @GetMapping("/{userId}")
    public ResponseEntity<ResponseAPI> getUser(@PathVariable Long userId) {
        ResponseAPI response = userService.getUser(userId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    // Update a user by ID
    @PutMapping("/{userId}")
    public ResponseEntity<ResponseAPI> updateUser(@PathVariable Long userId, @RequestBody User updatedUserDetails) {
        ResponseAPI response = userService.updateUser(userId, updatedUserDetails);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    // Get all users
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseAPI> getAllUsers() {
        ResponseAPI response = userService.getAllUsers();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    // Delete a user by ID
    @DeleteMapping("/{userId}")
    public ResponseEntity<ResponseAPI> deleteUser(@PathVariable Long userId) {
        ResponseAPI response = userService.deleteUser(userId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    // Get workout history for a user
    @GetMapping("/{userId}/workouts")
    public ResponseEntity<ResponseAPI> getWorkoutHistory(@PathVariable Long userId) {
        ResponseAPI response = userService.getWorkoutHistory(userId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    //Get Logged in Profile
    @GetMapping("/logged-in-user")
    public ResponseEntity<ResponseAPI> getLoggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        ResponseAPI response = userService.getLoggedInUser(email);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
}


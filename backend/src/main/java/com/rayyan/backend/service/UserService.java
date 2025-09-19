package com.rayyan.backend.service;


import com.rayyan.backend.dto.LoginRequest;
import com.rayyan.backend.dto.ResponseAPI;
import com.rayyan.backend.dto.UserDTO;
import com.rayyan.backend.dto.WorkoutDTO;
import com.rayyan.backend.entity.User;
import com.rayyan.backend.entity.Workout;
import com.rayyan.backend.exception.OurException;
import com.rayyan.backend.repository.UserRepository;
import com.rayyan.backend.repository.WorkoutRepository;
import com.rayyan.backend.security.JWTUtils;
import com.rayyan.backend.util.DtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private WorkoutRepository workoutRepository;



    //Saved a User in DB, but Responded with a UserDTO
    public ResponseAPI register(User user) {
        ResponseAPI responseAPI = new ResponseAPI();
        try {
            if (user.getRole() == null || user.getRole().isBlank()) {
                user.setRole("USER");
            }
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new OurException("User with email '" + user.getEmail() + "' already exists");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user);
            UserDTO userDTO = DtoConverter.toUserDTO(user);
            responseAPI.setStatusCode(200);
            responseAPI.setUser(userDTO);
        } catch (OurException e) {
            responseAPI.setStatusCode(400);
            responseAPI.setMessage(e.getMessage());
        } catch (Exception e) {
            responseAPI.setStatusCode(500);
            responseAPI.setMessage("Error Occurred During User Registration " + e.getMessage());

        }
        return responseAPI;
    }

    public ResponseAPI login(LoginRequest loginRequest) {

        ResponseAPI responseAPI = new ResponseAPI();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new OurException("User not found with this email."));

            var token = jwtUtils.generateToken(user);
            responseAPI.setStatusCode(200);
            responseAPI.setToken(token);
            responseAPI.setRole(user.getRole());
            responseAPI.setExpirationTime("7 Days");
            responseAPI.setMessage("successful");

        } catch (OurException e) {
            responseAPI.setStatusCode(404);
            responseAPI.setMessage(e.getMessage());

        } catch (Exception e) {

            responseAPI.setStatusCode(500);
            responseAPI.setMessage("Error during User login! " + e.getMessage());
        }
        return responseAPI;
    }

    public ResponseAPI getUser(Long userId){
        ResponseAPI responseAPI = new ResponseAPI();

        try{
            User user = userRepository.findById(userId).orElseThrow(()-> new OurException("User not found with id: " + userId));
            UserDTO userDTO = DtoConverter.toUserDTO(user);
            responseAPI.setStatusCode(200);
            responseAPI.setMessage("successful");
            responseAPI.setUser(userDTO);

        } catch (OurException e) {
            responseAPI.setStatusCode(404);
            responseAPI.setMessage(e.getMessage());

        } catch (Exception e) {

            responseAPI.setStatusCode(500);
            responseAPI.setMessage("Error getting all users " + e.getMessage());
        }
        return responseAPI;
    }

    public ResponseAPI getAllUsers() {
        ResponseAPI responseAPI = new ResponseAPI();

        try {
            List<User> users = userRepository.findAll();
            List<UserDTO> userDTOs = users.stream().map(DtoConverter::toUserDTO).collect(Collectors.toList());

            responseAPI.setStatusCode(200);
            responseAPI.setMessage("Users retrieved successfully");
            responseAPI.setUserList(userDTOs);

        } catch (Exception e) {
            responseAPI.setStatusCode(500);
            responseAPI.setMessage("Error getting all users: " + e.getMessage());
        }

        return responseAPI;
    }


    public ResponseAPI updateUser(Long userId, User updatedUserDetails) {
        ResponseAPI responseAPI = new ResponseAPI();

        try {

            User user = userRepository.findById(userId).orElseThrow(() -> new OurException("User not found with id: " + userId));
            // Update user details
            user.setName(updatedUserDetails.getName());
            user.setEmail(updatedUserDetails.getEmail());
            if (updatedUserDetails.getPassword() != null && !updatedUserDetails.getPassword().isBlank()) {
                user.setPassword(passwordEncoder.encode(updatedUserDetails.getPassword()));
            }

            User updatedUser = userRepository.save(user);
            UserDTO userDTO = DtoConverter.toUserDTO(updatedUser);

            responseAPI.setStatusCode(200);
            responseAPI.setMessage("User updated successfully");
            responseAPI.setUser(userDTO);

        } catch (OurException e) {
            responseAPI.setStatusCode(404);
            responseAPI.setMessage(e.getMessage());

        } catch (Exception e) {
            responseAPI.setStatusCode(500);
            responseAPI.setMessage("Error updating user: " + e.getMessage());
        }

        return responseAPI;
    }

    public ResponseAPI deleteUser(Long userId) {
        ResponseAPI responseAPI = new ResponseAPI();

        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new OurException("User not found with id: " + userId));
            userRepository.delete(user);

            responseAPI.setStatusCode(200);
            responseAPI.setMessage("User deleted successfully");

        } catch (OurException e) {
            responseAPI.setStatusCode(404);
            responseAPI.setMessage(e.getMessage());

        } catch (Exception e) {
            responseAPI.setStatusCode(500);
            responseAPI.setMessage("Error deleting user: " + e.getMessage());
        }

        return responseAPI;
    }

    public ResponseAPI getWorkoutHistory(Long userId) {
        ResponseAPI responseAPI = new ResponseAPI();

        try {
            // Find the user by ID
            User user = userRepository.findById(userId).orElseThrow(() -> new OurException("User not found with id: " + userId));

            // Get the workout history for the user
            List<Workout> workouts = workoutRepository.findByUser(user);
            List<WorkoutDTO> workoutDTOs = workouts.stream().map(DtoConverter::toWorkoutDTO).collect(Collectors.toList());


            responseAPI.setStatusCode(200);
            responseAPI.setMessage("Workout history retrieved successfully");
            responseAPI.setWorkoutList(workoutDTOs);

        } catch (OurException e) {
            responseAPI.setStatusCode(404);
            responseAPI.setMessage(e.getMessage());

        } catch (Exception e) {
            responseAPI.setStatusCode(500);
            responseAPI.setMessage("Error retrieving workout history: " + e.getMessage());
        }

        return responseAPI;
    }

    public ResponseAPI getLoggedInUser(String email){
        ResponseAPI responseAPI = new ResponseAPI();

        try{
            User user = userRepository.findByEmail(email).orElseThrow(() -> new OurException("User Not Found"));
            UserDTO userDTO = DtoConverter.toUserDTO(user);
            responseAPI.setStatusCode(200);
            responseAPI.setMessage("User retrieved successfully!");
            responseAPI.setUser(userDTO);
        }
        catch (OurException e){
            responseAPI.setStatusCode(404);
            responseAPI.setMessage(e.getMessage());
        }
        catch (Exception e){
            responseAPI.setStatusCode(500);
            responseAPI.setMessage("Error getting user. " + e.getMessage());
        }
        return responseAPI;
    }


}

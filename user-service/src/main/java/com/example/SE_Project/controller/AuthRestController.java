package com.example.SE_Project.controller;

import com.example.SE_Project.model.User;
import com.example.SE_Project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AuthRestController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerParticipant(@RequestBody User user) {
        if (userService.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already registered");
        }
        user.setType("Participant");
        userService.registerUser(user);
        return ResponseEntity.ok("Participant registered successfully");
    }

    @PostMapping("/register-EO")
    public ResponseEntity<String> registerOrganizer(@RequestBody User user) {
        if (userService.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already registered");
        }
        user.setType("EventOrganizer");
        userService.registerUser(user);
        return ResponseEntity.ok("Organizer registered successfully");
    }


    @PostMapping("/login")
    public ResponseEntity<Map <String, Object>> login(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean isValid = userService.validateLogin(user.getEmail(), user.getPassword());
            if (isValid) {
                User loggedInUser = userService.findByEmail(user.getEmail());
                if (loggedInUser != null) {
                    response.put("email", loggedInUser.getEmail());
                    response.put("type", loggedInUser.getType());
                    return ResponseEntity.ok(response);
                }
            }
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Invalid email or password"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Collections.singletonMap("message", "Login failed: " + e.getMessage()));
        }
    }


    @GetMapping("/users/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);
        if (user != null) return ResponseEntity.ok(user);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user != null) return ResponseEntity.ok(user);
        return ResponseEntity.notFound().build();
    }



    @GetMapping("/users/email")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}

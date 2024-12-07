package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User newUser = userService.createUser(user);
        return ResponseEntity.status(201).body(newUser);
    }
    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User loginRequest) {
        User authenticatedUser = userService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok(authenticatedUser);
    }
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/listuser")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/confirmdeliv")
    public ResponseEntity<User> ConfirmDeliv(@RequestBody User user) {
        User newUser = userService.createUser(user);
        return ResponseEntity.status(201).body(newUser);
    }
}

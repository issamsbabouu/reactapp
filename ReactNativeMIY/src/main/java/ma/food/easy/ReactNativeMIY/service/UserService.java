package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        // Logique pour enregistrer un nouvel utilisateur
        return userRepository.save(user);
    }

    public User authenticateUser(String email, String password) {
        // Authentification de l'utilisateur par email et mot de passe
        Optional<User> optionalUser = userRepository.findByEmailAndPassword(email, password);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new RuntimeException("Invalid credentials"); // Vous pouvez gérer l'erreur selon vos besoins
        }
    }

    public User updateUser(int id, User userDetails) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        return userRepository.save(user);
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

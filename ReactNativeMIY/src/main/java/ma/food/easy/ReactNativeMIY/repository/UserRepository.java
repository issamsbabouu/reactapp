package com.example.repository;

import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // Méthode personnalisée pour trouver un utilisateur par email et mot de passe
    Optional<User> findByEmailAndPassword(String email, String password);
}

package main.java.com.example.repository;

import main.java.com.example.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    // Méthode pour vérifier si un email existe déjà
    boolean existsByEmail(String email);
}

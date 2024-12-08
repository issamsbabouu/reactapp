package com.example.demo.repository;
import com.example.demo.modele.commande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<commande, Long> {
}
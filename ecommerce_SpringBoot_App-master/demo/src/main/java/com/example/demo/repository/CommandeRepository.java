package com.example.demo.repository;

import com.example.demo.modele.commande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandeRepository extends JpaRepository<commande, Long> {
    List<commande> findByPanierId(int panierId);
    List<commande> findByPanier_CompteId(Long compteId);
}

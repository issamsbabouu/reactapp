package com.example.demo.repository;

import com.example.demo.modele.Panier;
import com.example.demo.modele.comptes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PanierRepository extends JpaRepository<Panier, Long> {
    Panier findByCompteId(Long compteId);
    
}

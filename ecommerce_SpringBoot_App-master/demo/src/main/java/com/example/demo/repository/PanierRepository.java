package com.example.demo.repository;
import com.example.demo.modele.Panier;
import com.example.demo.modele.comptes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface  PanierRepository extends JpaRepository<Panier, Long> {
    List<Panier> findByCompteUsername(String username);
    List<Panier> findAllByIdInAndCompteUsername(List<Integer> panierIds, String username);
    Panier findByCommandesId(int productId);
    Optional<Panier> findByCompteId(int compteId);
}

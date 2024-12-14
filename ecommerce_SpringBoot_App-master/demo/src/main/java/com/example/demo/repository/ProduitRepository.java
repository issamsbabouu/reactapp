package com.example.demo.repository;

import com.example.demo.modele.categorie;
import com.example.demo.modele.produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProduitRepository extends JpaRepository<produit, Long> {
    @Query("SELECT p FROM produit p JOIN FETCH p.categorie")
    List<produit> findAllWithCategories();
}

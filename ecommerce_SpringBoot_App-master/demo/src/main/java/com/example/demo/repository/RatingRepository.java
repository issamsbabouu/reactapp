package com.example.demo.repository;
import com.example.demo.modele.Panier;
import com.example.demo.modele.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    @Query("SELECT AVG(r.ratingValue) FROM Rating r WHERE r.produit.id = :productId")
    Integer findAverageRatingByProduitId(@Param("productId") int productId);
    List<Rating> findByProduitId(int produitId);
}

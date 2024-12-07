package ma.food.easy.ReactNativeMIY.repository;
import ma.food.easy.ReactNativeMIY.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    @Query("SELECT AVG(r.ratingValue) FROM Rating r WHERE r.produit.id = :productId")
    Integer findAverageRatingByProduitId(@Param("productId") int productId);
    List<Rating> findByProduitId(int produitId);
}

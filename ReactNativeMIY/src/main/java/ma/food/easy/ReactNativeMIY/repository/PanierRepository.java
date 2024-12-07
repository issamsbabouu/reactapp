package ma.food.easy.ReactNativeMIY.repository;

import ma.food.easy.ReactNativeMIY.model.Panier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface  PanierRepository extends JpaRepository<Panier, Long> {
    List<Panier> findByCompteUsername(String username);
    List<Panier> findAllByIdInAndCompteUsername(List<Integer> panierIds, String username);
    Panier findByCommandesId(int productId);
    Optional<Panier> findByCompteId(int compteId);
}

package ma.food.easy.ReactNativeMIY.repository;

import ma.food.easy.ReactNativeMIY.model.User;
import ma.food.easy.ReactNativeMIY.model.commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CommandeRepository extends JpaRepository<commande, Long> {
    int countCommandesByCompte(User compte);
    List<commande> findAllByDelivered(Boolean delivered);
    List<commande> findAllByDeliveredAndDateLivraison(Boolean delivered, String dateLivraison);
    List<commande> findByDateLivraisonContaining(String keyword);
    int countByCompte_Filiere(String filiere);

    List<commande> findAllByPanierCompteEqualsAndConfirmed(User compte, Boolean confirmed);
    List<commande> findAllByPanierCompteEquals(User compte);

    List<commande> findAllByDeliverymanUsername(String username);
}

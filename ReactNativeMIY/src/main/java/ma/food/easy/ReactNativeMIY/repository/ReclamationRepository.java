package ma.food.easy.ReactNativeMIY.repository;

import ma.food.easy.ReactNativeMIY.model.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {
    List<Reclamation> findAllByCompteUsername(String username);
    int countByCompte_Filiere(String Filiere);
    int countByCompte_Type(String Type);
}

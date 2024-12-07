package ma.food.easy.ReactNativeMIY.repository;
import ma.food.easy.ReactNativeMIY.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    int countByCompte_Filiere(String Filiere);
    int countByCompte_Type(String Type);
    List<Comment> findByProduitId(Long produitId);

    List<Comment> findAllByCompteUsername(String username);
    List<Comment> findAllByProduitId(int id);

}

package ma.food.easy.ReactNativeMIY.repository;

import ma.food.easy.ReactNativeMIY.model.Categorie;
import ma.food.easy.ReactNativeMIY.model.produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ProduitRepository extends JpaRepository<produit, Long> {
    int countByCategorie(Categorie cat );
    int countByCategorie_Catname(String cat);

}

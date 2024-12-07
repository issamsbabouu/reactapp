package ma.food.easy.ReactNativeMIY.repository;

import ma.food.easy.ReactNativeMIY.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    boolean existsByCatname(String catname);
    Categorie findCategorieByCatname(String name);
}

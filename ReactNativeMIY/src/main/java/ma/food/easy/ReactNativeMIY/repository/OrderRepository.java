package ma.food.easy.ReactNativeMIY.repository;
import ma.food.easy.ReactNativeMIY.model.commande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<commande, Long> {
}

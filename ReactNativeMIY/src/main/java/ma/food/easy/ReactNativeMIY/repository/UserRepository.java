package ma.food.easy.ReactNativeMIY.repository;

import ma.food.easy.ReactNativeMIY.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


import java.util.List;

public interface  UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmailAndPassword(String username, String password);
    int countByType(String type);
    int countByFiliere(String fill);
    List<User> findAllByType(String type);

}

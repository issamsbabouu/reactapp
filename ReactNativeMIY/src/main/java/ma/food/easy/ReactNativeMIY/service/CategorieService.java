package main.java.com.example.service;

import com.example.model.Categorie;
import com.example.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategorieService {

    @Autowired
    private CategorieRepository categorieRepository;

    // Ajouter une catégorie
    public Categorie ajouterCategorie(Categorie categorie) {
        return categorieRepository.save(categorie); // Sauvegarde la catégorie dans la base de données
    }

    // Modifier une catégorie
    public Categorie modifierCategorie(Long id, Categorie categorie) {
        Optional<Categorie> existingCategorie = categorieRepository.findById(id);
        if (existingCategorie.isPresent()) {
            Categorie updatedCategorie = existingCategorie.get();
            updatedCategorie.setNom(categorie.getNom()); // Met à jour le nom de la catégorie
            return categorieRepository.save(updatedCategorie); // Sauvegarde la catégorie mise à jour
        } else {
            throw new RuntimeException("Catégorie non trouvée avec id : " + id); // Lève une exception si la catégorie n'existe pas
        }
    }

    // Supprimer une catégorie
    public void supprimerCategorie(Long id) {
        Optional<Categorie> existingCategorie = categorieRepository.findById(id);
        if (existingCategorie.isPresent()) {
            categorieRepository.delete(existingCategorie.get()); // Supprime la catégorie de la base de données
        } else {
            throw new RuntimeException("Catégorie non trouvée avec id : " + id); // Lève une exception si la catégorie n'existe pas
        }
    }

    // Lister toutes les catégories
    public List<Categorie> listerCategories() {
        return categorieRepository.findAll(); // Retourne toutes les catégories présentes dans la base de données
    }
}

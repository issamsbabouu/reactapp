package main.java.com.example.controller;

import com.example.model.Categorie;
import com.example.service.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategorieController {

    @Autowired
    private CategorieService categorieService;

    // Ajouter une catégorie
    @PostMapping("/ajouter")
    public Categorie ajouterCategorie(@RequestBody Categorie categorie) {
        return categorieService.ajouterCategorie(categorie);
    }

    // Modifier une catégorie
    @PutMapping("/modifier/{id}")
    public Categorie modifierCategorie(@PathVariable Long id, @RequestBody Categorie categorie) {
        return categorieService.modifierCategorie(id, categorie);
    }

    // Supprimer une catégorie
    @DeleteMapping("/supprimer/{id}")
    public void supprimerCategorie(@PathVariable Long id) {
        categorieService.supprimerCategorie(id);
    }

    // Lister toutes les catégories
    @GetMapping("/listercategories")
    public List<Categorie> listerCategories() {
        return categorieService.listerCategories();
    }
}

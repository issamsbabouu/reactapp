package com.example.demo.service;
import com.example.demo.modele.categorie;
import com.example.demo.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategorieService {

    @Autowired
    private CategorieRepository categoryRepository;

    public List<categorie> getCategories() {
        return categoryRepository.findAll();
    }

    public categorie addCategory(categorie category) {
        return categoryRepository.save(category);
    }

    // Méthode pour supprimer une catégorie
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}

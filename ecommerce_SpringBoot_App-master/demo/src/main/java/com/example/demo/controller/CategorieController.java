package com.example.demo.controller;

import com.example.demo.DTO.CategorieDTO;
import com.example.demo.modele.categorie;
import com.example.demo.repository.CategorieRepository;
import com.example.demo.service.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategorieController {
    @Autowired
    private CategorieService categoryService;
    @Autowired
    private CategorieRepository categoryRepository;

    @GetMapping
    public List<CategorieDTO> getCategories() {
        List<categorie> categories = categoryRepository.findAll(); // Récupérer toutes les catégories
        List<CategorieDTO> categoryDTOs = categories.stream()
                .map(category -> new CategorieDTO((long) category.getId(), category.getCatname()))
                .collect(Collectors.toList()); // Mapper vers CategoryDTO
        return categoryDTOs;
    }
    @PostMapping
    public ResponseEntity<categorie> addCategory(@RequestBody categorie category) {
        categorie savedCategory = categoryService.addCategory(category);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}


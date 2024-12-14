package com.example.demo.controller;

import com.example.demo.DTO.CategorieDTO;
import com.example.demo.DTO.ProduitDTO;
import com.example.demo.modele.categorie;
import com.example.demo.modele.produit;
import com.example.demo.repository.ProduitRepository;
import com.example.demo.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProduitController {

    @Autowired
    private ProduitService produitService;  // Service pour gérer la logique métier des produits
    @Autowired
    private ProduitRepository produitRepository; // Repository pour interagir avec la base de données
    @GetMapping
    public List<ProduitDTO> getProducts() {
        List<produit> products = produitService.getProducts();
        return products.stream()
                .map(product -> new ProduitDTO(product.getId(), product.getLabel(), product.getPrice(), product.getDescription()))
                .collect(Collectors.toList());
    }
    // Ajouter un nouveau produit
    @PostMapping
    public ResponseEntity<produit> addProduct(@RequestBody produit product) {
        produit savedProduct = produitService.addProduct(product);  // Appeler le service pour ajouter un produit
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    // Supprimer un produit
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        produitService.deleteProduct(id);  // Appeler le service pour supprimer un produit par ID
        return ResponseEntity.noContent().build();
    }

    // Modifier un produit (optionnel, si vous avez cette fonctionnalité)
    @PutMapping("/{id}")
    public ResponseEntity<produit> updateProduct(@PathVariable Long id, @RequestBody produit updatedProduct) {
        produit product = produitService.updateProduct(id, updatedProduct);  // Appeler le service pour mettre à jour le produit
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}

package com.example.controller;

import com.example.model.Produit;
import com.example.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    // Ajouter un produit
    @PostMapping("/ajouter")
    public Produit ajouterProduit(@RequestBody Produit produit) {
        return produitService.ajouterProduit(produit);
    }

    // Modifier un produit
    @PutMapping("/modifier/{id}")
    public Produit modifierProduit(@PathVariable Long id, @RequestBody Produit produit) {
        return produitService.modifierProduit(id, produit);
    }

    // Supprimer un produit
    @DeleteMapping("/supprimer/{id}")
    public void supprimerProduit(@PathVariable Long id) {
        produitService.supprimerProduit(id);
    }

    // Consulter tous les produits
    @GetMapping("/consulter")
    public List<Produit> consulterProduits() {
        return produitService.consulterProduits();
    }

    // Consulter un produit par ID
    @GetMapping("/consulter/{id}")
    public Produit consulterProduit(@PathVariable Long id) {
        return produitService.consulterProduit(id);
    }
}

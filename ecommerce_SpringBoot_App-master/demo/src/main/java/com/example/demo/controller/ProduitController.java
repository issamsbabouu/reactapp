package com.example.demo.controller;

import com.example.demo.modele.*;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.*;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products") // Le préfixe pour toutes les routes dans ce contrôleur
public class ProduitController {

    @Autowired
    private ProduitService productService;

    @GetMapping("/getall")
    public String getAllProducts(Model model) {
        List<produit> products = productService.getAllProducts();
        model.addAttribute("products", products);  // Ajoute la liste des produits au modèle
        return "api/products";  // Nom du template Thymeleaf à afficher
    }

    // Afficher un produit par ID
    @GetMapping("/{id}")
    public String getProductById(@PathVariable("id") Long id, Model model) {
        Optional<produit> product = productService.getProductById(id);
        model.addAttribute("product", product);  // Ajoute le produit au modèle
        return "api/product-details";  // Nom du template Thymeleaf pour afficher les détails
    }

    // Afficher le formulaire pour ajouter un produit
    @GetMapping("/new")
    public String showProductForm(Model model) {
        model.addAttribute("product", new produit());  // Ajoute un produit vide pour le formulaire
        return "api/product-form";  // Nom du template Thymeleaf pour afficher le formulaire
    }

    @PostMapping("/save")
    public String saveProduct(produit product) {
        productService.saveProduct(product);  // Sauvegarde le produit dans la base de données
        return "redirect:/api/products";  // Redirige vers la liste des produits
    }

    // Supprimer un produit par ID
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);  // Supprime le produit de la base de données
        return "redirect:/api/products";  // Redirige vers la liste des produits
    }
}

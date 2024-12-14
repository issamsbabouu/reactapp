package com.example.demo.service;

import com.example.demo.modele.categorie;
import com.example.demo.modele.produit;
import com.example.demo.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProduitService {

    @Autowired
    private ProduitRepository produitRepository;  // Utiliser le repository des produits

    public List<produit> getProducts() {
        return produitRepository.findAll();  // Renvoie tous les produits
    }

    // Ajouter un nouveau produit
    public produit addProduct(produit product) {
        return produitRepository.save(product);
    }

    // Supprimer un produit par son ID
    public void deleteProduct(Long id) {
        produitRepository.deleteById(id);
    }

    // Mettre à jour un produit existant
    public produit updateProduct(Long id, produit updatedProduct) {
        Optional<produit> existingProduct = produitRepository.findById(id);
        if (existingProduct.isPresent()) {
            produit product = existingProduct.get();
            product.setLabel(updatedProduct.getLabel());
            product.setDescription(updatedProduct.getDescription());
            product.setPrice(updatedProduct.getPrice());
            product.setColor(updatedProduct.getColor());
            product.setSize(updatedProduct.getSize());
            product.setQuantity(updatedProduct.getQuantity());
            product.setPhoto(updatedProduct.getPhoto());
            product.setCategorie(updatedProduct.getCategorie());
            return produitRepository.save(product);
        } else {
            throw new RuntimeException("Produit non trouvé");
        }
    }
}

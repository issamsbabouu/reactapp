package com.example.service;

import com.example.model.Produit;
import com.example.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProduitService {

    @Autowired
    private ProduitRepository produitRepository;

    // Ajouter un produit
    public Produit ajouterProduit(Produit produit) {
        return produitRepository.save(produit); // Sauvegarde un produit dans la base de données
    }

    // Modifier un produit
    public Produit modifierProduit(Long id, Produit produit) {
        Optional<Produit> existingProduit = produitRepository.findById(id);
        if (existingProduit.isPresent()) {
            Produit p = existingProduit.get();
            p.setNom(produit.getNom());
            p.setDetails(produit.getDetails());
            p.setPrice(produit.getPrice());
            p.setImage(produit.getImage());
            p.setQuantite(produit.getQuantite());
            return produitRepository.save(p); // Met à jour le produit existant
        } else {
            return null; // Retourne null si le produit n'existe pas
        }
    }

    // Supprimer un produit
    public void supprimerProduit(Long id) {
        produitRepository.deleteById(id); // Supprime le produit par son ID
    }

    // Consulter tous les produits
    public List<Produit> consulterProduits() {
        return produitRepository.findAll(); // Retourne tous les produits
    }

    // Consulter un produit par ID
    public Produit consulterProduit(Long id) {
        Optional<Produit> produit = produitRepository.findById(id);
        return produit.orElse(null); // Retourne le produit trouvé ou null si non trouvé
    }
}

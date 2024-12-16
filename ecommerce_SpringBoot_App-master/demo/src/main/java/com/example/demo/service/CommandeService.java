package com.example.demo.service;

import com.example.demo.modele.Panier;
import com.example.demo.modele.commande;
import com.example.demo.repository.CommandeRepository;
import com.example.demo.repository.PanierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private PanierRepository panierRepository;

    // CommandeService.java
    public void confirmerPanier(Long userId) {
        // Retrieve the user's basket
        Panier panier = (Panier) panierRepository.findByCompteId(userId);
        if (panier == null) {
            throw new RuntimeException("No basket found for this user");
        }

        // Create a command for each item in the basket
        for (commande panierCommande : panier.getCommandes()) {
            commande newCommande = new commande();
            newCommande.setPanier(panier);
            newCommande.setProduct(panierCommande.getProduct());
            newCommande.setQuantity(panierCommande.getQuantity());
            commandeRepository.save(newCommande);
        }

        panier.getCommandes().clear();
        panierRepository.save(panier);  // Save the empty basket
    }
    public List<commande> getAllCommandes() {
        return commandeRepository.findAll(); // Fetch all commandes from the database
    }
    public List<commande> getCommandesByUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("L'ID utilisateur est nul.");
        }
        return commandeRepository.findByPanier_CompteId(userId);
    }

}

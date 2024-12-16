package com.example.demo.service;

import com.example.demo.DTO.CommandeDTO;
import com.example.demo.DTO.PanierDTO;
import com.example.demo.modele.Panier;
import com.example.demo.modele.comptes;
import com.example.demo.repository.PanierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class PanierService {

    @Autowired
    private PanierRepository panierRepository;


    public PanierDTO getPanierForUser(Long compteId) {
        // Retrieve the panier (basket) for the user by their compteId
        Panier panier = panierRepository.findByCompteId(compteId);

        // If panier is not found, return null or throw a custom exception based on your needs
        if (panier == null) {
            return null;
        }

        // Map the panier entity to a DTO
        return new PanierDTO(
                panier.getId(),
                panier.getCommandes().stream().map(commande -> new CommandeDTO(
                        commande.getId(),
                        commande.getProduct().getLabel(),
                        commande.getProduct().getPrice(),
                        commande.getProduct().getPhoto(),
                        commande.getQuantity()
                )).collect(Collectors.toList())
        );
    }

}

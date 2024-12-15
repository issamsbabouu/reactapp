package com.example.demo.service;

import com.example.demo.DTO.CommandeDTO;
import com.example.demo.modele.commande;
import com.example.demo.repository.CommandeRepository;
import com.example.demo.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private ProduitRepository produitRepository;  // Assuming you're fetching products by their IDs (if needed)

    public CommandeDTO createOrder(CommandeDTO commandeDTO) {
        // Create a new commande object and populate its fields from the DTO
        commande newOrder = new commande();
        newOrder.setDateLivraison(commandeDTO.getDateLivraison());  // Assuming this is a date, update accordingly
        newOrder.setLieuLivraison(commandeDTO.getDeliveryAddress());  // Correct the method name to get the address

        // If you're adding products, you may need to handle them here
        // Example:
        // newOrder.setProduit(produitRepository.findById(commandeDTO.getProductId()).orElse(null));

        // Save the new order to the repository
        commande savedOrder = commandeRepository.save(newOrder);

        // Return a DTO with the saved order data
        return new CommandeDTO(
                savedOrder.getId(),
                savedOrder.getDateLivraison(),
                savedOrder.getLieuLivraison()
        );
    }
}

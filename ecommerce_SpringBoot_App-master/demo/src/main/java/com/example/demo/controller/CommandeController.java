package com.example.demo.controller;

import com.example.demo.DTO.CommandeRequest;
import com.example.demo.DTO.CommandeResponse;
import com.example.demo.modele.commande;
import com.example.demo.repository.CommandeRepository;
import com.example.demo.service.CommandeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class CommandeController {

    @Autowired
    private CommandeService commandeService;

    @Autowired
    private CommandeRepository commandeRepository;
    // CommandeController.java
    @PostMapping("/confirm")
    public String confirmOrder(@RequestBody List<CommandeRequest> commandesRequest) {
        // Example logic to process the orders
        for (CommandeRequest request : commandesRequest) {
            // Assuming the id is coming as 'id' instead of 'productId'
            commande commande = new commande(request.getId(), request.getQuantity()); // Change productId to id
            commandeRepository.save(commande);
        }
        return "Order confirmed!";
    }
    @GetMapping("/user")
    public List<CommandeResponse> getOrdersForUser(HttpSession session) {
        Long userId = (Long) session.getAttribute("compteId");
        if (userId == null) {
            throw new RuntimeException("Utilisateur non authentifié");
        }

        List<commande> commandes = commandeService.getCommandesByUserId(userId);

        // Convertir les commandes en CommandeResponse pour une réponse plus claire
        return commandes.stream()
                .map(commande -> new CommandeResponse(
                        commande.getId(),
                        commande.getProduct().getLabel(),
                        commande.getQuantity(),
                        (long) commande.getPanier().getCompte().getId()
                ))
                .toList();
    }
    @GetMapping("/commandes")
    public List<commande> getAllCommandes() {
        List<commande> commandes = commandeService.getAllCommandes();

        for (commande c : commandes) {
            if (c.getProduct() != null) {
                c.getProduct().setCategorie(null);
            }
        }


        return commandes;
    }
}

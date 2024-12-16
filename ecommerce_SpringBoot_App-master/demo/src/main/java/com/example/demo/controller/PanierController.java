package com.example.demo.controller;

import com.example.demo.DTO.CommandeDTO;
import com.example.demo.DTO.PanierDTO;
import com.example.demo.modele.Panier;
import com.example.demo.modele.comptes;
import com.example.demo.repository.PanierRepository;
import com.example.demo.service.PanierService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/panier")
public class PanierController {

    @Autowired
    private PanierRepository panierRepository;
    @Autowired
    private PanierService basketService;

    @GetMapping("/user")
    public ResponseEntity<PanierDTO> getBasketForUser(HttpSession session) {
        Long compteId = (Long) session.getAttribute("compteId");

        // Check if user is authenticated
        if (compteId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);  // User not authenticated
        }

        // Retrieve the user's basket from the database
        Panier panier = panierRepository.findByCompteId(compteId);

        // Check if the panier (basket) exists
        if (panier == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Basket not found
        }

        // Map the Panier entity to PanierDTO
        PanierDTO panierDTO = new PanierDTO(
                panier.getId(),
                panier.getCommandes().stream().map(commande -> new CommandeDTO(
                        commande.getId(),
                        commande.getProduct().getLabel(),
                        commande.getProduct().getPrice(),
                        commande.getProduct().getPhoto(),
                        commande.getQuantity()
                )).collect(Collectors.toList())
        );

        // Return the basket as DTO
        return ResponseEntity.ok(panierDTO);
    }
}

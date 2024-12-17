package com.example.demo.controller;

import com.example.demo.DTO.CommandeDTO;
import com.example.demo.DTO.PanierDTO;
import com.example.demo.modele.Panier;
import com.example.demo.modele.commande;
import com.example.demo.modele.comptes;
import com.example.demo.repository.CompteRepository;
import com.example.demo.repository.PanierRepository;
import com.example.demo.service.PanierService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/panier")
public class PanierController {
    @Autowired
    private PanierRepository panierRepository;
    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private PanierService basketService;
    @PostMapping("/add")
    public ResponseEntity<String> addToBasket(@RequestBody commande newCommande, HttpSession session) {
        Long compteId = (Long) session.getAttribute("compteId");
        if (compteId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        Optional<comptes> optionalCompte = compteRepository.findById(compteId);
        if (!optionalCompte.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        comptes currentUser = optionalCompte.get();
        Panier panier = panierRepository.findByCompteId(compteId);
        if (panier == null) {
            panier = new Panier();
            panier.setCompte(currentUser);
            panier.setCommandes(new ArrayList<>());
        }

        // Log newCommande to ensure it's being received correctly
        System.out.println("Adding item to basket: " + newCommande);

        panier.getCommandes().add(newCommande);
        panierRepository.save(panier);
        return ResponseEntity.status(HttpStatus.OK).body("Item added to basket successfully");
    }
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

        // Map the Panier entity to PanierDTO with null checks for product
        PanierDTO panierDTO = new PanierDTO(
                panier.getId(),
                panier.getCommandes().stream().map(commande -> {
                    // Safe check for null product
                    String productLabel = "Produit inconnu";
                    String productPhoto = "default.png";  // Default image
                    String productPrice = "0";

                    if (commande.getProduct() != null) {
                        productLabel = commande.getProduct().getLabel();
                        productPrice = String.valueOf(commande.getProduct().getPrice()); // Assuming price is a type like Double or BigDecimal
                        productPhoto = commande.getProduct().getPhoto();
                    }

                    return new CommandeDTO(
                            commande.getId(),
                            productLabel,
                            productPrice,
                            productPhoto,
                            commande.getQuantity()
                    );
                }).collect(Collectors.toList())
        );

        // Return the basket as DTO
        return ResponseEntity.ok(panierDTO);
    }

}

package com.example.demo.controller;

import com.example.demo.DTO.AssignDeliveryResponse;
import com.example.demo.DTO.CommandeRequest;
import com.example.demo.DTO.CommandeResponse;
import com.example.demo.modele.commande;
import com.example.demo.repository.CommandeRepository;
import com.example.demo.service.CommandeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @GetMapping("/livreur")
    public ResponseEntity<?> getOrdersForDeliveryman(HttpSession session) {
        Long deliverymanId = (Long) session.getAttribute("compteId");

        // Check if deliveryman is logged in
        if (deliverymanId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("You must be logged in as a deliveryman.");
        }

        // Fetch commandes for the deliveryman
        List<commande> commandes = commandeService.getCommandesByDeliverymanId(deliverymanId);
        if (commandes == null || commandes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No orders found for the deliveryman.");
        }
        List<CommandeResponse> responseList = commandes.stream()
                .map(commande -> {
                    // Log for debugging
                    System.out.println("Processing Commande ID: " + commande.getId());

                    // Default values in case of missing data
                    String productLabel = "Unknown Product";
                    String clientName = "Unknown Client";
                    String clientPhone = "Unknown Phone";  // Default value for phone
                    Long clientId = null;  // Default to null if client ID is not available

                    // Get product label from the commande's product
                    if (commande.getProduct() != null) {
                        productLabel = commande.getProduct().getLabel();
                    } else {
                        System.out.println("Commande ID: " + commande.getId() + " has null Product.");
                    }

                    // Get client data from the panier's associated compte
                    if (commande.getPanier() != null && commande.getPanier().getCompte() != null) {
                        clientId = (long) commande.getPanier().getCompte().getId();  // This will be Long
                        clientName = commande.getPanier().getCompte().getNom();  // Get client name
                        clientPhone = String.valueOf(commande.getPanier().getCompte().getPhone());  // Get client phone number
                    } else {
                        System.out.println("Commande ID: " + commande.getId() + " has null Panier or Compte.");
                    }

                    // Ensure clientId is not null, else set a default value or skip processing
                    if (clientId == null) {
                        // Log an error or set a default ID if required
                        clientId = -1L;  // Set a default ID value if null
                    }

                    // Create CommandeResponse object with product label, client name, and phone number
                    return new CommandeResponse(
                            commande.getId(),
                            productLabel,
                            commande.getQuantity(),
                            clientId,
                            clientName,
                            clientPhone  // Include phone number in the response
                    );
                })
                .toList();

        return ResponseEntity.ok(responseList);
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
                .map(commande -> {
                    // Safely handle null values for product
                    String productLabel = "Produit inconnu";
                    if (commande.getProduct() != null) {
                        productLabel = commande.getProduct().getLabel();
                    }

                    // Safely handle null values for panier and compte
                    Long panierCompteId = null;
                    String panierCompteNom = "Inconnu";
                    if (commande.getPanier() != null && commande.getPanier().getCompte() != null) {
                        panierCompteId = (long) commande.getPanier().getCompte().getId();
                        panierCompteNom = commande.getPanier().getCompte().getNom();
                    }

                    return new CommandeResponse(
                            commande.getId(),
                            productLabel,
                            commande.getQuantity(),
                            panierCompteId,
                            panierCompteNom
                    );
                })
                .collect(Collectors.toList());
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
    @PostMapping("/assign-deliveryman")
    public ResponseEntity<?> assignDeliveryman(@RequestParam Long orderId, HttpSession session) {
        Long deliverymanId = (Long) session.getAttribute("compteId");
        if (deliverymanId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in as a deliveryman.");
        }

        Optional<commande> optionalCommande = commandeRepository.findById(orderId);
        if (optionalCommande.isEmpty()) {
            return ResponseEntity.badRequest().body("Order not found.");
        }

        commande commande = optionalCommande.get();

        // Assign the deliveryman
        commande.setDeliverymanId(deliverymanId);
        commande.setStatus("TAKEN"); // Set status to TAKEN
        commandeRepository.save(commande);

        // Provide the URL of the image stored in the static folder
        String imageUrl = "http://localhost:8080/taken_order_image.jpg"; // URL to your static image

        return ResponseEntity.ok(new AssignDeliveryResponse("Delivery assigned successfully.", imageUrl));
    }


}

package main.java.com.example.controller;

import main.java.com.example.service.CommandeService;
import main.java.com.example.model.Commande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commande")
public class CommandeController {

    @Autowired
    private CommandeService commandeService;

    // 1. Méthode pour consulter une commande par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Commande> consultation(@PathVariable("id") int id) {
        Commande commande = commandeService.consulterCommande(id);
        if (commande != null) {
            return new ResponseEntity<>(commande, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 2. Méthode pour confirmer la livraison d'une commande
    @PutMapping("/{id}/confirmation-livraison")
    public ResponseEntity<String> confirmationDeLivraison(@PathVariable("id") int id) {
        boolean isConfirmed = commandeService.confirmerLivraison(id);
        if (isConfirmed) {
            return new ResponseEntity<>("La livraison a été confirmée avec succès.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Échec de la confirmation de la livraison.", HttpStatus.BAD_REQUEST);
        }
    }

    // 3. Méthode pour lister toutes les commandes
    @GetMapping("/listcommande")
    public ResponseEntity<List<Commande>> listerCommandes() {
        List<Commande> commandes = commandeService.listerToutesLesCommandes();
        if (!commandes.isEmpty()) {
            return new ResponseEntity<>(commandes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Retourne 204 si aucune commande n'est trouvée
        }
    }
}

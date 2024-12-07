package main.java.com.example.controller;

import main.java.com.example.service.PanierService;
import main.java.com.example.model.Panier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/panier")
public class PanierController {

    @Autowired
    private PanierService panierService;

    // 1. Méthode pour consulter un panier par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Panier> consulter(@PathVariable("id") int id) {
        Panier panier = panierService.consulterPanier(id);
        if (panier != null) {
            return new ResponseEntity<>(panier, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 2. Méthode pour confirmer un panier (par exemple, marquer comme validé)
    @PutMapping("/{id}/confirmer")
    public ResponseEntity<String> confirmer(@PathVariable("id") int id) {
        boolean isConfirmed = panierService.confirmerPanier(id);
        if (isConfirmed) {
            return new ResponseEntity<>("Le panier a été confirmé avec succès.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Échec de la confirmation du panier.", HttpStatus.BAD_REQUEST);
        }
    }
}

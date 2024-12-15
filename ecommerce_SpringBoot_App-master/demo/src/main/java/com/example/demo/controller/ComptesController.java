package com.example.demo.controller;

import com.example.demo.DTO.CompteDTO;
import com.example.demo.DTO.LoginRequest;
import com.example.demo.DTO.LoginResponse;
import com.example.demo.service.CommandeService;
import com.example.demo.service.ComptesService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comptes")
public class ComptesController {

    @Autowired
    private ComptesService daoComptes;

    @Autowired
    private CommandeService commandeService;

    public ComptesController(ComptesService daoComptes) {
        this.daoComptes = daoComptes;
    }

    // Méthode pour la connexion et la gestion de session
    @PostMapping("/loginii")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        try {
            // Authentifier l'utilisateur
            LoginResponse response = daoComptes.authenticate(loginRequest.getEmail(), loginRequest.getPassword());

            // Stocker l'ID utilisateur dans la session
            session.setAttribute("compteId", response.getCompteId());

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Récupérer les informations spécifiques au compte connecté
    @GetMapping("/me")
    public ResponseEntity<?> getMyAccount(HttpSession session) {
        // Vérifier si l'utilisateur est connecté
        Long compteId = (Long) session.getAttribute("compteId");

        if (compteId == null) {
            return ResponseEntity.status(401).body("Non connecté");
        }

        // Récupérer les informations du compte
        CompteDTO compteDTO = daoComptes.getCompteById(compteId);

        return ResponseEntity.ok(compteDTO);
    }

    // Liste de tous les comptes (accessible pour les admins uniquement)
    @GetMapping
    public List<CompteDTO> getAllComptes() {
        return daoComptes.getAllComptes();
    }

    // Supprimer un utilisateur par son ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompte(@PathVariable int id) {
        daoComptes.deleteCompte(id);
        return ResponseEntity.noContent().build();
    }
}

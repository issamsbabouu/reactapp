package com.example.demo.controller;

import com.example.demo.DTO.*;
import com.example.demo.modele.comptes;
import com.example.demo.service.ComptesService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comptes")
public class ComptesController {

    @Autowired
    private ComptesService daoComptes;


    public ComptesController(ComptesService daoComptes) {
        this.daoComptes = daoComptes;
    }

    @PostMapping("/loginii")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        try {
            // Authentifier l'utilisateur
            LoginResponse response = daoComptes.authenticate(loginRequest.getEmail(), loginRequest.getPassword());

            // Stocker l'ID utilisateur dans la session
            session.setAttribute("compteId", response.getCompteId());

            // Retourner une réponse avec les informations du compte
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
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

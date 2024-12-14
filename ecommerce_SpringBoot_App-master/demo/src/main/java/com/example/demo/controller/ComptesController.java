package com.example.demo.controller;
import com.example.demo.DTO.CategorieDTO;
import com.example.demo.DTO.CompteDTO;
import com.example.demo.DTO.LoginRequest;
import com.example.demo.DTO.LoginResponse;
import com.example.demo.modele.categorie;
import com.example.demo.modele.comptes;
import com.example.demo.modele.produit;
import com.example.demo.service.CommandeService;
import com.example.demo.service.ComptesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/comptes")
public class ComptesController {
    @Autowired
    private ComptesService daoComptes;
    @Autowired
    private CommandeService commandeService;
    @Autowired
    private ComptesService comptesService;
    public ComptesController(ComptesService daoComptes) {
        this.daoComptes = daoComptes;
    }

    @PostMapping("/loginii")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse response = daoComptes.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping
    public List<CompteDTO> getAllComptes() {
        return comptesService.getAllComptes();
    }

    // Supprimer un utilisateur par son ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompte(@PathVariable int id) {
        comptesService.deleteCompte(id);
        return ResponseEntity.noContent().build();
    }
}

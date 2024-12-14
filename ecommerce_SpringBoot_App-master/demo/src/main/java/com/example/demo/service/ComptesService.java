package com.example.demo.service;

import com.example.demo.DTO.CompteDTO;
import com.example.demo.DTO.LoginResponse;
import com.example.demo.modele.comptes;
import com.example.demo.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComptesService {
    public ComptesService(){}

    public ComptesService(CompteRepository compteRepository){
        this.compteRepository=compteRepository;
    }
    @Autowired
    private CompteRepository compteRepository;
    public LoginResponse authenticate(String email, String password) {
        comptes user = compteRepository.findByEmail(email);
        if (user == null || !user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        String role = user.getType();
        System.out.println("User role: " + role);
        String token = role.equals("client") ? "client-token" : role.equals("admin") ? "admin-token" : null;
        if (token == null) {
            throw new IllegalArgumentException("Invalid role");
        }
        return new LoginResponse("Login successful", token);
    }
    public List<comptes> findByType(String type){
        return compteRepository.findAllByType(type);
    }
    public List<CompteDTO> getAllComptes() {
        List<comptes> comptesList = compteRepository.findAll();
        return comptesList.stream()
                .map(this::convertToDTO)  // Convertit chaque utilisateur en DTO
                .collect(Collectors.toList());
    }

    // Supprimer un utilisateur par ID
    public void deleteCompte(int id) {
        compteRepository.deleteById((long) id);
    }

    // Convertir un compte en DTO
    private CompteDTO convertToDTO(comptes compte) {
        return new CompteDTO(
                compte.getId(),
                compte.getUsername(),
                compte.getEmail(),
                compte.getNom(),
                compte.getType()
        );
    }
}

package com.example.demo.service;

import com.example.demo.DTO.CompteDTO;
import com.example.demo.DTO.LoginResponse;
import com.example.demo.DTO.SignUpDTO;
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
        String token = role.equals("client") ? "client-token" : role.equals("admin") ? "admin-token" : role.equals("livreur") ? "livreur-token": null;
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
    private CompteDTO convertToDTO(comptes compte) {
        return new CompteDTO(
                compte.getId(),
                compte.getUsername(),
                compte.getEmail(),
                compte.getNom(),
                compte.getType(),
                compte.getPhone()
        );
    }
    public CompteDTO signUp(SignUpDTO signUpDTO) {
        comptes newCompte = new comptes();

        newCompte.setUsername(signUpDTO.getUsername());
        newCompte.setEmail(signUpDTO.getEmail());
        newCompte.setPhone(signUpDTO.getPhone());  // Set phone from SignUpDTO
        newCompte.setPassword(signUpDTO.getPassword());  // No encryption here
        newCompte.setPhoto(signUpDTO.getPhoto());
        newCompte.setNom(signUpDTO.getNom());
        newCompte.setFiliere("client");
        newCompte.setType("client");
        comptes savedCompte = compteRepository.save(newCompte);
        return new CompteDTO(
                savedCompte.getId(),
                savedCompte.getUsername(),
                savedCompte.getEmail(),
                savedCompte.getNom(),
                savedCompte.getType(),
                savedCompte.getPhone()
        );
    }
    public CompteDTO signUppi(SignUpDTO signUpDTO) {
        comptes newCompte = new comptes();
        newCompte.setUsername(signUpDTO.getUsername());
        newCompte.setEmail(signUpDTO.getEmail());
        newCompte.setPhone(signUpDTO.getPhone());  // Set phone from SignUpDTO
        newCompte.setPassword(signUpDTO.getPassword());  // No encryption here
        newCompte.setPhoto(signUpDTO.getPhoto());
        newCompte.setNom(signUpDTO.getNom());
        newCompte.setFiliere("livreur");
        newCompte.setType("livreur");
        comptes savedCompte = compteRepository.save(newCompte);

        return new CompteDTO(
                savedCompte.getId(),
                savedCompte.getUsername(),
                savedCompte.getEmail(),
                savedCompte.getNom(),
                savedCompte.getType(),
                savedCompte.getPhone() // Include phone in the response DTO
        );
    }
}

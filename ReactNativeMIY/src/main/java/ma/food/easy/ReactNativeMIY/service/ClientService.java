package main.java.com.example.service;

import main.java.com.example.dto.ClientRegistrationDto;
import main.java.com.example.model.Client;
import main.java.com.example.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    // Méthode pour enregistrer un client
    public void register(ClientRegistrationDto clientDto) {
        // Validation des données d'entrée
        if (clientRepository.existsByEmail(clientDto.getEmail())) {
            throw new RuntimeException("Un client avec cet email existe déjà !");
        }

        // Conversion du DTO en entité Client
        Client client = new Client();
        client.setNom(clientDto.getNom());
        client.setPrenom(clientDto.getPrenom());
        client.setEmail(clientDto.getEmail());
        client.setPassword(clientDto.getPassword()); // Assurez-vous de hacher le mot de passe en production
        client.setAdresse(clientDto.getAdresse());

        // Enregistrement dans la base de données
        clientRepository.save(client);
    }
}

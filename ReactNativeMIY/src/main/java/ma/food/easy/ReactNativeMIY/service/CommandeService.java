package main.java.com.example.service;

import com.example.model.Commande;
import com.example.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    // Consulter une commande par son ID
    public Commande consulterCommande(int id) {
        Optional<Commande> commande = commandeRepository.findById(id);
        return commande.orElse(null); // Si la commande existe, on la retourne, sinon on retourne null
    }

    // Confirmer la livraison d'une commande
    public boolean confirmerLivraison(int id) {
        Optional<Commande> commande = commandeRepository.findById(id);
        if (commande.isPresent()) {
            Commande cmd = commande.get();
            cmd.setEtat(true); // On suppose que "true" signifie que la commande a été livrée
            commandeRepository.save(cmd); // Sauvegarde la commande mise à jour
            return true;
        } else {
            return false; // La commande n'existe pas
        }
    }

    // Lister toutes les commandes
    public List<Commande> listerToutesLesCommandes() {
        return commandeRepository.findAll(); // Retourne la liste de toutes les commandes dans la base de données
    }
}

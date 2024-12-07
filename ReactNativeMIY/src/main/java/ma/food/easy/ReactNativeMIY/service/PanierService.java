package main.java.com.example.service;

import com.example.model.Panier;
import com.example.repository.PanierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PanierService {

    @Autowired
    private PanierRepository panierRepository;

    // Consulter un panier par son ID
    public Panier consulterPanier(int id) {
        Optional<Panier> panier = panierRepository.findById(id);
        return panier.orElse(null); // Si le panier existe, on le retourne, sinon on retourne null
    }

    // Confirmer un panier (par exemple, marquer comme validé)
    public boolean confirmerPanier(int id) {
        Optional<Panier> panier = panierRepository.findById(id);
        if (panier.isPresent()) {
            Panier p = panier.get();
            p.setEtat(true); // Supposons que 'true' signifie que le panier a été validé
            panierRepository.save(p); // Sauvegarde le panier mis à jour
            return true;
        } else {
            return false; // Le panier n'existe pas
        }
    }
}

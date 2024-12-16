package com.example.demo.modele;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Panier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "panier", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<commande> commandes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "compte_id")
    private comptes compte;

    public Panier() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<commande> getCommandes() {
        return commandes;
    }

    public void setCommandes(List<commande> commandes) {
        this.commandes = commandes;
    }

    public comptes getCompte() {
        return compte;
    }

    public void setCompte(comptes compte) {
        this.compte = compte;
    }
    public void addProduct(produit product, int quantity) {
        commandes.add(new commande(product, quantity));
    }
    public void addCommande(commande commande) {
        if (commande != null) {
            commandes.add(commande);
            commande.setPanier(this);  // Set the Panier reference in the Commande
        }
    }

}

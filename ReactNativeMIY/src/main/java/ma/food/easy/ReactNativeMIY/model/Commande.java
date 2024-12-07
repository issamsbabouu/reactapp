package com.example.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_commande;

    @OneToMany
    @JoinColumn(name = "id_produit")
    private List<Produit> produits;

    @OneToOne
    @JoinColumn(name="nom_livreur")
    private String livreur;
    private boolean etat;

    public Commande(int id, List<Produit> produits, String nom_livreur, boolean etat) {
        this.id = id;
        this.produits = produits;
        this.nom_livreur = nom_livreur;
        this.etat = etat;
    }

    public Commande() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    public String getNom_livreur() {
        return nom_livreur;
    }

    public void setNom_livreur(String nom_livreur) {
        this.nom_livreur = nom_livreur;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", produits=" + produits +
                ", nom_livreur='" + nom_livreur + '\'' +
                ", etat=" + etat +
                '}';
    }
}
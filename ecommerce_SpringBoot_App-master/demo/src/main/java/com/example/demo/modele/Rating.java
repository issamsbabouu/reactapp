package com.example.demo.modele;

import jakarta.persistence.*;

@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int ratingValue; // Assuming rating is an integer value, can be adjusted if needed

    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
    private produit produit;

    @ManyToOne
    @JoinColumn(name = "compte_id", nullable = false)
    private comptes compte;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(int ratingValue) {
        this.ratingValue = ratingValue;
    }

    public produit getProduit() {
        return produit;
    }

    public void setProduit(produit produit) {
        this.produit = produit;
    }

    public comptes getCompte() {
        return compte;
    }

    public void setCompte(comptes compte) {
        this.compte = compte;
    }
}


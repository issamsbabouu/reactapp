package com.example.model;

import javax.persistence.*;

@Entity
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_produit;

    @Column(nullable = false, length = 100)
    private String nom;

    @Lob
    @Column
    private String details;

    @Column(nullable = false)
    private double price;

    @Column
    private String image;

    @Column(nullable = false)
    private int quantite;

    public Produit() {
    }

    public Produit(int id_produit, String nom, String details, double price, String image, int quantite) {
        this.id_produit = id_produit;
        this.nom = nom;
        this.details = details;
        this.price = price;
        this.image = image;
        this.quantite = quantite;
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id_produit=" + id_produit +
                ", nom='" + nom + '\'' +
                ", details='" + details + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", quantite=" + quantite +
                '}';
    }
}

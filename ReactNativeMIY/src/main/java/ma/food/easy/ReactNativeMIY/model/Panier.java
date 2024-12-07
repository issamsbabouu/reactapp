package com.example.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Panier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_panier;

    @ManyToMany
    @JoinTable(
            name = "panier_produit",
            joinColumns = @JoinColumn(name = "id_panier"),
            inverseJoinColumns = @JoinColumn(name = "id_produit")
    )
    private List<Produit> produits;

    @Column(nullable = false)
    private float prix_total;

    @Column(nullable = false)
    private boolean etat;

    public Panier() {
    }

    public Panier(int id_panier, List<Produit> produits, float prix_total, boolean etat) {
        this.id_panier = id_panier;
        this.produits = produits;
        this.prix_total = prix_total;
        this.etat = etat;
    }

    public int getId_panier() {
        return id_panier;
    }

    public void setId_panier(int id_panier) {
        this.id_panier = id_panier;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    public float getPrix_total() {
        return prix_total;
    }

    public void setPrix_total(float prix_total) {
        this.prix_total = prix_total;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Panier{" +
                "id_panier=" + id_panier +
                ", produits=" + produits +
                ", prix_total=" + prix_total +
                ", etat=" + etat +
                '}';
    }
}

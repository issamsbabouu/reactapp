package com.example.demo.modele;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;


@Entity
public class commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "panier_id")
    @JsonBackReference
    private Panier panier;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private produit product;

    private int quantity;

    public commande() {
    }

    public commande(produit product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public commande(Long productId, int quantity) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Panier getPanier() {
        return panier;
    }

    public void setPanier(Panier panier) {
        this.panier = panier;
    }

    public produit getProduct() {
        return product;
    }

    public void setProduct(produit product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}

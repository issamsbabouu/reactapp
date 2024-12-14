package com.example.demo.DTO;

import com.example.demo.modele.categorie;

public class ProduitDTO {

    private int id;
    private String label;
    private String description;
    private double price;
    private String color;
    private String photo;
    private String size;
    private int quantity;
    private CategorieDTO categorie;

    public ProduitDTO() {
    }

    public ProduitDTO(int id, String label, double price, String color) {
        this.id = id;
        this.label = label;
        this.price = price;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProduitDTO(int id, String label, String description, double price, String color, String photo, String size, int quantity, CategorieDTO categorie) {
        this.id = id;
        this.label = label;
        this.description = description;
        this.price = price;
        this.color = color;
        this.photo = photo;
        this.size = size;
        this.quantity = quantity;
        this.categorie = categorie;
    }

    public CategorieDTO getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieDTO categorie) {
        this.categorie = categorie;
    }
}
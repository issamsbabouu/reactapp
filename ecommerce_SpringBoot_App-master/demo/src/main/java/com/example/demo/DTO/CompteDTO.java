package com.example.demo.DTO;

public class CompteDTO {
    private Integer id;           // Changement de int à Integer pour permettre les valeurs nulles
    private String username;
    private String email;
    private String nom;
    private String type;
    private Integer phone;        // Changement de int à Integer pour la même raison

    // Constructeur
    public CompteDTO(Integer id, String username, String email, String nom, String type, Integer phone) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.nom = nom;
        this.type = type;
        this.phone = phone;
    }

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }
}

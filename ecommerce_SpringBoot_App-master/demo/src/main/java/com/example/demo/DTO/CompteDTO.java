package com.example.demo.DTO;

public class CompteDTO {
    private int id;
    private String username;
    private String email;
    private String nom;
    private String type;

    public CompteDTO(int id, String username, String email, String nom, String type) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.nom = nom;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}

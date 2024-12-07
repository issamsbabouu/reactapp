package com.example.model;

import javax.persistence.*;

@Entity
@Table(name = "users") // Nom de la table dans la base de données
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrémentation de l'ID
    private int id_user;

    @Column(nullable = false) // Colonne non nulle
    private String nom;

    @Column(nullable = false, unique = true) // Email unique
    private String email;

    @Column(nullable = false) // Colonne non nulle
    private String password;

    @Column(nullable = false) // Colonne non nulle
    private String role;

    @Column(nullable = false) // Colonne non nulle
    private int number;
    // Constructeur par défaut
    public User() {}

    // Constructeur avec paramètres
    public User(int id_user, String nom, String email, String password, String role, int number) {
        this.id_user = id_user;
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.role = role;
        this.number = number;
    }

    // Getters et Setters
    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}

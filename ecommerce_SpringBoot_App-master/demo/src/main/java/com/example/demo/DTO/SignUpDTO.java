package com.example.demo.DTO;

public class SignUpDTO {
    private String username;
    private String email;
    private int phone;
    private String password;
    private String photo;
    private String nom;
    private String filiere;

    public SignUpDTO() {
    }

    public SignUpDTO(String username, String email, int phone, String password, String photo, String nom, String filiere) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.photo = photo;
        this.nom = nom;
        this.filiere = filiere;
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

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getFiliere() {
        return filiere;
    }

    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }
}

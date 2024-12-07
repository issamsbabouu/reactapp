package ma.food.easy.ReactNativeMIY.model;


import jakarta.persistence.*;
import jakarta.persistence.Id;
import ma.food.easy.ReactNativeMIY.model.Panier;
import ma.food.easy.ReactNativeMIY.model.Rating;
import ma.food.easy.ReactNativeMIY.model.commande;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "comptes")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String email;
    private int phone;
    private String password;
    private String type;
    private String nom;
    @OneToMany(mappedBy = "compte", cascade = CascadeType.ALL)
    private List<Panier> paniers;
    @OneToMany(mappedBy = "compte", cascade = CascadeType.ALL)
    private List<commande> commandes;
    @OneToMany(mappedBy = "deliveryman", cascade = CascadeType.ALL)
    private List<commande> commandesd;
    @OneToMany(mappedBy = "compte")
    private List<Rating> ratings;

    public User() {
    }

    public User(int id, String username, String email, int phone, String password, String type, String nom, List<Panier> paniers, List<commande> commandes, List<commande> commandesd, List<Rating> ratings) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.type = type;
        this.nom = nom;
        this.paniers = paniers;
        this.commandes = commandes;
        this.commandesd = commandesd;
        this.ratings = ratings;
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
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getNom() {
        return nom;
    }
}

package ma.food.easy.ReactNativeMIY.model;


import jakarta.persistence.*;

@Entity
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_categorie;

    @Column(nullable = false, unique = true, length = 50)
    private String nom;

    public Categorie() {
    }

    public Categorie(int id_categorie, String nom) {
        this.id_categorie = id_categorie;
        this.nom = nom;
    }

    public int getId_categorie() {
        return id_categorie;
    }

    public void setId_categorie(int id_categorie) {
        this.id_categorie = id_categorie;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "id_categorie=" + id_categorie +
                ", nom='" + nom + '\'' +
                '}';
    }
}

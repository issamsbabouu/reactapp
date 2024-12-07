package ma.food.easy.ReactNativeMIY.model;
import jakarta.persistence.*;
import jakarta.persistence.Id;
@Entity
public class Reclamation {
    public Reclamation(int id, String description, User compte, produit produit, String proof) {
        this.id = id;
        this.description = description;
        this.compte = compte;
        this.produit=produit;
        this.proof=proof;
    }

    public Reclamation() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String description;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User compte;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private produit produit;
    @Column
    private String proof;

    public produit getProduit() {
        return produit;
    }

    public void setProduit(produit produit) {
        this.produit = produit;
    }

    public String getProof() {
        return proof;
    }

    public void setProof(String proof) {
        this.proof = proof;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCompte() {
        return compte;
    }

    public void setCompte(User compte) {
        this.compte = compte;
    }

    public String getdescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

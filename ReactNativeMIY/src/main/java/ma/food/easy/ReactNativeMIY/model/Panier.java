package ma.food.easy.ReactNativeMIY.model;
import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;
@Entity
@Table(name ="panier")
public class Panier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name="customer_id")
    private User compte;

    @OneToMany(mappedBy = "panier", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<commande> commandes;
    public List<commande> getCommandes() {
        return commandes;
    }


    public void setCommandes(List<commande> commandes) {
        this.commandes = commandes;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public User getCompte() {
        return compte;
    }
    public void setCompte(User compte) {
        this.compte = compte;
    }
    public Panier() {
    }

    public Panier(int id, User compte, List<commande> commandes) {
        this.id = id;
        this.commandes=commandes;
        this.compte = compte;
    }
    @Override
    public String toString() {
        return "Panier{" +
                "id=" + id +
                ", compte=" + compte +
                ", commandes=" + commandes;
    }
}

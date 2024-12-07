package ma.food.easy.ReactNativeMIY.model;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name ="commands")
public class commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String dateLivraison;
    @Column
    private String lieuLivraison;
    @Column
    private Boolean delivered;
    @Column
    private int quantity;
    @Column
    private Boolean confirmed;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private produit p;
    @ManyToOne
    @JoinColumn(name="customer_id")
    private User compte;
    @ManyToOne
    @JoinColumn(name="deliveryman_id")
    private User deliveryman;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "panier_id")
    private Panier panier;

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }
    public Panier getPanier() {
        return panier;
    }
    public void setPanier(Panier panier) {
        this.panier = panier;
    }

    public commande() {
    }
    public commande(int id,User deliveryman, int quantity, produit p, User compte, String dateLivraison, String lieuLivraison,  Boolean delivered, Boolean confirmed) {
        this.id = id;
        this.compte = compte;
        this.dateLivraison = dateLivraison;
        this.lieuLivraison = lieuLivraison;
        this.delivered = delivered;
        this.p=p;
        this.quantity= quantity;
        this.deliveryman=deliveryman;
        this.confirmed=confirmed;
    }
    public int getId() {
        return id;
    }

    public User getDeliveryman() {
        return deliveryman;
    }

    public void setDeliveryman(User deliveryman) {
        this.deliveryman = deliveryman;
    }

    public User getCompte() {
        return compte;
    }
    public String getDateLivraison() {
        return dateLivraison;
    }
    public String getLieuLivraison() {
        return lieuLivraison;
    }
    public Boolean isDelivered() {
        return delivered;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setCompte(User compte) {
        this.compte = compte;
    }
    public void setDateLivraison(String dateLivraison) {
        this.dateLivraison = dateLivraison;
    }
    public void setLieuLivraison(String lieuLivraison) {
        this.lieuLivraison = lieuLivraison;
    }
    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public produit getP() {
        return p;
    }

    public void setP(produit p) {
        this.p = p;
    }

    public Boolean getDelivered() {
        return delivered;
    }

    @Override
    public String toString() {
        return "commande{" +
                "id=" + id +
                ", compte=" + compte +
                ", dateLivraison='" + dateLivraison + '\'' +
                ", lieuLivraison='" + lieuLivraison + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        commande commande = (commande) o;
        return id == commande.id && Objects.equals(compte, commande.compte) && Objects.equals(dateLivraison, commande.dateLivraison) && Objects.equals(lieuLivraison, commande.lieuLivraison );
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, compte, dateLivraison, lieuLivraison);
    }
}

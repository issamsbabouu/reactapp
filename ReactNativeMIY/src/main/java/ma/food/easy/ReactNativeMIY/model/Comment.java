package ma.food.easy.ReactNativeMIY.model;
import jakarta.persistence.*;

@Entity
public class Comment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "compte_id", nullable = false)
    private User compte;

    @ManyToOne
    @JoinColumn(name = "produit_id")
    private produit produit;

    public Comment() {}

    public Comment(Long id,int rating, String content, User compte, produit produit) {
        this.id = id;
        this.content = content;
        this.compte = compte;
        this.produit = produit;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", compte=" + compte +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public produit getProduit() {
        return produit;
    }

    public void setProduit(produit produit) {
        this.produit = produit;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getCompte() {
        return compte;
    }

    public void setCompte(User compte) {
        this.compte = compte;
    }
}

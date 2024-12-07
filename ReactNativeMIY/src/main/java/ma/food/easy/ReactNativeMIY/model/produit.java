package ma.food.easy.ReactNativeMIY.model;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name ="products")
public class produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String label;
    @Column
    private String description;
    @Column
    private double price;
    @Column
    private String color;
    @Column
    private String photo;
    @Column
    private String size;
    @Column
    private int quantity;
    @ManyToOne
    @JoinColumn(name="category_id")
    private Categorie categorie;
    @OneToMany(mappedBy = "p")
    private List<commande> command;
    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
    private List<Comment> comments;
    @OneToMany(mappedBy = "produit", fetch = FetchType.EAGER)
    private List<Rating> ratings;
    @OneToMany(mappedBy = "produit")
    private List<Reclamation> reclamations;

    public produit(int id, String label, String description, double price, String color, String photo, int quantity,List<Rating> ratings, String size, Categorie categorie) {
        this.id = id;
        this.label = label;
        this.description = description;
        this.price = price;
        this.color = color;
        this.photo = photo;
        this.size = size;
        this.categorie = categorie;
        this.ratings=ratings;
        this.quantity=quantity;
        this.comments = comments;
    }

    public produit() {}

    public List<commande> getCommand() {
        return command;
    }

    public void setCommand(List<commande> command) {
        this.command = command;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
}

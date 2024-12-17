package com.example.demo.DTO;

public class CommandeDTO {

    private Long id;
    private String label;
    private double price;
    private String photo;
    private int quantity;
    private Long productId;
    public CommandeDTO(Long id, String label, String price, String photo, int quantity) {
        this.id = id;
        this.label = label;
        this.price = Double.parseDouble(String.valueOf(price));
        this.photo = photo;
        this.quantity = quantity;
    }

    public CommandeDTO(Long id, String label, double price, String photo, int quantity) {
    }

    // Getter and Setter methods
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

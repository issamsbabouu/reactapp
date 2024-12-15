package com.example.demo.modele;

import java.util.List;

public class commande {
    private Long id;
    private String clientName;
    private String deliveryAddress;
    private List<Long> productIds;  // Assuming product IDs are a list of Longs
    private int quantity;
    private String livreurName;

    // No-argument constructor for Jackson
    public commande() {}

    // Constructor to easily create a DTO from the entity
    public commande(Long id, String clientName, String deliveryAddress, List<Long> productIds, int quantity, String livreurName) {
        this.id = id;
        this.clientName = clientName;
        this.deliveryAddress = deliveryAddress;
        this.productIds = productIds;
        this.quantity = quantity;
        this.livreurName = livreurName;
    }

    // Getters and setters for all fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getLivreurName() {
        return livreurName;
    }

    public void setLivreurName(String livreurName) {
        this.livreurName = livreurName;
    }
}

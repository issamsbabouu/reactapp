package com.example.demo.DTO;

import java.util.List;

public class CommandeDTO {

    private List<Long> productIds;   // IDs of the products being ordered
    private Integer quantity;        // Quantity of products ordered
    private String deliveryAddress;  // Delivery address for the order
    private String clientName;       // Client's name
    private String livreurName;
    private String dateLivraison;


    public String getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison() {
        this.dateLivraison = dateLivraison;
    }

    public CommandeDTO(int id, String dateLivraison, String lieuLivraison) {
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getLivreurName() {
        return livreurName;
    }

    public void setLivreurName(String livreurName) {
        this.livreurName = livreurName;
    }

    public CommandeDTO(List<Long> productIds, Integer quantity, String deliveryAddress, String clientName, String livreurName) {
        this.productIds = productIds;
        this.quantity = quantity;
        this.deliveryAddress = deliveryAddress;
        this.clientName = clientName;
        this.livreurName = livreurName;
    }
}

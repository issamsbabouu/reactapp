package com.example.demo.DTO;

public class CommandeResponse {
    private Long id;
    private String productName;
    private int quantity;
    private Long userId;

    public CommandeResponse(Long id, String productName, int quantity, Long userId) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
// Getters et setters
}


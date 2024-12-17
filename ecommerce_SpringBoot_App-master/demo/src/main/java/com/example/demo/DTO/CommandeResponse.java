package com.example.demo.DTO;
public class CommandeResponse {
    private Long id;
    private String productLabel;
    private int quantity;
    private Long clientId;
    private String clientName;
    private String clientPhone;
    private String productPrice;
    private String livreurPhone;

    // Constructor with phone number (for /livreur)
    public CommandeResponse(Long id, String productLabel , int quantity, Long clientId, String clientName, String clientPhone, String productPrice, String livreurPhone ) {
        this.id = id;
        this.productLabel = productLabel;
        this.quantity = quantity;
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.productPrice = productPrice;
        this.livreurPhone = livreurPhone;
    }

    // Constructor without phone number (for /user)
    public CommandeResponse(Long id, String productLabel, int quantity, Long clientId, String clientName) {
        this(id, productLabel, quantity, clientId, clientName, "");  // Provide an empty string or null for phone
    }

    public CommandeResponse(Long id, String productLabel, int quantity, Long clientId, String clientName, String s) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductLabel() {
        return productLabel;
    }

    public void setProductLabel(String productLabel) {
        this.productLabel = productLabel;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }
}


package com.example.demo.DTO;

public class LoginResponse {
    private String message;
    private String token;
    private String type; // This should be part of the response
    private Long compteId;
    public LoginResponse(int id, String message, String token, String role) {
        this.message = message;
        this.token = token;
        this.type = token.contains("client") ? "client" : "admin"; // You can also set the type dynamically based on the token or user
    }

    public Long getCompteId() {
        return compteId;
    }

    public void setCompteId(Long compteId) {
        this.compteId = compteId;
    }

    public String getMessage() { return message; }
    public String getToken() { return token; }
    public String getType() { return type; }
}

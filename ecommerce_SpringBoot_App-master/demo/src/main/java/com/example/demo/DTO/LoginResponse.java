package com.example.demo.DTO;

public class LoginResponse {
    private String message;
    private String token;
    private String type; // This should be part of the response

    public LoginResponse(String message, String token) {
        this.message = message;
        this.token = token;
        this.type = token.contains("client") ? "client" : "admin"; // You can also set the type dynamically based on the token or user
    }

    // Getters and setters
    public String getMessage() { return message; }
    public String getToken() { return token; }
    public String getType() { return type; }
}

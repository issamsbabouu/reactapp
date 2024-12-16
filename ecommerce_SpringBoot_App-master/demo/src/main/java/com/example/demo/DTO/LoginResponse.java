package com.example.demo.DTO;

public class LoginResponse {
    private String message;
    private String token;
    private String type;
    private Long compteId;

    public LoginResponse(Long compteId, String message, String token, String type) {
        this.compteId = compteId;
        this.message = message;
        this.token = token;
        this.type = type;
    }

    public Long getCompteId() {
        return compteId;
    }

    public void setCompteId(Long compteId) {
        this.compteId = compteId;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }
}

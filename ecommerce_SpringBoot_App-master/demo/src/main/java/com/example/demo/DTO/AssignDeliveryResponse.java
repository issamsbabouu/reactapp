package com.example.demo.DTO;

public class AssignDeliveryResponse {
    private String message;
    private String pictureUrl;

    public AssignDeliveryResponse(String message, String pictureUrl) {
        this.message = message;
        this.pictureUrl = pictureUrl;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}

package com.example.demo.DTO;

public class CommentDTO {
    private Long id;
    private String content;
    private String username; // From comptes
    private ProduitDTO product; // Nested Product details

    public CommentDTO() {}

    public CommentDTO(Long id, String content, String username, ProduitDTO product) {
        this.id = id;
        this.content = content;
        this.username = username;
        this.product = product;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public ProduitDTO getProduct() {
        return product;
    }
    public void setProduct(ProduitDTO product) {
        this.product = product;
    }
}

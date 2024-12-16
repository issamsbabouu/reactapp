package com.example.demo.DTO;

import com.example.demo.DTO.CommandeDTO;

import java.util.List;

public class PanierDTO {
    private Long id;
    private List<CommandeDTO> commandes;

    // Constructor
    public PanierDTO(Long id, List<CommandeDTO> commandes) {
        this.id = id;
        this.commandes = commandes;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CommandeDTO> getCommandes() {
        return commandes;
    }

    public void setCommandes(List<CommandeDTO> commandes) {
        this.commandes = commandes;
    }
}

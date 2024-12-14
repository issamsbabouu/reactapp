package com.example.demo.DTO;
public class CategorieDTO {

    private Long id;
    private String catname;

    public CategorieDTO(Long id, String catname) {
        this.id = id;
        this.catname = catname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }
}
